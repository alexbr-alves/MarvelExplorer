package alexProject.adapters

import alexProject.data.model.serie.SeriesModel
import alexProject.databinding.LayoutCardNameBinding
import alexProject.util.Constants
import alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {
    inner class SeriesViewHolder(val binding : LayoutCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<SeriesModel>() {
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

    private val differ = AsyncListDiffer(this, differCallBack)

    var seriesList : List<SeriesModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter {
            it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        })

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SeriesViewHolder {
        return SeriesViewHolder(
            LayoutCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() : Int {
        return seriesList.size
    }

    override fun onBindViewHolder(holder : SeriesViewHolder, position : Int) {
        val series = seriesList[position]
        holder.binding.apply {
            textName.text = series.title
            loadImage(
                image,
                series.thumbnailModel.path,
                series.thumbnailModel.extension
            )
        }
    }
}