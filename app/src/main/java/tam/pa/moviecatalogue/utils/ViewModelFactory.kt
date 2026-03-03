package tam.pa.moviecatalogue.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tam.pa.moviecatalogue.di.Injection
import tam.pa.moviecatalogue.domain.usecase.CatalogueUseCase
import tam.pa.moviecatalogue.ui.detail.DetailViewModel
import tam.pa.moviecatalogue.ui.favorite.FavoriteViewModel
import tam.pa.moviecatalogue.ui.home.HomeViewModel

class ViewModelFactory(
    private val catalogueUseCase: CatalogueUseCase
): ViewModelProvider.Factory {
    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideCatalogueUseCase(context))
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(catalogueUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(catalogueUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(catalogueUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
