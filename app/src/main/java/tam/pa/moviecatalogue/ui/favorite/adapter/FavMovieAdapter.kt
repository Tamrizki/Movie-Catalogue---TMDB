package tam.pa.moviecatalogue.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.databinding.ItemFavoriteBinding
import tam.pa.moviecatalogue.network.ApiConfig

class FavMovieAdapter(
        private val listener: OnAdapterListener
): PagedListAdapter<MovieEntity, FavMovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean  = oldItem == newItem
        }
    }

    interface OnAdapterListener{
        fun OnClick( movie: MovieEntity)
    }

    class ViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding){
                tvTitle.text = movie.original_title
                tvRate.text = movie.vote_average.toString()

                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+movie.backdrop_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_waiting)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if ( movie != null ){
            holder.bind( movie )
            holder.binding.container.setOnClickListener {
                listener.OnClick( movie )
            }
        }
    }

    fun getSwipeData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)
}