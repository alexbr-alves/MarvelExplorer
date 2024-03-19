package alexProject.adapters

import alexProject.data.model.ItemSiteMarvelModel
import alexProject.data.model.MenuHomeModel
import alexProject.databinding.ItemCardName2Binding
import alexProject.databinding.ItemMenuHomeBinding
import alexProject.util.setGone
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenuHomeAdapter : RecyclerView.Adapter<MenuHomeAdapter.MenuHomeViewHolder>() {

    inner class MenuHomeViewHolder(val binding: ItemMenuHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<MenuHomeModel>() {
        override fun areItemsTheSame(
            oldItem: MenuHomeModel,
            newItem: MenuHomeModel
        ): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MenuHomeModel,
            newItem: MenuHomeModel
        ): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.image == newItem.image  &&
                    oldItem.destination == newItem.destination
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuHomeViewHolder {
        return MenuHomeViewHolder(
            ItemMenuHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var list: List<MenuHomeModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onBindViewHolder(holder: MenuHomeViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = list[position]
        holder.binding.apply {
            textTitle.text = context.getString(item.title)
            Glide.with(image).load(item.image).into(image)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(item)
            }
        }
    }

    private var onItemClickListener : ((MenuHomeModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (MenuHomeModel) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }
}