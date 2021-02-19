package id.co.woiapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import id.co.woiapp.R

object GenerateView {
    fun loading(view:ImageView,context: Context){
        Glide.with(context)
            .load(context.resources.getDrawable(R.drawable.loading))
            .into(view)
    }
}