package com.mikeherasimov.doitapp.ui.signin

import androidx.databinding.ObservableField
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.ui.base.ScopedViewModel

class SignInViewModel: ScopedViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val noEmailError = ObservableField<Int?>()
    val noPasswordError = ObservableField<Int?>()
//    val wrongCredentialsError = ObservableField<Int?>()

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

}