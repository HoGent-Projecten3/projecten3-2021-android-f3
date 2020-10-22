package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.faith.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModel: LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        //binding.lifecycleOwner = this
        binding.setLifecycleOwner(this)

        binding.btnLogin.setOnClickListener() {
            val login = Login(binding.txtNaam.text.toString(), binding.txtWachtwoord.text.toString())
            viewModel.login(login)
        }

        viewModel.loginSuccesvol.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                this.view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_hoofdschermFragment)
            }
        })

        viewModel.errorMessage.observe(this.viewLifecycleOwner, Observer {
            binding.errorText.text = it
        })

        return binding.root
    }

}