package id.co.woiapp.ui.detailbook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.tasks.Task
import id.co.woiapp.R
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.BookTask
import id.co.woiapp.data.entities.Character
import id.co.woiapp.data.entities.MemberBook
import id.co.woiapp.databinding.ItemAnggotaBinding
import id.co.woiapp.databinding.ItemBookBinding
import id.co.woiapp.databinding.ItemCharacterBinding
import id.co.woiapp.databinding.ItemTaskBinding

class MemberBookAdapter(private val listener: MemberBookItemListener,private val idUser : Int,private val isOwner : Boolean) :
    RecyclerView.Adapter<MemberBookViewHolder>() {

    interface MemberBookItemListener {
        fun onClickedDelete(idBookUser: Int,position: Int)

    }

    val items = ArrayList<MemberBook>()

    fun setItems(items: List<MemberBook>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberBookViewHolder {
        val binding: ItemAnggotaBinding =
            ItemAnggotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberBookViewHolder(binding, listener,idUser,isOwner)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MemberBookViewHolder, position: Int) =
        holder.bind(items[position],position)
}

class MemberBookViewHolder(
    private val itemBinding: ItemAnggotaBinding,
    private val listener: MemberBookAdapter.MemberBookItemListener,
    private val idUser : Int,
    private val isOwner : Boolean
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var task: MemberBook

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MemberBook,position: Int) {
        this.task = item
        itemBinding.tvTitleTask.text = item.name
        itemBinding.tvEmail.text = item.email
        if (item.idUser == idUser)
            itemBinding.ivDeleteMember.visibility = View.GONE
        itemBinding.ivDeleteMember.setOnClickListener {
            listener.onClickedDelete(item.idBookUser?:0,position)
        }
        if(!isOwner){
            itemBinding.ivDeleteMember.visibility = View.GONE
        }

    }

    override fun onClick(v: View?) {
//        listener.onClickedTask(task.idTask!!)
    }
}

