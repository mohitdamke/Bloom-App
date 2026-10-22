package com.mohit.mohitbloomapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.MobileAds
import com.mohit.mohitbloomapp.ads.AppOpenAdManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null
    private var appWasBackgrounded = false

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)

        MobileAds.initialize(this) {
            Log.d("AdMob", "Mobile Ads SDK initialized")
            AppOpenAdManager.loadAd(this)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {

                override fun onStart(owner: LifecycleOwner) {
                    // Called when the app returns to the foreground.
                    if (appWasBackgrounded) {
                        currentActivity?.let { activity ->
                            AppOpenAdManager.showIfAvailable(activity)
                        }
                    }

                    appWasBackgrounded = false
                }

                override fun onStop(owner: LifecycleOwner) {
                    appWasBackgrounded = true
                }
            }
        )
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) = Unit

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }
}