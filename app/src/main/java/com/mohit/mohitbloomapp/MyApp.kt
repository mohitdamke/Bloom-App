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
class MyApp : Application(),
    Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {

    private var currentActivity: Activity? = null
    private var wasInBackground = false

    override fun onCreate() {
        super<Application>.onCreate()

        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        MobileAds.initialize(this) {
            Log.d("WAOOAA", "Mobile Ads SDK initialized")
            AppOpenAdManager.loadAd(this)
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        if (wasInBackground) {
            wasInBackground = false

            currentActivity?.let { activity ->
                Log.d("WAOOAA", "App returned to foreground")

                if (AppOpenAdManager.canShowAds) {
                    AppOpenAdManager.showIfAvailable(activity)
                }
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        wasInBackground = true
        Log.d("WAOOAA", "App moved to background")
    }

    override fun onActivityStarted(activity: Activity) {
        if (!AppOpenAdManager.isAdShowing()) {
            currentActivity = activity
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) = Unit
}