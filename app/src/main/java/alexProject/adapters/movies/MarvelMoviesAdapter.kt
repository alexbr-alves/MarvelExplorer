package alexProject.adapters.movies


import alexProject.data.model.movies.MoviesModel
import alexProject.databinding.ItemCardName2Binding
import alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MarvelMoviesAdapter : RecyclerView.Adapter<MarvelMoviesAdapter.MoviesViewHolder>() {

    inner class MoviesViewHolder(val binding: ItemCardName2Binding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(
            oldItem: MoviesModel,
            newItem: MoviesModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: MoviesModel,
            newItem: MoviesModel
        ): Boolean {
            return oldItem.image == newItem.image &&
                    oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelMoviesAdapter.MoviesViewHolder {
        return MoviesViewHolder(
            ItemCardName2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var movies: List<MoviesModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onBindViewHolder(holder: MarvelMoviesAdapter.MoviesViewHolder, position: Int) {
        val moviesItens = movies[position]
        holder.binding.apply {
            text1.text = moviesItens.name
            text2.setGone()
            Glide.with(image).load(moviesItens.image).into(image)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}