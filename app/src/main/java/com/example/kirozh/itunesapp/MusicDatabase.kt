package com.example.kirozh.itunesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author Kirill Ozhigin on 23.11.2021
 */
@Database(entities = [Album::class, Song::class], version = 2)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}

private lateinit var INSTANCE: MusicDatabase

fun getDatabase(context: Context): MusicDatabase {
    synchronized(MusicDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MusicDatabase::class.java, "music_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}