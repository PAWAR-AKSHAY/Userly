package com.example.selfinfo.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersModel::class,QuotesModel::class,RatingsModel::class,UsersQuotesCrossRef::class],version = 3,exportSchema = false)
abstract class Databases: RoomDatabase(){

    abstract fun usersDao() : UsersDao
    abstract fun quotesDao() : QuotesDao
    abstract fun ratingsDao() : RatingsDao

    companion object{
        @Volatile

        private var INSTANCE : Databases? = null

        fun getDatabase(context: Context) : Databases?{
            if(INSTANCE == null){
                synchronized(Databases::class){
                    INSTANCE = Room.databaseBuilder(context, Databases::class.java,"Database").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}