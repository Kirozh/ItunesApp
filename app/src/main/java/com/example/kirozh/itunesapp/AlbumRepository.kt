package com.example.kirozh.itunesapp


/**
 * @author Kirill Ozhigin on 19.11.2021
 */
class AlbumRepository(val retrofitApi: RetrofitApi, val database: MusicDatabase) {

    private fun getAlbumsFromRoom(key: String) = database.musicDao().searchAlbumByAlbumTitle(key)

    suspend fun getAlbums(queryString: String) = try {
        retrofitApi.getAlbums(queryString).albumResults
            .filter {
                it.title.contains(queryString, ignoreCase = true)
            }.apply {
                database.musicDao().insertAlbumsToRoom(this)
            }
    } catch (e: Exception) {
        getAlbumsFromRoom("%$queryString%")
    }

    private fun getSongsFromRoom(key: String) = database.musicDao().searchSongByAlbumId(key)

    suspend fun getSongs(albumId: String) = try {
        retrofitApi.getSongs(albumId).results
            .filter {
                it.kind == "song"
            }
            .apply {
                database.musicDao().insertSongsToRoom(this)
            }
    } catch (e: Exception) {
        getSongsFromRoom(albumId)
    }

    private fun getAlbumByIdFromRoom(key: String) = database.musicDao().getAlbumByIdFromRoom(key)

    suspend fun getAlbum(albumId: String): Album = try {
        retrofitApi.getAlbumById(albumId).albumResults[0]

    } catch (e: Exception) {
        getAlbumByIdFromRoom(albumId)
    }
}