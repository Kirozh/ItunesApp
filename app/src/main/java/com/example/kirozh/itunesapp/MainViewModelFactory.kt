package com.example.kirozh.itunesapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kirozh.itunesapp.ui.main.MainViewModel
import com.example.kirozh.itunesapp.AlbumRepository
import com.example.kirozh.itunesapp.RetrofitApi

/**
 * @author Kirill Ozhigin on 19.11.2021
 */
class MainViewModelFactory(private val repository: AlbumRepository,
                           private val app: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository, this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
