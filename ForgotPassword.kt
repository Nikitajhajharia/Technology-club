package com.example.tech

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class ForgotPasswordFragment : Fragment() {

    private lateinit var etForgotEmail: EditText
    private lateinit var btnResetPassword: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        etForgotEmail = view.findViewById(R.id.etForgotEmail)
        btnResetPassword = view.findViewById(R.id.btnResetPassword)

        btnResetPassword.setOnClickListener {
            handlePasswordReset()
        }

        return view
    }

    private fun handlePasswordReset() {
        val email = etForgotEmail.text.toString()

        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resetPassword(email)
        } else {
            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetPassword(email: String) {
        val tempPassword = generateTemporaryPassword()

        AlertDialog.Builder(requireContext())
            .setTitle("Temporary Password")
            .setMessage("Your temporary password is: $tempPassword\nPlease log in and change your password.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp() // Navigate back to LoginFragment
            }
            .show()

        // Save the temporary password locally (for testing purposes only)
        saveTemporaryPassword(email, tempPassword)
    }

    private fun generateTemporaryPassword(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..8).map { characters.random() }.joinToString("")
    }

    private fun saveTemporaryPassword(email: String, password: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("TempPasswordPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(email, password)
            .apply()
    }
}
