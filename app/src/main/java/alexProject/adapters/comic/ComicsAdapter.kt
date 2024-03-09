package alexProject.adapters.comic

import alexProject.data.model.comic.ComicModel
import alexProject.databinding.ItemCardNameBinding
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder>() {

    inner class ComicsViewHolder(val binding : ItemCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {
        override fun areItemsTheSame(oldItem : ComicModel, newItem : ComicModel) : Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem : ComicModel,
            newItem : ComicModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension &&
                    oldItem.characters.available == newItem.characters.available &&
                    oldItem.events.available == newItem.events.available
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comicList : List<ComicModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun getItemCount() : Int {
        return comicList.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ComicsViewHolder {
        return ComicsViewHolder(
            ItemCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private var onItemClickListener : ((ComicModel) -> Unit)? = null

    fun setOnClickListener(listener : (ComicModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder : ComicsViewHolder, position : Int) {
        val comic = comicList[position]
        holder.binding.apply {
            textName.text = comic.title.limitDescription(15)
            textComic.setGone()
            imageComic.setGone()
            textPeople.text = comic.characters.available.toString()
            textEvent.text = comic.events.available.toString()

            loadImage(
                image,
                comic.thumbnailModel.path,
                comic.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(comic)
            }
        }
    }
}