package alexProject.adapters.group


import alexProject.data.model.group.GroupMemberModel
import alexProject.databinding.ItemCardName2Binding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CurrentMemberAdapter : RecyclerView.Adapter<CurrentMemberAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(val binding: ItemCardName2Binding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<GroupMemberModel>() {
        override fun areItemsTheSame(
            oldItem: GroupMemberModel,
            newItem: GroupMemberModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: GroupMemberModel,
            newItem: GroupMemberModel
        ): Boolean {
            return oldItem.image == newItem.image &&
                    oldItem.heroName == newItem.heroName &&
                    oldItem.peopleName == newItem.peopleName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentMemberAdapter.GroupViewHolder {
        return GroupViewHolder(
            ItemCardName2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var groups: List<GroupMemberModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onBindViewHolder(holder: CurrentMemberAdapter.GroupViewHolder, position: Int) {
        val groupItem = groups[position]
        holder.binding.apply {
            text1.text = groupItem.heroName
            text1.text = groupItem.peopleName
            Glide.with(image).load(groupItem.image).into(image)
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

}