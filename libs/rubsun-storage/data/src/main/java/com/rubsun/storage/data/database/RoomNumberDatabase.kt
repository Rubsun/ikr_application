package com.rubsun.storage.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RoomNumberEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class RoomNumberDatabase : RoomDatabase() {
    abstract fun numberDao(): RoomNumberDao

    companion object {
        @Volatile
        private var INSTANCE: RoomNumberDatabase? = null

        fun getDatabase(context: Context): RoomNumberDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomNumberDatabase::class.java,
                    "number_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


