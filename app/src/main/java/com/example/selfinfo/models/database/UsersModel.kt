package com.example.selfinfo.models.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = USERS_TABLE_NAME)
data class UsersModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USERS_ID)
    var usersId : Int? = 0,

    @ColumnInfo(name = USERS_FULL_NAME)
    var usersFullName: String?,

    @ColumnInfo(name = USERS_EMAIL)
    var usersEmail: String?,

    @ColumnInfo(name = USERS_PASSWORD)
    var usersPassword: String?,

    @ColumnInfo(name = USERS_CONTACT)
    var usersContact: String?,

    @ColumnInfo(name = USERS_PROFILE_IMAGE)
    var usersProfileImage : String?


) : Parcelable{
    constructor():this(null,"","","","","")
}

const val USERS_TABLE_NAME = "UsersModel"
const val USERS_FULL_NAME = "usersFullName"
const val USERS_ID = "usersId"
const val USERS_EMAIL = "usersEmail"
const val USERS_PASSWORD = "usersPassword"
const val USERS_CONTACT = "usersContact"
const val USERS_PROFILE_IMAGE = "usersProfileImage"