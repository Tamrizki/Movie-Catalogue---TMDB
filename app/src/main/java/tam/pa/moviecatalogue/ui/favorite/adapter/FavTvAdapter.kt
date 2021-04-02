package tam.pa.moviecatalogue.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.databinding.ItemFavoriteBinding
import tam.pa.moviecatalogue.network.ApiConfig


class FavTvAdapter(
        val listener: OnAdapterListener
): PagedListAdapter<SerialTvEntity, FavTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SerialTvEntity>(){
            override fun areItemsTheSame(
                    oldItem: SerialTvEntity,
                    newItem: SerialTvEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                    oldItem: SerialTvEntity,
                    newItem: SerialTvEntity
            ): Boolean = oldItem == newItem

        }
    }

    interface OnAdapterListener{
        fun OnClick( serialTv: SerialTvEntity)
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: SerialTvEntity) {
            with(binding){
                tvTitle.text = tv.name
                tvRate.text = tv.vote_average.toString()

                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+tv.backdrop_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_waiting)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)

                container.setOnClickListener {
                    listener.OnClick( tv )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null){
            holder.bind(tv)
        }
    }

    fun getSwipeData(swipedPosition: Int): SerialTvEntity? = getItem(swipedPosition)
}