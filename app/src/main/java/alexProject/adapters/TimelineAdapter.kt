package alexProject.adapters

import alexProject.R
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.timeline.TimelineModel
import alexProject.data.model.timeline.TimelineType
import alexProject.databinding.LayoutTimelineBinding
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding: LayoutTimelineBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val desireDataFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val desiredTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {
        override fun areItemsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ComicModel,
            newItem: ComicModel
        ): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.description == newItem.description) &&
                    (oldItem.title == newItem.title) &&
                    (oldItem.thumbnailModel.path == newItem.thumbnailModel.path) &&
                    (oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension)
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var timelineList: List<ComicModel>
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
        val context = holder.itemView.context
        val timeline = timelineList[position]
        holder.binding.apply {
            name.text = timeline.title.limitDescription(30)

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

    private var onItemClickListener: ((ComicModel) -> Unit)? = null

    fun setOnClickListener(listener: (ComicModel) -> Unit) {
        onItemClickListener = listener
    }
}