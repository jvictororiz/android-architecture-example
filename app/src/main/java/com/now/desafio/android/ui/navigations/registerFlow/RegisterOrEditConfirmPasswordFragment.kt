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
import kotlinx.android.synthetic.main.fragment_login.tv_error
import kotlinx.android.synthetic.main.fragment_register_confirm_password.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterOrEditConfirmPasswordFragment : Fragment() {
    val viewModel: RegisterAndLoginViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_confirm_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        setupObservables()
    }

    private fun setupListeners() {
        ic_close.setOnClickListener {
            findNavController().navigateWithAnimation(R.id.action_registerOrEditConfirmPasswordFragment_to_loginFragment)
        }

        btn_back.setOnClickListener {
            findNavController().popBackStack()
        }

        btn_next.setOnClickListener {
            viewModel.next(RegisterAndLoginViewModel.StepLogin.CONFIRM_PASSWORD, input.text.toString())
        }
    }

    private fun setupObservables() {
        viewModel.successObserver.observe(viewLifecycleOwner, Observer { user ->
            val action = RegisterOrEditConfirmPasswordFragmentDirections.actionRegisterOrEditConfirmPasswordFragmentToMainListFragment2(user)
            findNavController().navigateWithAnimation(action)
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.text = it
        })

        viewModel.toNextSaveObserver.observe(viewLifecycleOwner, Observer {
            viewModel.saveUser()
        })
        viewModel.toNextUpdateObserver.observe(viewLifecycleOwner, Observer {
            viewModel.updateUser()
        })
    }

}