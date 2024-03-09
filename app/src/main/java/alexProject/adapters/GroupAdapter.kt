package alexProject.adapters

import alexProject.R
import alexProject.data.model.GroupModel
import alexProject.data.model.GroupModelResponse
import alexProject.databinding.LayoutTimelineBinding
import alexProject.util.loadImage
import alexProject.util.setGone
import alexProject.util.setInvisible
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.AdapterViewHolder>() {
    inner class AdapterViewHolder(val binding: LayoutTimelineBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<GroupModel>() {
        override fun areItemsTheSame(
            oldItem: GroupModel,
            newItem: GroupModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: GroupModel,
            newItem: GroupModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.image == newItem.image
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var groups: List<GroupModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(
            LayoutTimelineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return groups.size
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
       val context = holder.itemView.context
        val group = groups[position]
        holder.binding.apply {
            name.text = group.name
            description.text = group.description
            type.setInvisible()
            Glide.with(image).load(group.image).into(image)
        }
    }
}