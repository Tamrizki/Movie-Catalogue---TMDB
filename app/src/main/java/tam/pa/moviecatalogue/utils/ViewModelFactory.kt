package tam.pa.moviecatalogue.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.di.Injection
import tam.pa.moviecatalogue.ui.detail.DetailViewModel
import tam.pa.moviecatalogue.ui.favorite.FavoriteViewModel
import tam.pa.moviecatalogue.ui.home.HomeViewModel

class ViewModelFactory(
    private val repository: CatalogueRepository
): ViewModelProvider.Factory {
    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.providerRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}