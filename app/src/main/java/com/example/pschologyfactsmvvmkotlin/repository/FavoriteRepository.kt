package com.example.pschologyfactsmvvmkotlin.repository

import androidx.lifecycle.LiveData
import com.example.pschologyfactsmvvmkotlin.room.Favorite
import com.example.pschologyfactsmvvmkotlin.room.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: LiveData<List<Favorite>> = favoriteDao.getFav()

    suspend fun insert(favorite: Favorite) {
        favoriteDao.addFav(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        favoriteDao.removeFav(favorite)
    }

    suspend fun update(favorite: Favorite) {
        favoriteDao.updateFav(favorite)
    }

    suspend fun isFavorite(fact: String): Boolean {
        return favoriteDao.isFavorite(fact)
    }
}

