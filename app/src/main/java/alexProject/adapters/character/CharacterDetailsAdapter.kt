package alexProject.adapters.character

import alexProject.data.model.character.CharacterModel
import alexProject.databinding.LayoutMiniCardsBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CharacterDetailsAdapter :
    RecyclerView.Adapter<CharacterDetailsAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {

        override fun areItemsTheSame(oldItem : CharacterModel, newItem : CharacterModel) : Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem : CharacterModel,
            newItem : CharacterModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var characters : List<CharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() : Int {
        return characters.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val character = characters[position]
        holder.binding.apply {
            textName.text = character.name.limitDescription(15)

            loadImage(
                image,
                character.thumbnailModel.path,
                character.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(character)
            }
        }
    }

    private var onItemClickListener : ((CharacterModel) -> Unit)? = null

    fun setOnClickListener(listener : (CharacterModel) -> Unit) {
        onItemClickListener = listener
    }
}
