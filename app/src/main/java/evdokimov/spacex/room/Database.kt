package evdokimov.spacex.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import evdokimov.spacex.news.data.entity.LaunchRoom

@androidx.room.Database(
    entities = [
        LaunchRoom::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val spaceXDAO: NewsDao

    companion object {
        const val DB_NAME = "database.db"
        private var instance: Database? = null
     //   fun getInstance() = instance ?: throw IllegalStateException("Database has not been created")
        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
            }
        }
    }
}