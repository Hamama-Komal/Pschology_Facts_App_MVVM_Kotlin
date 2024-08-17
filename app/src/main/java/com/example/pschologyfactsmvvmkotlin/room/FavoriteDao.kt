package com.example.pschologyfactsmvvmkotlin.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFav(favorite: Favorite)

    @Delete
    suspend fun removeFav(favorite: Favorite)

    @Update
    suspend fun updateFav(favorite: Favorite)

    @Query("SELECT * FROM FavFacts")
    fun getFav(): LiveData<List<Favorite>>

    @Query("SELECT COUNT(*) FROM FavFacts WHERE fact = :fact")
    suspend fun isFavorite(fact: String): Boolean
}