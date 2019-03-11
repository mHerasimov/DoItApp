package com.mikeherasimov.doitapp.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikeherasimov.doitapp.R

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("exitFromApp", true)
        setResult(RESULT_OK, intent)
        finish()
    }

}
