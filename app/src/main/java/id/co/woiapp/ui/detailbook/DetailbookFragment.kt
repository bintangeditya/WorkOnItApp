package id.co.woiapp.ui.detailbook

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.R
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailBook
import id.co.woiapp.data.entities.MemberBook
import id.co.woiapp.databinding.FragmentDetailbookBinding
import id.co.woiapp.ui.booktask.BooktaskFragment
import id.co.woiapp.utils.*
import kotlinx.android.synthetic.main.fragment_detailbook.*


@AndroidEntryPoint
class DetailbookFragment : Fragment(), MemberBookAdapter.MemberBookItemListener {

    private var binding: FragmentDetailbookBinding by autoCleared()
    private val viewModel: DetailBookViewModel by viewModels()
    private lateinit var adapter: MemberBookAdapter
    private val listSpinner = listOf("Private", "Sharing")
    private var detailBook = DetailBook()
    private var deletePosition = -1
    private var muteVar = 0
    private var book = Book()

    companion object {
        const val BOOK = "BOOK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingBlocker, requireContext())
        book = arguments?.getParcelable(BOOK) ?: Book()
        val idUser = SharedPref.getIntVal(SharedPref.IDUSER, requireActivity())
        viewModel.getDetailBook(book.idBook!!, idUser)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailbookFragment_to_booktaskFragment,
                bundleOf(BooktaskFragment.BOOK to book)
            )
        }

        binding.btnEditDesc.setOnClickListener {
            if (!binding.tvDescBook.isEnabled)
                editClickListener(binding.btnEditDesc, binding.tvDescBook)
            else
                closeClickListener(binding.btnEditDesc, binding.tvDescBook)
        }
        binding.btnEditTitle.setOnClickListener {
            if (!binding.tvTitle.isEnabled)
                editClickListener(binding.btnEditTitle, binding.tvTitle)
            else
                closeClickListener(binding.btnEditTitle, binding.tvTitle)
        }

        binding.btnDelete.let {
            if (book.status == "owner") {
                it.text = "HAPUS BOOK"
                it.setOnClickListener {
                    if (detailBook.type == "private") {
                        viewModel.deleteBook(book.idBook!!)
                    } else

                        SnackBin.show(
                            binding.root,
                            "Ubah terlebih dahulu tipe Book menjadi private"
                        )
                }
            } else {
                it.text = "KELUAR DARI BOOK"
                it.setOnClickListener {
                    viewModel.unjoinMe(book.idBookUser!!)
                }
                binding.btnEditDesc.visibility = View.GONE
                binding.btnEditTitle.visibility = View.GONE
                binding.spinnerType.isEnabled = false

            }
        }
        muteVar = book.mute!!
        if(muteVar == 0)
            binding.btnMute.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_bell_on))
        else
            binding.btnMute.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_bell_off))

        binding.btnMute.setOnClickListener {
            val mute = if(book.mute == 1) 0 else 1
            val mBook = Book(idBookUser =  book.idBookUser,idBook = book.idBook,idUser = book.idUser,
            mute =  mute,status = book.status)
            muteVar = mute
            viewModel.mute(mBook)
        }

        setupObservers()
        setupRecyclerView()
    }

    private fun editClickListener(iv: ImageView, tv: EditText) {
        tv.isEnabled = true
        tv.requestFocus()
        tv.setSelection(tv.text.length)
        iv.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_round_close_24))
    }

    private fun setSpinner() {
        if (binding.tvCode.text.toString().isNotBlank()) {
            binding.spinnerType.let {
                it.adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_spinner,
                    listSpinner
                )
                it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                        if (adapter.items!!.size > 1 && p2 == 0) {
                            SnackBin.show(binding.root, "Book memiliki lebih dari satu anggota")
                            it.setSelection(1)
                        } else {
                            val title = binding.tvTitle.text.toString()
                            val desc = binding.tvDescBook.text.toString()
                            val type = spinner_type.selectedItem.toString().toLowerCase()
                            val book = Book(
                                description = desc,
                                title = title,
                                type = type,
                                idBook = detailBook.idBook
                            )
                            viewModel.updateBook(book)
                        }

                    }

                }
            }
        }
    }

    private fun closeClickListener(iv: ImageView, tv: EditText) {

        if (binding.tvTitle.text.toString().length > 40){
            SnackBin.show(binding.root, "Panjang maksimal Judul Book adalah 40")
        }else{
            tv.isEnabled = false
            val title = binding.tvTitle.text.toString()
            val desc = binding.tvDescBook.text.toString()
            val type = spinner_type.selectedItem.toString().toLowerCase()
            val book = Book(description = desc, title = title, type = type, idBook = detailBook.idBook)
            viewModel.updateBook(book)
            iv.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_edit))
        }
    }


    private fun setupObservers() {
        viewModel.books.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        detailBook = it.data[0]
                        if (!it.data[0].member.isNullOrEmpty()) {
                            adapter.setItems(it.data[0].member as List<MemberBook>)
                        }
                        binding.tvCode.text = it.data[0].idBook.toString()
                        binding.tvTitle.setText(it.data[0].title!!)
                        binding.tvDescBook.setText(it.data[0].description!!)
                        setSpinner()
                        if (it.data[0].type == "private")
                            binding.spinnerType.setSelection(0)
                        else
                            binding.spinnerType.setSelection(1)
                    }
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })

        viewModel.resultUnjoin.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (deletePosition >= 0) {
                        adapter.notifyItemRemoved(deletePosition)
                        adapter.items.removeAt(deletePosition)
                    }
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })

        viewModel.resultMute.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    book.mute = muteVar
                    if(muteVar == 0)
                        binding.btnMute.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_bell_on))
                    else
                        binding.btnMute.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_bell_off))

                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                Resource.Status.ERROR -> {
                    muteVar = book.mute!!
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })

        viewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    detailBook.title = binding.tvTitle.text.toString()
                    detailBook.description = binding.tvDescBook.text.toString()
                    detailBook.type = spinner_type.selectedItem.toString().toLowerCase()

                    book.title = binding.tvTitle.text.toString()
                    book.description = binding.tvDescBook.text.toString()
                    book.type = spinner_type.selectedItem.toString().toLowerCase()

                    binding.tvTitle.setText(detailBook.title)
                    binding.tvDescBook.setText(detailBook.description)
                    if (detailBook.type == "private")
                        binding.spinnerType.setSelection(0)
                    else
                        binding.spinnerType.setSelection(1)
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                }
                Resource.Status.ERROR -> {
                    binding.tvTitle.setText(detailBook.title)
                    binding.tvDescBook.setText(detailBook.description)
                    if (detailBook.type == "private")
                        binding.spinnerType.setSelection(0)
                    else
                        binding.spinnerType.setSelection(1)
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })

        viewModel.resultDelete.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    findNavController().navigate(R.id.action_detailbookFragment_to_homeFragment)
                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter =
            MemberBookAdapter(this, SharedPref.getIntVal(SharedPref.IDUSER, requireActivity()),book.status == "owner")
        binding.rvMember.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMember.adapter = adapter
    }

    override fun onClickedDelete(idBookUser: Int, position: Int) {
        viewModel.unjoin(idBookUser)
        deletePosition = position
    }
}