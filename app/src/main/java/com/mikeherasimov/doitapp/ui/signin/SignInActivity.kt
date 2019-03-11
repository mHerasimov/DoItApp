package com.mikeherasimov.doitapp.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_in)
        val viewModel = setupViewModel()
        binding.viewModel = viewModel
        binding.loginClickListener = View.OnClickListener {
            viewModel.validate()
        }
        binding.registerClickListener = View.OnClickListener {
            viewModel.validate()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("exitFromApp", true)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setupViewModel(): SignInViewModel {
        return ViewModelProviders.of(this).get(SignInViewModel::class.java)
    }

}
