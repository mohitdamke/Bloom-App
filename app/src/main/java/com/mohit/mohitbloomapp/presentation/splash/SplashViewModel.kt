package com.mohit.mohitbloomapp.presentation.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.mohitbloomapp.ads.AppOpenAdManager
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


sealed interface SplashState {
    data object Loading : SplashState
    data object Onboarding : SplashState
    data object ShowAppOpenAd : SplashState
    data object Home : SplashState
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state = _state.asStateFlow()

    private var started = false

    fun start() {
        if (started) return
        started = true

        viewModelScope.launch {
            try {
                delay(1500.milliseconds)

                val onboardingCompleted =
                    OnboardingDataStore(context)
                        .onboardingCompleted
                        .first()

                if (!onboardingCompleted) {
                    LogHelper.log("First-time user: onboarding required")
                    _state.value = SplashState.Onboarding
                    return@launch
                }

                LogHelper.log("Returning user: enabling App Open Ads")
                AppOpenAdManager.enableAds()

                val adReady = AppOpenAdManager.waitForAd(context)

                _state.value = if (adReady) {
                    SplashState.ShowAppOpenAd
                } else {
                    LogHelper.log("Ad unavailable: continue to Home")
                    SplashState.Home
                }
            } catch (exception: Exception) {
                LogHelper.log("Splash flow failed: ${exception.message}")
                _state.value = SplashState.Home
            }
        }
    }

    fun onAdFlowFinished() {
        LogHelper.log("Ad flow finished: navigate to Home")
        _state.value = SplashState.Home
    }

    fun onAdCouldNotStart() {
        // A rotation may cause a new Activity to attempt showing an ad
        // while the previous Activity's ad is still visible.
        if (!AppOpenAdManager.isAdShowing()) {
            LogHelper.log("Ad could not start: navigate to Home")
            _state.value = SplashState.Home
        }
    }
}

private object LogHelper {
    fun log(message: String) {
        android.util.Log.d("WAOOAA", message)
    }
}