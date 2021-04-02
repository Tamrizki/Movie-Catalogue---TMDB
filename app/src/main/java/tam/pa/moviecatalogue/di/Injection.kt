package tam.pa.moviecatalogue.di

import android.content.Context
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.LocalDataSource
import tam.pa.moviecatalogue.data.local.room.CatalogueDatabase
import tam.pa.moviecatalogue.data.remote.RemoteDataSource
import tam.pa.moviecatalogue.network.ApiConfig
import tam.pa.moviecatalogue.utils.AppExecutors

object Injection {
    fun providerRepository(context: Context): CatalogueRepository{
        val remote = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val local = LocalDataSource.getInstance(CatalogueDatabase.getInstance(context).catalogueDao())
        val appExecutors = AppExecutors()
        return CatalogueRepository(local, remote, appExecutors)
    }
}