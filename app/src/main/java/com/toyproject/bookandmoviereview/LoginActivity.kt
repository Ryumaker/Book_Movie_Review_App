package com.toyproject.bookandmoviereview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.toyproject.bookandmoviereview.databinding.ActivityLoginBinding

import com.toyproject.bookandmoviereview.models.darkmode

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val max_id_length = 12
    private val max_pw_length = 12

    private fun setColors() {
        if (!darkmode) {
            binding.root.setBackgroundResource(R.color.light_mode_login_background)
            binding.textLogin.setTextColor(Color.BLACK)
            binding.editId.setTextColor(Color.BLACK)
            binding.editPassword.setTextColor(Color.BLACK)
            binding.buttonLogin.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_mode_login_button_background))
            binding.buttonLogin.setTextColor(Color.WHITE)
            binding.buttonFindId.setTextColor(Color.BLACK)
            binding.textBoundary.setTextColor(Color.BLACK)
            binding.buttonFindPassword.setTextColor(Color.BLACK)
            binding.buttonRegister.setTextColor(Color.BLACK)
            binding.switchDarkmode.setTextColor(Color.BLACK)

        } else {
            binding.root.setBackgroundResource(R.color.dark_mode_login_background)
            binding.textLogin.setTextColor(Color.WHITE)
            binding.editId.setTextColor(Color.WHITE)
            binding.editPassword.setTextColor(Color.WHITE)
            binding.buttonLogin.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.dark_mode_login_button_background))
            binding.buttonFindId.setTextColor(Color.WHITE)
            binding.textBoundary.setTextColor(Color.WHITE)
            binding.buttonFindPassword.setTextColor(Color.WHITE)
            binding.buttonRegister.setTextColor(Color.WHITE)
            binding.switchDarkmode.setTextColor(Color.WHITE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 다크모드에 따른 색상 지정
        setColors()

        // ID 최대 길이 설정
        binding.editId.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_id_length))

        // PW 최대 길이 설정
        binding.editPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_pw_length))

        binding.buttonLogin.setBackgroundColor(Color.parseColor("#228DDB"))
        binding.buttonLogin.setTextColor(Color.WHITE)

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.switchDarkmode.setOnCheckedChangeListener { _, onDarkmode ->
            darkmode = if (onDarkmode) {
                Toast.makeText(this@LoginActivity, "darkmode is on", Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(this@LoginActivity, "darkmode is off", Toast.LENGTH_SHORT).show()
                false
            }
            setColors()
        }
    }
}