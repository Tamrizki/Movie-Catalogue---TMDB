package tam.pa.moviecatalogue.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.databinding.ItemsMovieBinding
import tam.pa.moviecatalogue.network.ApiConfig

class MovieAdapter(
    private val listener: OnAdapterListener): PagedListAdapter<MovieEntity, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean  = oldItem == newItem
        }
    }

    class ViewHolder(val binding: ItemsMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding){
                tvTitle.text = movie.original_title
                tvDate.text = movie.release_date
                tvRate.text = movie.vote_average.toString()

                Glide.with(itemView.context)
                    .load(ApiConfig.BASE_URL_IMAGE+movie.poster_path)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_waiting)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind( movie )
            holder.binding.container.setOnClickListener {
                listener.OnClick( movie )
            }
        }
    }

    interface OnAdapterListener{
        fun OnClick( movie: MovieEntity )
    }
}