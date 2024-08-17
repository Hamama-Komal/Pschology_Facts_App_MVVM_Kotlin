package com.example.pschologyfactsmvvmkotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao():  FavoriteDao

    companion object{
        private var INSTANCE : FavoriteDatabase? = null

        fun getDatabase(context: Context) : FavoriteDatabase{

            if (INSTANCE == null){

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, FavoriteDatabase::class.java, "FavFactDb").build()
                }
            }
            return INSTANCE!!
        }
    }
}