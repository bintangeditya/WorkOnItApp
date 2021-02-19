package id.co.woiapp.ui.booktask

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.tasks.Task
import id.co.woiapp.R
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.BookTask
import id.co.woiapp.data.entities.Character
import id.co.woiapp.databinding.ItemBookBinding
import id.co.woiapp.databinding.ItemCharacterBinding
import id.co.woiapp.databinding.ItemTaskBinding
import id.co.woiapp.utils.DateUtils
import id.co.woiapp.utils.alarm.Utils
import java.util.*
import kotlin.collections.ArrayList

class BooktaskAdapter(private val listener: BooktaskItemListener, private val isMute: Boolean) :
    RecyclerView.Adapter<BooktaskViewHolder>() {

    interface BooktaskItemListener {
        fun onClickedTask(taskId: Int)
        fun onClickedTaskCheckbox(task: BookTask, selectedItemView: ItemTaskBinding, potition: Int)
    }

    val items = ArrayList<BookTask>()

    fun setItems(items: ArrayList<BookTask>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooktaskViewHolder {
        val binding: ItemTaskBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooktaskViewHolder(binding, listener, isMute)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BooktaskViewHolder, position: Int) =
        holder.bind(items[position], position, items.lastIndex)
}

class BooktaskViewHolder(
    private val itemBinding: ItemTaskBinding,
    private val listener: BooktaskAdapter.BooktaskItemListener,
    private val isMute: Boolean
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var task: BookTask

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: BookTask, position: Int, lastIndex: Int) {
        this.task = item
        itemBinding.tvTitleTask.text = item.titleTask
        itemBinding.ivCheckbox.let { iv ->
            if (item.doneStatus == 1) {
                iv.setOnClickListener {
//                    iv.setImageResource(R.drawable.ic_checkbox)
                    listener.onClickedTaskCheckbox(task, itemBinding, position)
                }
                iv.setImageResource(R.drawable.ic_checkbox_filled)
            } else {
                iv.setOnClickListener {
//                    iv.setImageResource(R.drawable.ic_checkbox_filled)
                    listener.onClickedTaskCheckbox(task, itemBinding, position)
                }
                iv.setImageResource(R.drawable.ic_checkbox)

            }
        }

        if (isMute) {
            Log.d("dadvsfsadsas", "masuk ke mute")
            Utils.cancelAlarm(itemBinding.root.context, item.idTask!!)
        } else {
            if (item.dueDate != null) {
                Log.d("dadvsfsadsas", "tidak mute")
                Log.d("dadvsfsadsas", item.toString())
                val timeInMilliSeconds: Long = DateUtils.apiToDate(item.dueDate).time
                Utils.setAlarm(
                    itemBinding.root.context,
                    timeInMilliSeconds,
                    item.idTask!!,
                    item.titleTask ?: "",
                    item.desc ?: ""
                )

            }
        }

    }

    override fun onClick(v: View?) {
        listener.onClickedTask(task.idTask!!)
    }
}

