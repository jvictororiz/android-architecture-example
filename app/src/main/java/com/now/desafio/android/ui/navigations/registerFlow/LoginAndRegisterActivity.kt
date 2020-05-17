package com.now.desafio.android.ui.navigations.registerFlow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.now.desafio.android.R
import kotlinx.android.synthetic.main.activity_login_and_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginAndRegisterActivity : AppCompatActivity(R.layout.activity_login_and_register) {
    private val viewModel: RegisterAndLoginViewModel by viewModel()

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHosLoginAndRegister).navigateUp() || super.onSupportNavigateUp()
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
