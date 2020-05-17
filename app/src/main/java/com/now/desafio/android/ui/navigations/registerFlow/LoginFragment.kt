package com.now.desafio.android.ui.navigations.registerFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.now.desafio.android.R
import com.now.desafio.android.util.ext.navigateWithAnimation
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : Fragment() {
    val viewModel: RegisterAndLoginViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clearUser()
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        tv_register.setOnClickListener {
            findNavController().navigateWithAnimation(R.id.action_loginFragment_to_registerOrEditNameFragment)
        }

        btn_login.setOnClickListener {
            viewModel.doLogin(edt_username.text.toString(), edt_password.text.toString())
        }
    }

    private fun setupObservers() {
        viewModel.loadObserver.observe(viewLifecycleOwner, Observer {
            pb_load.visibility = if (it) View.VISIBLE else View.GONE
            btn_login.visibility = if (!it) View.VISIBLE else View.GONE
        })

        viewModel.successObserver.observe(viewLifecycleOwner, Observer { user ->
            edt_username.setText("")
            edt_password.setText("")
            val navDirections = LoginFragmentDirections.actionLoginFragmentToMainListFragment2(user)
            findNavController().navigateWithAnimation(navDirections)
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.text = it
        })
    }

}