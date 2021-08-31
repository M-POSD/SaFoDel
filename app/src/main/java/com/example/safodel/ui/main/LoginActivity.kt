package com.example.safodel.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.safodel.R
import com.example.safodel.databinding.ActivityLoginBinding
import com.example.safodel.databinding.ActivityMainBinding
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var sha = MessageDigest.getInstance("SHA-256")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            val password = binding.passwordEdit.text.toString().toByteArray()
            val byte = BigInteger(1,sha.digest(password)).toString()
            if (byte.equals(getString(R.string.password))) {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "account or password is invalid",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}