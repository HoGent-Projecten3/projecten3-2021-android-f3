package com.example.faith

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.faith.data.Login
import com.example.faith.databinding.FragmentLoginBinding
import com.example.faith.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false
            )
        // val viewModel: LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        // binding.lifecycleOwner = this
        binding.setLifecycleOwner(this)

        binding.buttonLogin.setOnClickListener() {
            val login = Login(binding.editTextTextEmailAddress.text.toString(), binding.editTextNumberPassword.text.toString())
            viewModel.login(login)
        }

        viewModel.loginSuccesvol.observe(
            this.viewLifecycleOwner,
            Observer {
                if (it) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        )

        viewModel.errorMessage.observe(
            this.viewLifecycleOwner,
            Observer {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        )

        return binding.root
    }
}
