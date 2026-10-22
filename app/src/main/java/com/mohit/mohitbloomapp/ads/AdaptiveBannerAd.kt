package com.mohit.mohitbloomapp.ads

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.AdView

@Composable
fun AdaptiveBannerAd(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),

            factory = { viewContext ->
                val displayMetrics = viewContext.resources.displayMetrics
                val density = displayMetrics.density

                val screenWidthDp =
                    (displayMetrics.widthPixels / density).toInt()

                val adSize =
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        viewContext,
                        screenWidthDp
                    )

                AdView(viewContext).apply {
                    adUnitId = AdMobConfig.BANNER_AD_ID
                    setAdSize(adSize)

                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            Log.d("BannerAd", "Banner loaded")
                        }

                        override fun onAdFailedToLoad(
                            error: LoadAdError
                        ) {
                            Log.e(
                                "BannerAd",
                                "Banner failed: ${error.message}"
                            )
                        }

                        override fun onAdClicked() {
                            Log.d("BannerAd", "Banner clicked")
                        }
                    }

                    loadAd(AdRequest.Builder().build())
                }
            },

            update = {}
        )
    }
}