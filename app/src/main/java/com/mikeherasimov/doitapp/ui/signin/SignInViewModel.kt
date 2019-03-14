package com.mikeherasimov.doitapp.ui.signin

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel
import kotlinx.coroutines.launch
import java.io.IOException

class SignInViewModel(
    private val userRepository: UserRepository
): ScopedViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val noEmailError = ObservableField<Int?>()
    val noPasswordError = ObservableField<Int?>()
//    val wrongCredentialsError = ObservableField<Int?>()

    private val _loginOrRegisterCompletedSuccessfully = MutableLiveData<Boolean>()
    val loginOrRegisterCompletedSuccessfully: LiveData<Boolean>
        get() = _loginOrRegisterCompletedSuccessfully

    fun validate(): Boolean {
        if (email.get().isNullOrEmpty()) {
            noEmailError.set(R.string.error_no_email)
            return false
        } else {
            noEmailError.set(null)
        }
        if (password.get().isNullOrEmpty()) {
            noPasswordError.set(R.string.error_no_password)
            return false
        } else {
            noPasswordError.set(null)
        }
        return true
    }

    fun login() {
        launch {
            try {
                userRepository.login(email.get()!!, password.get()!!)
                _loginOrRegisterCompletedSuccessfully.value = true
            } catch (e: IOException) {
                _networkError.value = true
            } catch (e: retrofit2.HttpException) {
                _networkError.value = true
            } finally {
                _networkError.value = false
            }
        }
    }

    fun register() {
        launch {
            try {
                userRepository.register(email.get()!!, password.get()!!)
                _loginOrRegisterCompletedSuccessfully.value = true
            } catch (e: IOException) {
                _networkError.value = true
            } catch (e: retrofit2.HttpException) {
                _networkError.value = true
            } finally {
                _networkError.value = false
            }
        }
    }

    class Factory(private val userRepository: UserRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(userRepository) as T
        }

    }

}