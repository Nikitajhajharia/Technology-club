package com.example.tech

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult

class SignUpFragment : Fragment() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var cbTermsConditions: CheckBox
    private lateinit var ivProfilePicture: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnGoToLogin: Button
    private val PICK_IMAGE_REQUEST = 1

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ivProfilePicture.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_sign_up, container, false)

        // Initialize UI elements
        etUsername = rootView.findViewById(R.id.etUsername)
        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        ivProfilePicture = rootView.findViewById(R.id.ivProfilePicture)
        btnSelectImage = rootView.findViewById(R.id.btnSelectImage)
        btnSignUp = rootView.findViewById(R.id.btnSignUp)
        btnGoToLogin = rootView.findViewById(R.id.btnGoToLogin)
        cbTermsConditions = rootView.findViewById(R.id.cbTermsConditions)

        // Enable Sign Up button only when Terms and Conditions are accepted
        cbTermsConditions.setOnCheckedChangeListener { _, isChecked ->
            btnSignUp.isEnabled = isChecked
        }

        // Set up select image button to launch image picker
        btnSelectImage.setOnClickListener {
            getContent.launch("image/*")
        }

        // Set up the sign-up button
        btnSignUp.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Logic for signing up the user
                Toast.makeText(requireContext(), "Signed Up Successfully!", Toast.LENGTH_SHORT)
                    .show()

                // Navigate to another fragment or perform any other logic
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Set up the Go to Login button listener
        btnGoToLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        return rootView
    }
}
