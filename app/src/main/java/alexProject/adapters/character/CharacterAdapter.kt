package alexProject.adapters.character


import alexProject.R
import alexProject.network.response.CharacterResponse
import alexProject.databinding.ItemCardNameBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : ItemCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterResponse>() {
        override fun areItemsTheSame(oldItem : CharacterResponse, newItem : CharacterResponse) : Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem : CharacterResponse,
            newItem : CharacterResponse
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension &&
                    oldItem.comics.available == newItem.comics.available &&
                    oldItem.events.available == newItem.events.available
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
    private val characterMutableList: MutableList<CharacterResponse> = mutableListOf()

    var characters : List<CharacterResponse>
        get() = characterMutableList
        set(value) {
            characterMutableList.clear()
            characterMutableList.addAll(value)
            differ.submitList(value)
        }

    override fun getItemCount() : Int = characters.size
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            ItemCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun addItems(items: List<CharacterResponse>) {
        val startPosition = characters.size
        characters = characters + items
        differ.submitList(characterMutableList)
        notifyItemRangeInserted(startPosition, items.size)
    }
    fun clearItems() {
        characterMutableList.clear()
        differ.submitList(null)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val character = characters[position]
        holder.binding.apply {
            textName.text = removeParenthesesOnTitle(character.name).limitDescription(15)
            loadImage(
                image,
                character.thumbnailModel.path,
                character.thumbnailModel.extension
            )
        }
        holder.itemView.findViewById<MaterialCardView>(R.id.card).setOnClickListener {
            onItemClickListener?.let {
                it(character)
            }
        }
    }

    private var onItemClickListener : ((CharacterResponse) -> Unit)? = null

    fun setOnClickListener(listener : (CharacterResponse) -> Unit) {
        onItemClickListener = listener
    }
}