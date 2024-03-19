package alexProject.adapters.event

import alexProject.data.model.event.EventModel
import alexProject.databinding.LayoutMiniCardsBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EventDetailsAdapter : RecyclerView.Adapter<EventDetailsAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding : LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<EventModel>() {

        override fun areItemsTheSame(oldItem : EventModel, newItem : EventModel) : Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem : EventModel,
            newItem : EventModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var events : List<EventModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() : Int {
        return events.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : EventViewHolder {
        return EventViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : EventViewHolder, position : Int) {
        val event = events[position]
        holder.binding.apply {
            textName.text = event.title.limitDescription(15)

            loadImage(
                image,
                event.thumbnailModel.path,
                event.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(event)
            }
        }
    }

    private var onItemClickListener : ((EventModel) -> Unit)? = null

    fun setOnClickListener(listener : (EventModel) -> Unit) {
        onItemClickListener = listener
    }
}
