package alexProject.adapters

import alexProject.data.model.FavoriteModel
import alexProject.databinding.ItemStoryCircleBinding
import alexProject.util.Constants
import alexProject.util.loadImage
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding: ItemStoryCircleBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<FavoriteModel>() {
        override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: FavoriteModel,
            newItem: FavoriteModel
        ): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.description == newItem.description) &&
                    (oldItem.title == newItem.title) &&
                    (oldItem.thumbnailModel.path == newItem.thumbnailModel.path) &&
                    (oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension)
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var list: List<FavoriteModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return TimelineViewHolder(
            ItemStoryCircleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            loadImage(
                image,
                item.thumbnailModel.path,
                item.thumbnailModel.extension
            )

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((FavoriteModel) -> Unit)? = null

    fun setOnClickListener(listener: (FavoriteModel) -> Unit) {
        onItemClickListener = listener
    }
}