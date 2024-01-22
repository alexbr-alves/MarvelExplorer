package alexProject.adapters


import alexProject.data.model.character.CharacterModel
import alexProject.databinding.LayoutCardNameBinding
import alexProject.fragment.CharacterFragment
import alexProject.util.Constants
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.setGone
import alexProject.util.setInvisible
import alexProject.util.setVisible
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.logging.Handler

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : LayoutCardNameBinding) :
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
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension &&
                    oldItem.comics.available == newItem.comics.available &&
                    oldItem.events.available == newItem.events.available
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var characters : List<CharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter { it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE })

    override fun getItemCount() : Int = characters.size
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            LayoutCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val character = characters[position]
        holder.binding.apply {
            textPeople.setGone()
            imagePeople.setGone()
            textComic.text = character.comics.available.toString()
            textEvent.text = character.events.available.toString()

            textName.text = character.name.limitDescription(20)
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