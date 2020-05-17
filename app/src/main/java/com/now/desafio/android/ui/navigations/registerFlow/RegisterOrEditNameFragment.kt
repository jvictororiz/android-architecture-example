package com.now.desafio.android.ui.navigations.registerFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.now.desafio.android.R
import com.now.desafio.android.util.ext.navigateWithAnimation
import kotlinx.android.synthetic.main.fragment_register_or_edit_name.*
import kotlinx.android.synthetic.main.fragment_register_or_edit_name.btn_next
import kotlinx.android.synthetic.main.fragment_register_or_edit_name.input
import kotlinx.android.synthetic.main.fragment_register_or_edit_name.tv_error
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterOrEditNameFragment : Fragment() {
    val viewModel: RegisterAndLoginViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        return inflater.inflate(R.layout.fragment_register_or_edit_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs by navArgs<RegisterOrEditNameFragmentArgs>()
        navArgs.user?.let {
            viewModel.userSelected(it)
        }
        setupListener()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.toNextSaveObserver.observe(viewLifecycleOwner, Observer {
            findNavController().navigateWithAnimation(R.id.action_registerOrEditNameFragment_to_registerOrEditFragmentEmail)
        })
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.text = it
        })
        viewModel.name.observe(viewLifecycleOwner, Observer {
            input.setText(it)
        })
    }

    private fun setupListener() {
        tv_cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        btn_next.setOnClickListener {
            viewModel.next(RegisterAndLoginViewModel.StepLogin.NAME, input.text.toString())
        }

    }

}