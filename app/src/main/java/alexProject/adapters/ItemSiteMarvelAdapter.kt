package alexProject.adapters


import alexProject.data.model.ItemSiteMarvelModel
import alexProject.databinding.ItemCardName2Binding
import alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemSiteMarvelAdapter : RecyclerView.Adapter<ItemSiteMarvelAdapter.MoviesViewHolder>() {

    inner class MoviesViewHolder(val binding: ItemCardName2Binding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ItemSiteMarvelModel>() {
        override fun areItemsTheSame(
            oldItem: ItemSiteMarvelModel,
            newItem: ItemSiteMarvelModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ItemSiteMarvelModel,
            newItem: ItemSiteMarvelModel
        ): Boolean {
            return oldItem.image == newItem.image &&
                    oldItem.name == newItem.name  &&
                    oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        return MoviesViewHolder(
            ItemCardName2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var list: List<ItemSiteMarvelModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            text1.text = item.name
            text2.setGone()
            Glide.with(image).load(item.image).into(image)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(item)
            }
        }
    }

    private var onItemClickListener : ((ItemSiteMarvelModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (ItemSiteMarvelModel) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }

}