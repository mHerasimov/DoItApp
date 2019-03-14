package com.mikeherasimov.doitapp.io.data

import androidx.lifecycle.LiveData
import com.mikeherasimov.doitapp.io.api.ApiService
import com.mikeherasimov.doitapp.io.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val taskDao: TaskDao
) {

    fun getUser(): LiveData<User?> = userDao.getUser()

    suspend fun getUserSync(): User? {
        var user: User? = null
        withContext(Dispatchers.IO) {
            user = userDao.getUserSync()
        }
        return user
    }

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val user = User(email, password)
            val map = apiService.loginAsync(user).await()
            userDao.insert(user.copy(token = map.getValue("token")))
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
            val user = User(email, password)
            val map = apiService.registerAsync(user).await()
            userDao.insert(user.copy(token = map.getValue("token")))
        }
    }

}