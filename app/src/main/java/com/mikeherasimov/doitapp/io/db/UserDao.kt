package com.mikeherasimov.doitapp.io.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<User>

    @Query("DELETE FROM user")
    fun deleteAll()

}