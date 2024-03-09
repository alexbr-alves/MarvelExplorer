package alexProject.adapters.event

import alexProject.data.model.event.EventModel
import alexProject.databinding.LayoutCardNameBinding
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding : LayoutCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem : EventModel, newItem : EventModel) : Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem : EventModel,
            newItem : EventModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.description == newItem.description &&
                    oldItem.title == newItem.title &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension &&
                    oldItem.characters.available == newItem.characters.available &&
                    oldItem.comics.available == newItem.comics.available
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var eventList : List<EventModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : EventViewHolder {
        return EventViewHolder(
            LayoutCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() : Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder : EventViewHolder, position : Int) {
        val event = eventList[position]
        holder.binding.apply {
            textEvent.setGone()
            imageEvent.setGone()
            textPeople.text = event.characters.available.toString()
            textComic.text = event.comics.available.toString()

            textName.text = event.title.limitDescription(15)
            loadImage(
                image,
                event.thumbnailModel.path,
                event.thumbnailModel.extension
            )

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(event)
                }
            }
        }
    }

    private var onItemClickListener : ((EventModel) -> Unit)? = null

    fun setOnClickListener(listener : (EventModel) -> Unit) {
        onItemClickListener = listener
    }
}