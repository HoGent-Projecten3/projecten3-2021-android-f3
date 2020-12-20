package com.example.faith

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.faith.data.Login
import com.example.faith.databinding.FragmentLoginBinding
import com.example.faith.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hotel.*
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @author iedereen
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var compassIdleAnimation: AnimationDrawable
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Modelbinding
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

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

        // Activeer de logo animatie
        binding.imageLogo.apply {
            setBackgroundResource(R.drawable.compass_idle)

            compassIdleAnimation = background as AnimationDrawable
        }
        compassIdleAnimation.start()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.hotelFragment, false)
        }

        /* Rescale hotel images to the according screen size */
        scaleLogo()

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

    private fun scaleLogo() {

        // Obtain screen width & height DP
        val screenWidthDp = resources.configuration.screenWidthDp
        val screenHeightDp = resources.configuration.screenHeightDp

        // Define new size based on the screen DP. Height can be half the screen width, width has to then keep its ratio.
        // val newHeight = (screenWidthDp * 0.60).toInt() // height half the screen width
        val newWidth = (screenWidthDp * 0.75).toInt()

        val newHeight = (screenHeightDp * 0.40).toInt() // height half the screen width

        // Updating the dimensions for all rooms in pixel (Deprecated)
        /*
        image_penthouse.layoutParams.height = newWidth
        image_penthouse.layoutParams.width = newWidth
        */

        // Updating the dimensions for all rooms in DP
        val dimensionHeightInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newHeight.toFloat(), resources.displayMetrics).toInt() // new DP height
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newWidth.toFloat(), resources.displayMetrics).toInt() // new DP height

        // Only update height. these are constant. Width updated automatically, as wrap_content, and adjustviewbounds keeps ratio intact.
        image_logo.layoutParams.width = dimensionHeightInDp
        image_logo.layoutParams.height = dimensionHeightInDp
    }
}
