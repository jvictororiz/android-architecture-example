package com.now.desafio.android.ui.navigations.registerFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.now.desafio.android.R
import com.now.desafio.android.util.ext.navigateWithAnimation
import kotlinx.android.synthetic.main.fragment_register_email.btn_back
import kotlinx.android.synthetic.main.fragment_register_email.btn_next
import kotlinx.android.synthetic.main.fragment_register_email.ic_close
import kotlinx.android.synthetic.main.fragment_register_email.input
import kotlinx.android.synthetic.main.fragment_register_email.tv_error
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterOrEditEmailFragment : Fragment() {
    val viewModel: RegisterAndLoginViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        setupObservables()
    }

    private fun setupListeners() {
        ic_close.setOnClickListener {
            findNavController().navigateWithAnimation(R.id.action_registerOrEditFragmentEmail_to_loginFragment)
        }

        btn_back.setOnClickListener {
            findNavController().popBackStack()
        }

        btn_next.setOnClickListener {
            viewModel.next(RegisterAndLoginViewModel.StepLogin.EMAIL, input.text.toString())
        }
    }

    private fun setupObservables() {
        viewModel.toNextSaveObserver.observe(viewLifecycleOwner, Observer {
            findNavController().navigateWithAnimation(R.id.action_registerOrEditFragmentEmail_to_registerOrEditEmailDataNascimentoFragment)
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.text = it
        })

        viewModel.email.observe(viewLifecycleOwner, Observer {
            input.setText(it)
        })

        viewModel.currentUserObserver.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                ic_close.visibility = View.INVISIBLE
                input.isEnabled = false
            }
        })

    }

}