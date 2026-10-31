package com.krishansingh.campuscompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class CampusDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var instance: CampusDatabase? = null

        fun get(context: Context): CampusDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                CampusDatabase::class.java,
                "campus_companion.db"
            ).build().also { instance = it }
        }
    }
}
