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
import kotlinx.android.synthetic.main.fragment_register_date_nascimento.btn_back
import kotlinx.android.synthetic.main.fragment_register_date_nascimento.btn_next
import kotlinx.android.synthetic.main.fragment_register_date_nascimento.ic_close
import kotlinx.android.synthetic.main.fragment_register_date_nascimento.input
import kotlinx.android.synthetic.main.fragment_register_date_nascimento.tv_error
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterOrEditPasswordFragment : Fragment() {
    val viewModel: RegisterAndLoginViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservables()
    }

    private fun setupListeners() {
        ic_close.setOnClickListener {
            findNavController().navigateWithAnimation(R.id.action_registerOrEditPasswordFragment_to_loginFragment)
        }

        btn_back.setOnClickListener {
            findNavController().popBackStack()
        }

        btn_next.setOnClickListener {
            viewModel.next(RegisterAndLoginViewModel.StepLogin.PASSWORD, input.text.toString())
        }
    }

    private fun setupObservables() {
        viewModel.toNextSaveObserver.observe(viewLifecycleOwner, Observer {
            tv_error.visibility = View.VISIBLE
            findNavController().navigateWithAnimation(R.id.action_registerOrEditPasswordFragment_to_registerOrEditConfirmPasswordFragment)
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.text = it
        })

        viewModel.password.observe(viewLifecycleOwner, Observer {
            input.setText(it)
        })

        viewModel.currentUserObserver.observe(viewLifecycleOwner, Observer {
            ic_close.visibility = View.INVISIBLE
        })
    }

}