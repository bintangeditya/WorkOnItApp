package id.co.woiapp.ui.booktask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.R
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.BookTask
import id.co.woiapp.data.entities.CheckTaskBody
import id.co.woiapp.databinding.FragmentBooktaskBinding
import id.co.woiapp.databinding.FragmentHomeBinding
import id.co.woiapp.databinding.ItemTaskBinding
import id.co.woiapp.ui.detailbook.DetailbookFragment
import id.co.woiapp.ui.detailtask.DetailtaskFragment
import id.co.woiapp.ui.home.BookAdapter
import id.co.woiapp.ui.home.HomeViewModel
import id.co.woiapp.ui.newtask.NewtaskFragment
import id.co.woiapp.utils.*
import kotlin.jvm.internal.Ref

@AndroidEntryPoint
class BooktaskFragment : Fragment(), BooktaskAdapter.BooktaskItemListener {

    private var binding: FragmentBooktaskBinding by autoCleared()
    private val viewModel: BooktaskViewModel by viewModels()
    private lateinit var adapter: BooktaskAdapter
    private lateinit var book: Book
    private lateinit var customBackpressed: OnBackPressedCallback

    companion object {
        const val BOOK = "BOOK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customBackpressed = requireActivity().onBackPressedDispatcher.addCallback {
            customBackpressed.remove()
            findNavController().navigate(R.id.action_booktaskFragment_to_homeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooktaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingRvTask, requireContext())
        GenerateView.loading(binding.loadingCheckdone, requireContext())
        book = arguments?.getParcelable(BOOK) ?: Book()
        val idUser = SharedPref.getIntVal(SharedPref.IDUSER, requireActivity())
        val idBook = book.idBook ?: 0
        binding.tvTitleBar.text = book.title
        binding.btnCreatetask.setOnClickListener { newtask(idBook) }
        viewModel.getTask(idUser, idBook)
        binding.btnBack.setOnClickListener {
            customBackpressed.remove()
            findNavController().navigate(R.id.action_booktaskFragment_to_homeFragment)
        }
        binding.srlBooktask.setOnRefreshListener {
            viewModel.getTask(idUser, idBook)
            binding.srlBooktask.isRefreshing = false
        }
        binding.tvTitleBar.setOnClickListener {
            goToDetail()
        }
        if(book.status != "owner"){
            binding.btnCreatetask.hide()
        }
        setupObservers()
        setupRecyclerView()
    }

    private fun goToDetail(){
        customBackpressed.remove()
        findNavController().navigate(R.id.action_booktaskFragment_to_detailbookFragment,
        bundleOf(DetailbookFragment.BOOK to  book))
    }

    private fun newtask(idBook: Int) {
        customBackpressed.remove()
        findNavController().navigate(
            R.id.action_booktaskFragment_to_newtaskFragment,
            bundleOf(NewtaskFragment.IDBOOK to idBook)
        )
    }


    private fun setupObservers() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        adapter.setItems(ArrayList(it.data))
                    }
                    binding.loadingRvTask.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingRvTask.visibility = View.INVISIBLE
                }

                Resource.Status.LOADING ->{
                    binding.loadingRvTask.visibility = View.VISIBLE
                }

            }
        })

        viewModel.responseCheckTask.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    calbackCheckDone()
                    binding.loadingCheckdone.visibility = View.INVISIBLE
                    binding.blockerCheckdone.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingCheckdone.visibility = View.INVISIBLE
                    binding.blockerCheckdone.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                Resource.Status.LOADING -> {
                    binding.loadingCheckdone.visibility = View.VISIBLE
                    binding.blockerCheckdone.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    );
                }

            }
        })

    }

    private fun setupRecyclerView() {
        adapter = BooktaskAdapter(this,book.mute == 1)
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.adapter = adapter
    }

    override fun onClickedTask(taskId: Int) {

        customBackpressed.remove()
        findNavController().navigate(
            R.id.action_booktaskFragment_to_detailtaskFragment,
            bundleOf(
                DetailtaskFragment.IDTASK to taskId,
                DetailtaskFragment.STATUS to book.status
            )
        )
    }

    private var currentCheckSelectedPotition = 0
    private var curentCheckSelectedItemBinding: ItemTaskBinding? = null
    private var currentCheckSelectedDoneStatus = 0

    override fun onClickedTaskCheckbox(
        task: BookTask,
        selectedItemView: ItemTaskBinding,
        potition: Int
    ) {
        val idUser = SharedPref.getIntVal(SharedPref.IDUSER, requireActivity())
        val doneStatus = if (task.doneStatus == 1) 0 else 1

        curentCheckSelectedItemBinding = selectedItemView
        currentCheckSelectedPotition = potition
        currentCheckSelectedDoneStatus = doneStatus

        val body = CheckTaskBody(idUser, doneStatus, task.idTask)

        viewModel.chekDone(body)
    }

    private fun calbackCheckDone() {
        if(curentCheckSelectedItemBinding != null && adapter.items.size >= 0 ){
            if (currentCheckSelectedDoneStatus == 1) curentCheckSelectedItemBinding!!.ivCheckbox.setImageResource(
                R.drawable.ic_checkbox_filled
            )
            else curentCheckSelectedItemBinding!!.ivCheckbox.setImageResource(R.drawable.ic_checkbox)
            adapter.items[currentCheckSelectedPotition].doneStatus = currentCheckSelectedDoneStatus
        }

        currentCheckSelectedPotition = 0
        curentCheckSelectedItemBinding = null
        currentCheckSelectedDoneStatus = 0
    }


}