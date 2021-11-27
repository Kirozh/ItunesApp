package com.example.kirozh.itunesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Kirill Ozhigin on 19.11.2021
 */
interface RetrofitApi {
    @GET("search?entity=album&limit=200")
    suspend fun getAlbums(@Query("term") inputString: String): AlbumResponseModel

    @GET("lookup?entity=song")
    suspend fun getSongs(@Query("id") albumId: String): SongResponseModel

    @GET("lookup?entity=album")
    suspend fun getAlbumById(@Query("id") albumId: String): AlbumResponseModel

    companion object {
        var retrofitService: RetrofitApi? = null

        fun getInstance(): RetrofitApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitApi::class.java)
            }
            return retrofitService!!
        }
    }
}