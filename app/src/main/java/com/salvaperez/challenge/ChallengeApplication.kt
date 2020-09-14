package com.salvaperez.challenge

import android.app.Application
import com.salvaperez.challenge.data.di.initDI

class ChallengeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}