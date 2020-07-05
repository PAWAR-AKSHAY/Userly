package com.example.selfinfo.models.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(usersModel: UsersModel)

    @Query("SELECT * FROM $USERS_TABLE_NAME WHERE $USERS_EMAIL =:email and $USERS_PASSWORD =:password")
    suspend fun validateLogin(email : String,password : String) : List<UsersModel>

    @Query("SELECT * FROM $USERS_TABLE_NAME WHERE $USERS_ID =:id")
    suspend fun getUserData(id : Int) : List<UsersModel>

    @Update
    suspend fun updateUserData(usersModel: UsersModel)

    @Query("SELECT * FROM $USERS_TABLE_NAME WHERE $USERS_ID=:id")
    fun getUser(id : Int) : LiveData<UsersModel>

    @Query("SELECT * FROM $USERS_TABLE_NAME")
    fun getAllUsers() : LiveData<List<UsersModel>>

    @Transaction
    @Query("SELECT * FROM $USERS_TABLE_NAME")
    fun getUsersWithQuotes() : List<UsersWithQuotes>

}