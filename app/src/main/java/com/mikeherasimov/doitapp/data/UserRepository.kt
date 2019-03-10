package com.mikeherasimov.doitapp.data

import com.mikeherasimov.doitapp.api.ApiService
import com.mikeherasimov.doitapp.db.TaskDao
import com.mikeherasimov.doitapp.db.User
import com.mikeherasimov.doitapp.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val taskDao: TaskDao
) {

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