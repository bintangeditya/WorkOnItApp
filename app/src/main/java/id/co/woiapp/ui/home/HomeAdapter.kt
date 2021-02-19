package id.co.woiapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.Character
import id.co.woiapp.databinding.ItemBookBinding
import id.co.woiapp.databinding.ItemCharacterBinding

class BookAdapter(private val listener: BookItemListener) : RecyclerView.Adapter<BookViewHolder>() {

    interface BookItemListener {
        fun onClickedBook(book: Book)
    }

    private val items = ArrayList<Book>()

    fun setItems(items: ArrayList<Book>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding: ItemBookBinding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) = holder.bind(items[position])
}

class BookViewHolder(private val itemBinding: ItemBookBinding, private val listener: BookAdapter.BookItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var book: Book

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Book) {
        this.book = item
        itemBinding.tvTitleBook.text = item.title
//        itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
//        Glide.with(itemBinding.root)
//            .load(item.image)
//            .transform(CircleCrop())
//            .into(itemBinding.image)

    }

    override fun onClick(v: View?) {
        listener.onClickedBook(book)
    }
}

