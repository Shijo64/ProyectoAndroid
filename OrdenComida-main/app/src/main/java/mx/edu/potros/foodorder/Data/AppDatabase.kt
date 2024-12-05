package mx.edu.potros.foodorder.Data

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import mx.edu.potros.foodorder.Interfaces.OrdenDao
import mx.edu.potros.foodorder.Interfaces.PlatillosDao

@Database(entities = [PlatilloOrden::class, Orden::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun platillosDao(): PlatillosDao
    abstract fun ordenDao(): OrdenDao

    internal lateinit var instance: AppDatabase

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }
}