package com.rubsun.impl.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NumberEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class NumberDatabase : RoomDatabase() {
    abstract fun numberDao(): NumberDao

    companion object {
        @Volatile
        private var INSTANCE: NumberDatabase? = null

        fun getDatabase(context: Context): NumberDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NumberDatabase::class.java,
                    "number_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


