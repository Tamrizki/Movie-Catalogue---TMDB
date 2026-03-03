package tam.pa.moviecatalogue.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.databinding.ItemsPopularBinding
import tam.pa.moviecatalogue.network.ApiConfig

class DetailAdapter(private val listener: onAdapterListener): PagedListAdapter<Movie, DetailAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean  = oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: ItemsPopularBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding){
                tvTitleSub.text = movie.title
                tvRateSub.text = movie.voteAverage.toString()

                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+movie.posterPath)
                        .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_waiting)
                                        .error(R.drawable.ic_error))
                        .into(imgPosterSub)
                container.setOnClickListener {
                    listener.OnClick( movie )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemsPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }

    interface onAdapterListener{
        fun OnClick(data: Movie)
    }

}