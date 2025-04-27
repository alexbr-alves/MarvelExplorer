
package alexProject.adapters

import alexProject.network.response.ComicResponse
import alexProject.databinding.LayoutTimelineBinding
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import alexProject.util.setGone
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding: LayoutTimelineBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicResponse>() {
        override fun areItemsTheSame(oldItem: ComicResponse, newItem: ComicResponse): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ComicResponse,
            newItem: ComicResponse
        ): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.description == newItem.description) &&
                    (oldItem.title == newItem.title) &&
                    (oldItem.thumbnailModel.path == newItem.thumbnailModel.path) &&
                    (oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension)
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var timelineList: List<ComicResponse>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return TimelineViewHolder(
            LayoutTimelineBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val timeline = timelineList[position]
        holder.binding.apply {
            name.text = removeParenthesesOnTitle(timeline.title).limitDescription(30)
            type.setGone()

            if (timeline.description.isNullOrEmpty()) {
                description.setGone()
            } else {
                description.text = timeline.description.limitDescription(80)
            }
            loadImage(
                image,
                timeline.thumbnailModel.path,
                timeline.thumbnailModel.extension
            )

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(timeline)
                }
            }
        }
    }

    private var onItemClickListener: ((ComicResponse) -> Unit)? = null

    fun setOnClickListener(listener: (ComicResponse) -> Unit) {
        onItemClickListener = listener
    }
}