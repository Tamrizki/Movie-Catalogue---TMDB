package tam.pa.moviecatalogue.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.databinding.ItemsPopularBinding
import tam.pa.moviecatalogue.network.ApiConfig

class DetailAdapter(private val listener: onAdapterListener): PagedListAdapter<MovieEntity, DetailAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean  = oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: ItemsPopularBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding){
                tvTitleSub.text = movie.original_title
                tvRateSub.text = movie.vote_average.toString()

                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+movie.poster_path)
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
        fun OnClick(data: MovieEntity)
    }

}