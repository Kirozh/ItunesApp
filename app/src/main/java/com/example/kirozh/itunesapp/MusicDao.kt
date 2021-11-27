package com.example.kirozh.itunesapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kirozh.itunesapp.Album
import com.example.kirozh.itunesapp.Song

/**
 * @author Kirill Ozhigin on 23.11.2021
 */
@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbumsToRoom(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongsToRoom(songs: List<Song>)

    @Query("SELECT * FROM album WHERE album.title LIKE :title")
    fun searchAlbumByAlbumTitle(title: String): List<Album>

    @Query("SELECT * FROM song WHERE song.collectionId LIKE :key")
    fun searchSongByAlbumId(key: String): List<Song>

    @Query("SELECT * FROM album WHERE album.collectionId LIKE :key")
    fun getAlbumByIdFromRoom(key: String): Album
}