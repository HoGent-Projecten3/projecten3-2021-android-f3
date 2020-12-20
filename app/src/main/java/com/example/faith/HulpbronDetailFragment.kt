package com.example.faith

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.faith.HulpbronDetailFragment.Callback
import com.example.faith.data.Hulpbron
import com.example.faith.databinding.FragmentHulpbronDetailBinding
import com.example.faith.viewmodels.HulpbronDetailViewModel
import com.example.faith.viewmodels.HulpbronDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hulpbron_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class HulpbronDetailFragment : Fragment() {

    private val args: HulpbronDetailFragmentArgs by navArgs()
    @Inject
    lateinit var hulpbronDetailViewModelFactory: HulpbronDetailViewModel.AssistedFactory
    lateinit var binding: FragmentHulpbronDetailBinding

    private val hulpbronDetailViewModel: HulpbronDetailViewModel by viewModels {
        provideFactory(
            hulpbronDetailViewModelFactory,
            args.hulpbronId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHulpbronDetailBinding>(
            inflater,
            R.layout.fragment_hulpbron_detail,
            container,
            false
        ).apply {
            viewModel = hulpbronDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback { hulpbron ->
                hulpbron?.let {
                }
            }
        }
        createLayout()
        setHasOptionsMenu(true)
        return binding.root
    }


    /**
     * OnClickListeners instellen
     */
    private fun createLayout() {
        binding.linLayTelefoon.setOnClickListener() {
            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:" + hulpbronDetailViewModel.hulpbron.value?.telefoonnummer)
                )
            )
        }

        binding.linLayUrl.setOnClickListener() {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(hulpbronDetailViewModel.hulpbron.value?.url)
                )
            )
        }

        binding.linLayChatUrl.setOnClickListener() {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(hulpbronDetailViewModel.hulpbron.value?.chatUrl)
                )
            )
        }

        binding.linLayEmail.setOnClickListener() {
            startActivity(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("mailto:" + hulpbronDetailViewModel.hulpbron.value?.emailadres)
                )
            )
        }
    }

    fun interface Callback {
        fun add(hulpbron: Hulpbron?)
    }
}
