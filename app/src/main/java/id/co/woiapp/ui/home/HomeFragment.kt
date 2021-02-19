package id.co.woiapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import id.co.woiapp.databinding.FragmentHomeBinding
import id.co.woiapp.ui.booktask.BooktaskFragment
import id.co.woiapp.utils.*

@AndroidEntryPoint
class HomeFragment : Fragment(), BookAdapter.BookItemListener {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var customBackpressed: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customBackpressed = requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingRvBook, requireContext())
        viewModel.getBook(SharedPref.getIntVal(SharedPref.IDUSER, requireActivity()))
        binding.btnCreatebook.setOnClickListener { newBook() }
        binding.tvName.text = SharedPref.getStringVal(SharedPref.NAME,requireActivity()) +","
        binding.srlHome.setOnRefreshListener {
//            viewModel.getQuote()
            viewModel.getBook(SharedPref.getIntVal(SharedPref.IDUSER, requireActivity()))
            binding.srlHome.isRefreshing = false
        }
        viewModel.getQuote()
        binding.tvQuote.setOnClickListener { viewModel.getQuote() }
        binding.tvName.setOnClickListener {
            customBackpressed.remove()
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment)
        }
        setupObservers()
        setupRecyclerView()
    }

    private fun newBook() {
        customBackpressed.remove()
        findNavController().navigate(R.id.action_homeFragment_to_createbookFragment2)
    }

    private fun setupObservers() {
        viewModel.books.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        adapter.setItems(ArrayList(it.data))
                        Log.d("dasdasdacdas6546", it.data.toString())
                    }
                    binding.loadingRvBook.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingRvBook.visibility = View.INVISIBLE

                }

                Resource.Status.LOADING ->{
                    binding.loadingRvBook.visibility = View.VISIBLE
                }
            }
        })
        viewModel.quote.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.tvQuote.text = it.data?.get(0)
                }
                Resource.Status.ERROR -> {
                }

                Resource.Status.LOADING ->{
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvBook.isNestedScrollingEnabled = false
        adapter = BookAdapter(this)
        binding.rvBook.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBook.adapter = adapter
        binding.rvBook.isNestedScrollingEnabled = false

    }

    override fun onClickedBook(book: Book) {
        customBackpressed.remove()
        findNavController().navigate(
            R.id.action_homeFragment_to_booktaskFragment,
            bundleOf(BooktaskFragment.BOOK to book)
        )
//        SnackBin.show(binding.root, "bookId : $bookId")
    }


}