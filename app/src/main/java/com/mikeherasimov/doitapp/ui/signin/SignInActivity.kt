package com.mikeherasimov.doitapp.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mikeherasimov.doitapp.App
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.databinding.ActivitySignInBinding
import com.mikeherasimov.doitapp.io.data.UserRepository
import com.mikeherasimov.doitapp.ui.util.showNoInternetToast
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_in)
        val viewModel = setupViewModel()
        binding.viewModel = viewModel
        binding.loginClickListener = View.OnClickListener {
            if (viewModel.validate()) {
                viewModel.login()
            }
        }
        binding.registerClickListener = View.OnClickListener {
            if (viewModel.validate()) {
                viewModel.register()
            }
        }
        viewModel.loginOrRegisterCompletedSuccessfully.observe(this, Observer {
            if (it) {
                finish()
            }
        })
        viewModel.networkError.observe(this, Observer {
            if (it) {
                showNoInternetToast(this)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("exitFromApp", true)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setupViewModel(): SignInViewModel {
        val factory = SignInViewModel.Factory(userRepository)
        return ViewModelProviders
            .of(this, factory)
            .get(SignInViewModel::class.java)
    }

}
