package com.example.pschologyfactsmvvmkotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pschologyfactsmvvmkotlin.repository.FavoriteRepository
import com.example.pschologyfactsmvvmkotlin.room.Favorite
import com.example.pschologyfactsmvvmkotlin.room.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    val allFavorites: LiveData<List<Favorite>>

    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        allFavorites = repository.allFavorites
    }

    fun insert(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(favorite)
    }

    fun delete(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(favorite)
    }

    fun update(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(favorite)
    }

    suspend fun isFavorite(fact: String): Boolean {
        return repository.isFavorite(fact)
    }
}
