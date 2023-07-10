package com.toyproject.bookandmoviereview

import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.toyproject.bookandmoviereview.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val max_id_length = 12
    private val max_pw_length = 12
    private val max_email_length = 50

    private var pwVisible: Boolean = false
    private var pwConfirmVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ID 최대 길이 설정
        binding.editId.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_id_length))

        // PW 최대 길이 설정
        binding.editPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_pw_length))
        binding.editPasswordConfirm.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_pw_length))

        // Email 최대 길이 설정
        binding.editEmail.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max_email_length))

        binding.iconPasswordVisibility.setOnClickListener {
            pwVisible = !pwVisible
            if (pwVisible) {
                binding.iconPasswordVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.iconPasswordVisibility.setImageResource(R.drawable.ic_visibility_on)
                binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.iconPasswordConfirmVisibility.setOnClickListener {
            pwConfirmVisible = !pwConfirmVisible
            if (pwConfirmVisible) {
                binding.iconPasswordConfirmVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.editPasswordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.iconPasswordConfirmVisibility.setImageResource(R.drawable.ic_visibility_on)
                binding.editPasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}