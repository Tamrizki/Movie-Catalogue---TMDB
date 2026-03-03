package tam.pa.moviecatalogue.ui.serialtv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.databinding.ItemsSerialTvBinding
import tam.pa.moviecatalogue.network.ApiConfig

class SerialTvAdapter(
    val listener: OnAdapterListener): PagedListAdapter<TvSeries, SerialTvAdapter.vHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvSeries>(){
            override fun areItemsTheSame(
                oldItem: TvSeries,
                newItem: TvSeries
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TvSeries,
                newItem: TvSeries
            ): Boolean = oldItem == newItem

        }
    }

    inner class vHolder(val binding: ItemsSerialTvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(serialTv: TvSeries) {
            with(binding){
                tvTitle.text = serialTv.name
                tvDate.text = itemView.context.getString(R.string.first_date)+serialTv.firstAirDate
                tvRate.text = serialTv.voteAverage.toString()
                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+serialTv.posterPath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_waiting)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)

                btnShare.setOnClickListener {
                    listener.OnShare( serialTv )
                }
                container.setOnClickListener {
                    listener.OnClick( serialTv )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = vHolder(
            ItemsSerialTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SerialTvAdapter.vHolder, position: Int) {
        val serialTv = getItem(position)
        if (serialTv != null){
            holder.bind(serialTv)
        }
    }

    interface OnAdapterListener{
        fun OnClick( serialTv: TvSeries )
        fun OnShare( serialTv: TvSeries )
    }
}