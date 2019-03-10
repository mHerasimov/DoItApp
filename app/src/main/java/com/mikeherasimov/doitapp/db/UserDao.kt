package com.mikeherasimov.doitapp.db

import androidx.lifecycle.LiveData
import androidx.room.Query

interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<User>

    @Query("DELETE FROM user")
    fun deleteAll()

}