package com.hiphopheads.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Release::class], version = 1, exportSchema = false)
abstract class ReleaseDatabase : RoomDatabase(){

    abstract fun getReleaseDao(): ReleaseDao

    companion object {
        @Volatile
        private var INSTANCE: ReleaseDatabase? = null

        fun getDatabase(context: Context): ReleaseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ReleaseDatabase::class.java, "release_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}