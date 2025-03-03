package alexProject.adapters.comic

import alexProject.network.response.ComicResponse
import alexProject.databinding.LayoutMiniCardsBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ComicDetailsAdapter : RecyclerView.Adapter<ComicDetailsAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding : LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicResponse>() {

        override fun areItemsTheSame(oldItem : ComicResponse, newItem : ComicResponse) : Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem : ComicResponse,
            newItem : ComicResponse
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comics : List<ComicResponse>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() : Int {
        return comics.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ComicViewHolder {
        return ComicViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private var onItemClickListener : ((ComicResponse) -> Unit)? = null

    fun setOnClickListener(listener : (ComicResponse) -> Unit) {
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder : ComicViewHolder, position : Int) {
        val comic = comics[position]
        holder.binding.apply {
            textName.text = removeParenthesesOnTitle(comic.title).limitDescription(15)

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