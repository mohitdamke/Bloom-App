package com.mohit.mohitbloomapp.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/** Loads and shows one interstitial ad at a time. */
object InterstitialAdManager {

    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false

    val isReady: Boolean get() = interstitialAd != null

    /** Starts loading. [onResult] is called with true when loaded, false on failure. */
    fun load(context: Context, onResult: ((Boolean) -> Unit)? = null) {
        if (interstitialAd != null) {
            onResult?.invoke(true)
            return
        }
        if (isLoading) return
        isLoading = true

        InterstitialAd.load(
            context.applicationContext,
            AdIds.interstitial,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    onResult?.invoke(true)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                    onResult?.invoke(false)
                }
            }
        )
    }

    /**
     * Loads the ad and waits up to [timeoutMs]. Returns true if an ad is ready to show.
     * Use this on the splash screen so the user never waits too long.
     */
    suspend fun preload(context: Context, timeoutMs: Long = 4000L): Boolean {
        if (isReady) return true
        return withTimeoutOrNull(timeoutMs) {
            suspendCancellableCoroutine<Boolean> { cont ->
                load(context) { success -> if (cont.isActive) cont.resume(success) }
            }
        } ?: false
    }

    /** Shows the ad. [onFinished] is ALWAYS called (ad closed, failed, or not available). */
    fun show(activity: Activity, onFinished: () -> Unit) {
        val ad = interstitialAd
        if (ad == null) {
            onFinished()
            return
        }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                onFinished()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                onFinished()
            }
        }
        ad.show(activity)
    }
}

/** Finds the Activity from any Compose/Context. */
fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}