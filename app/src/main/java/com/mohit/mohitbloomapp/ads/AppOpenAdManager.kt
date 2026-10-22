package com.mohit.mohitbloomapp.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

object AppOpenAdManager {

    private const val TAG = "WAOOAA"
    private const val AD_VALIDITY_MS = 4 * 60 * 60 * 1000L

    private var appOpenAd: AppOpenAd? = null
    private var isLoading = false
    private var isShowing = false
    private var loadedAt = 0L

    var canShowAds = false
        private set

    fun enableAds() {
        canShowAds = true
        Log.d(TAG, "App Open Ads ENABLED")
    }

    suspend fun waitForAd(
        context: Context,
        timeoutMillis: Long = 2_000L
    ): Boolean {
        loadAd(context)

        return withTimeoutOrNull(timeoutMillis.milliseconds) {
            while (!isAdAvailable()) {
                delay(100.milliseconds)
            }
            true
        } ?: false
    }

    fun loadAd(context: Context) {

        Log.d(
            TAG,
            "loadAd() called | isLoading=$isLoading, available=${isAdAvailable()}"
        )

        if (isLoading) {
            Log.d(TAG, "Ad load skipped: already loading")
            return
        }

        if (isAdAvailable()) {
            Log.d(TAG, "Ad load skipped: an ad is already available")
            return
        }

        isLoading = true
        Log.d(TAG, "Loading App Open Ad | adUnitId=${AdMobConfig.APP_OPEN_AD_ID}")

        AppOpenAd.load(
            context.applicationContext,
            AdMobConfig.APP_OPEN_AD_ID,
            AdRequest.Builder().build(),
            object : AppOpenAd.AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    loadedAt = System.currentTimeMillis()
                    isLoading = false

                    Log.d(
                        TAG,
                        "SUCCESS: App Open Ad loaded | loadedAt=$loadedAt"
                    )
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    appOpenAd = null
                    isLoading = false

                    Log.e(
                        TAG,
                        "FAILED to load App Open Ad | " +
                                "code=${error.code}, " +
                                "domain=${error.domain}, " +
                                "message=${error.message}"
                    )
                }
            }
        )
    }

    suspend fun waitAndShowAd(
        activity: Activity,
        onFinished: () -> Unit
    ): Boolean {

        Log.d(TAG, "waitAndShowAd() called")

        if (!canShowAds) {
            Log.d(TAG, "Cannot show ad: ads are disabled")
            return false
        }

        loadAd(activity.applicationContext)

        Log.d(TAG, "Waiting up to 2 seconds for an ad to load")

        val loaded = withTimeoutOrNull(2_000L.milliseconds) {
            while (!isAdAvailable()) {
                delay(100.milliseconds)
            }
            true
        } ?: false

        if (!loaded) {
            Log.w(
                TAG,
                "Ad not ready after 2 seconds | isLoading=$isLoading"
            )
            return false
        }

        Log.d(TAG, "Ad is ready; attempting to show")
        return showIfAvailable(activity, onFinished)
    }

    private fun isAdAvailable(): Boolean {
        val ad = appOpenAd

        if (ad == null) {
            return false
        }

        if (System.currentTimeMillis() - loadedAt >= AD_VALIDITY_MS) {
            Log.d(TAG, "Loaded ad expired; discarding it")
            appOpenAd = null
            return false
        }

        return true
    }

    /**
     * Returns true if an ad display was initiated.
     * Returns false if the ad cannot be shown.
     */
    fun showIfAvailable(
        activity: Activity,
        onFinished: () -> Unit = {}
    ): Boolean {

        val available = isAdAvailable()

        Log.d(
            TAG,
            "showIfAvailable() | " +
                    "canShowAds=$canShowAds, " +
                    "isShowing=$isShowing, " +
                    "isFinishing=${activity.isFinishing}, " +
                    "isDestroyed=${activity.isDestroyed}, " +
                    "available=$available"
        )

        if (!canShowAds) {
            Log.w(TAG, "Ad not shown: ads are disabled")
            loadAd(activity.applicationContext)
            return false
        }

        if (isShowing) {
            Log.w(TAG, "Ad not shown: another ad is already showing")
            return false
        }

        if (activity.isFinishing || activity.isDestroyed) {
            Log.w(TAG, "Ad not shown: Activity is finishing or destroyed")
            return false
        }

        if (!available) {
            Log.w(TAG, "Ad not shown: no ad is available")
            loadAd(activity.applicationContext)
            return false
        }

        val ad = appOpenAd

        if (ad == null) {
            Log.w(TAG, "Ad not shown: ad became unavailable")
            return false
        }

        isShowing = true
        appOpenAd = null

        Log.d(TAG, "Calling ad.show(activity)")

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "SUCCESS: App Open Ad displayed")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "App Open Ad dismissed by user")
                finishShowing(activity, onFinished)
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.e(
                    TAG,
                    "FAILED to display App Open Ad | " +
                            "code=${error.code}, " +
                            "domain=${error.domain}, " +
                            "message=${error.message}"
                )

                finishShowing(activity, onFinished)
            }

            override fun onAdClicked() {
                Log.d(TAG, "App Open Ad clicked")
            }

            override fun onAdImpression() {
                Log.d(TAG, "App Open Ad impression recorded")
            }
        }

        return try {
            ad.show(activity)
            Log.d(TAG, "ad.show(activity) called successfully")
            true
        } catch (exception: Exception) {
            Log.e(TAG, "Exception while showing ad", exception)
            finishShowing(activity, onFinished)
            false
        }
    }

    private fun finishShowing(
        activity: Activity,
        onFinished: () -> Unit
    ) {
        Log.d(TAG, "Finishing ad flow")

        isShowing = false

        Log.d(TAG, "Requesting next App Open Ad")
        loadAd(activity.applicationContext)

        Log.d(TAG, "Calling onFinished()")
        onFinished()
    }

    fun isAdShowing(): Boolean {
        Log.d(TAG, "isAdShowing() = $isShowing")
        return isShowing
    }
}