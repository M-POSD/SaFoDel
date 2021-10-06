package com.example.safodel.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.safodel.R
import com.example.safodel.databinding.ActivityLoginBinding
import tyrantgit.explosionfield.ExplosionField
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var wrongCount = 0
    private lateinit var toast: Toast
    private var sha = MessageDigest.getInstance("SHA-256")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = getSharedPreferences("login", Context.MODE_PRIVATE)
        toast = Toast.makeText(this,"message",Toast.LENGTH_SHORT)
        pref.edit().putBoolean("enabled",true).apply()
        binding.login.setOnClickListener {
            val password = binding.passwordEdit.text.toString().toByteArray()
            val byte = BigInteger(1,sha.digest(password)).toString()
            if (byte == getString(R.string.password)) {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val editor = pref.edit()
                wrongCount++
                when {
                    wrongCount < 7 -> {
                        toast.setText("account or password is invalid")
                        toast.show()
                    }
                    wrongCount in 7..9 -> {
                        toast.setText("Last " + (10-wrongCount) +" chances.")
                        toast.show()
                    }
                    wrongCount == 10 -> {
                        editor.putBoolean("enabled",false)
                        editor.apply()
                        binding.passwordEdit.isEnabled = false
                        ExplosionField.attach2Window(this).explode(binding.login)
                    }
                }
            }
        }
    }

}