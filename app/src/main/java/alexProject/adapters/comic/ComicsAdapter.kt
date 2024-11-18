package alexProject.adapters.comic

import alexProject.R
import alexProject.data.model.comic.ComicModel
import alexProject.databinding.ItemCardNameBinding
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import alexProject.util.setGone
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder>() {

    inner class ComicsViewHolder(val binding : ItemCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {
        override fun areItemsTheSame(oldItem : ComicModel, newItem : ComicModel) : Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem : ComicModel,
            newItem : ComicModel
        ) : Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)
    private val comicMutableList: MutableList<ComicModel> = mutableListOf()

    var comicList: List<ComicModel>
        get() = differ.currentList
        set(value) {
            comicMutableList.clear()
            comicMutableList.addAll(value)
            differ.submitList(comicMutableList)
        }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        return ComicsViewHolder(
            ItemCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    fun addItems(newItems: List<ComicModel>) {
        val startPosition = comicMutableList.size
        comicMutableList.addAll(newItems)
        differ.submitList(comicMutableList)
        notifyItemRangeInserted(startPosition, newItems.size)
        notifyDataSetChanged()
    }

    fun clearItems() {
        comicMutableList.clear()
        differ.submitList(null)
        notifyDataSetChanged()
    }

    private var onItemClickListener : ((ComicModel) -> Unit)? = null

    fun setOnClickListener(listener: (ComicModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        val comic = comicList[position]
        holder.binding.apply {
            textName.text = removeParenthesesOnTitle(comic.title).limitDescription(15)
            loadImage(
                image,
                comic.thumbnailModel.path,
                comic.thumbnailModel.extension
            )
        }
        holder.itemView.findViewById<MaterialCardView>(R.id.card).setOnClickListener {
            onItemClickListener?.let {
                it(comic)
            }
        }
    }
}