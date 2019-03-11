package com.mikeherasimov.doitapp.io.data

import androidx.lifecycle.LiveData
import com.mikeherasimov.doitapp.io.api.ApiService
import com.mikeherasimov.doitapp.io.db.TaskDao
import com.mikeherasimov.doitapp.io.db.User
import com.mikeherasimov.doitapp.io.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val taskDao: TaskDao
) {

    fun getUser(): LiveData<User?> = userDao.getUser()

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val token = apiService.login(email, password).await()
            userDao.insert(User(email, password, token))
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            userDao.deleteAll()
            taskDao.deleteAll()
        }
    }

    suspend fun register(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val token = apiService.register(email, password).await()
            userDao.insert(User(email, password, token))
        }
    }

}