package com.now.desafio.android.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.now.desafio.android.BaseApplication

//Classe usada para alterar a url do aplicativo
class MockTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, BaseApplicationTest::class.java.name, context)
    }
}

class  BaseApplicationTest : BaseApplication() {
    override fun getApiUrl(): String {
        return "http://127.0.0.1:8081"
    }

    override fun getNameDatabase(): String {
        return "database-db-test"
    }
}
