package com.now.desafio.android.ui.navigations.registerFlow

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.now.desafio.android.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: RegisterAndLoginViewModel by viewModel()

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHosLoginAndRegister).navigateUp() || super.onSupportNavigateUp()
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
