package alexProject.adapters.character


import alexProject.R
import alexProject.network.response.CharacterModel
import alexProject.databinding.ItemCardNameBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : ItemCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem : CharacterModel, newItem : CharacterModel) : Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem : CharacterModel,
            newItem : CharacterModel
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
    private val characterMutableList: MutableList<CharacterModel> = mutableListOf()

    var characters : List<CharacterModel>
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

    fun addItems(items: List<CharacterModel>) {
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
            Log.i("Alex Brito", "onBindViewHolder: $character")
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