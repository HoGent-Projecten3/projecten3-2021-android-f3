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
import androidx.navigation.Navigation
import com.example.faith.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {

        /* Inflate this layout */
        val binding: FragmentLoginBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        /* Obtain the login viewmodel */
        val viewModel: LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        //binding.lifecycleOwner = this
        binding.setLifecycleOwner(this)

        /* On login button click, make login object & tracht er via het login viewmodel mee in te loggen*/
        binding.buttonLogin.setOnClickListener() {
            //Make a login object
            val login = Login(binding.editTextTextEmailAddress.text.toString(), binding.editTextNumberPassword.text.toString())
            viewModel.login(login)
        }

        /*On succesvol loginViewModel, navigate to hotel fragment*/
        viewModel.loginSuccesvol.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                this.view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_hotelFragment)
            }
        })

        viewModel.errorMessage.observe(this.viewLifecycleOwner, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        return binding.root
    }
}