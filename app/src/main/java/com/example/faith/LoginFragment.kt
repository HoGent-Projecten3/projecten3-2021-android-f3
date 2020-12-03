package com.example.faith

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.faith.data.Login
import com.example.faith.databinding.FragmentLoginBinding
import com.example.faith.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var compassIdleAnimation: AnimationDrawable
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        //Modelbinding
        val binding: FragmentLoginBinding =   DataBindingUtil.inflate(  inflater, R.layout.fragment_login, container, false )

        // val viewModel: LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        // binding.lifecycleOwner = this
        binding.setLifecycleOwner(this)

        binding.buttonLogin.setOnClickListener() {
            val login = Login(
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextNumberPassword.text.toString()
            )
            viewModel.login(login)
        }

        //Activeer de logo animatie
        val jens = binding.imageView.apply { setBackgroundResource(R.drawable.compass_idle)
        compassIdleAnimation = background as AnimationDrawable }
        compassIdleAnimation.start()




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.hotelFragment, false)
        }

        viewModel.loginSuccesvol.observe(
            this.viewLifecycleOwner,
            Observer {
                if (it) {
                    navController.navigate(R.id.action_global_hotelFragment)
                    /*
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)

                     */
                }
            }
        )

        viewModel.errorMessage.observe(
            this.viewLifecycleOwner,
            Observer {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        )
    }
}
