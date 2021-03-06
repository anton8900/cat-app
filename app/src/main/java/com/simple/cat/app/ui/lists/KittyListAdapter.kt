package com.simple.cat.app.ui.lists

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import android.graphics.Point
import com.bumptech.glide.request.RequestOptions
import com.simple.cat.app.ui.activities.ContainerActivity

class KittyListAdapter(
    private val context: ContainerActivity,
    private val items: ArrayList<Kitty>,
    private val saveClickListener: View.OnClickListener?
): RecyclerView.Adapter<KittyListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kittyImageView: ImageView = itemView.findViewById(R.id.kitty_image_view)
        val imageLoadingProgress: View = itemView.findViewById(R.id.image_progress)
        val downloadImageView: View = itemView.findViewById(R.id.download)
        init {
            if(saveClickListener != null) {
                itemView.setOnClickListener(saveClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.kitty_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitty = getKittyAt(position)
        val url = kitty?.url
        if(url != null) {
            kitty.resize(getDisplayWidth())

            holder.imageLoadingProgress.layoutParams.height = kitty.height
            holder.imageLoadingProgress.visibility = View.VISIBLE

            Glide.with(context)
                .load(url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        holder.imageLoadingProgress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        holder.imageLoadingProgress.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions().override(kitty.width, kitty.height))
                .into(holder.kittyImageView)

            holder.downloadImageView.setOnClickListener{
                context.saveImage(url)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    fun loadMoreKitties(moreKitties: List<Kitty>) {
        items.addAll(moreKitties)
        notifyItemRangeInserted(items.size - moreKitties.size, moreKitties.size)
    }

    fun getKittyAt(pos: Int?): Kitty? =
        if(pos != null && items.size > pos)
            items[pos]
        else
            null

    private fun getDisplayWidth(): Int {
        val display = context.getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        return size.x
    }
}