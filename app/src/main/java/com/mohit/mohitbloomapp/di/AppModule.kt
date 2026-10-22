package com.mohit.mohitbloomapp.di

import android.content.Context
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOnboardingDataStore(
        @ApplicationContext context: Context
    ): OnboardingDataStore {
        return OnboardingDataStore(context)
    }
}