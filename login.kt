package com.example.tech

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var btnSignup: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize UI components
        etEmail = view.findViewById(R.id.etUsername)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword)
        btnSignup = view.findViewById(R.id.btnsignup1)

        // Set up Login button listener
        btnLogin.setOnClickListener {
            validateLogin()
        }

        // Set up Forgot Password listener
        tvForgotPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ForgotPasswordFragment())
                .addToBackStack(null)
                .commit()
        }

        // Set up Signup button listener
        btnSignup.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun validateLogin() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            if (isTemporaryPasswordValid(email, password)) {
                Toast.makeText(requireContext(), "Login successful with temporary password", Toast.LENGTH_SHORT).show()
                // Navigate to the next screen or perform login logic
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isTemporaryPasswordValid(email: String, password: String): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("TempPasswordPrefs", Context.MODE_PRIVATE)
        val savedPassword = sharedPreferences.getString(email, null)
        return savedPassword == password
    }
}






