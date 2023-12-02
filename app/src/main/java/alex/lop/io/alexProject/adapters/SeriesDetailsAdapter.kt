package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.serie.SeriesModel
import alex.lop.io.alexProject.databinding.LayoutMiniCardsBinding
import alex.lop.io.alexProject.util.Constants
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SeriesDetailsAdapter : RecyclerView.Adapter<SeriesDetailsAdapter.SeriesViewHolder>()  {

    inner class SeriesViewHolder(val binding : LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<SeriesModel>() {

        override fun areItemsTheSame(oldItem : SeriesModel, newItem : SeriesModel) : Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem : SeriesModel,
            newItem : SeriesModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var series : List<SeriesModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun getItemCount(): Int {
        return series.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SeriesViewHolder {
        return SeriesViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : SeriesViewHolder, position : Int) {
        val serie = series[position]
        holder.binding.apply {
            textName.text = serie.title

            loadImage(
                image,
                serie.thumbnailModel.path,
                serie.thumbnailModel.extension
            )
        }
    }
}
