package tam.pa.moviecatalogue.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity

@Database(entities = [MovieEntity::class, SerialTvEntity::class],
        version = 1,
        exportSchema = false)
abstract class CatalogueDatabase : RoomDatabase(){
    abstract fun catalogueDao(): CatalogueDao

    companion object{

        @Volatile
        private var INSTANCE: CatalogueDatabase? = null

        fun getInstance(context: Context): CatalogueDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                CatalogueDatabase::class.java,
                "Catalogue.db").build()
            }
    }
}