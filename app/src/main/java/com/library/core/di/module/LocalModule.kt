package com.library.core.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.library.core.di.qualifier.PreferenceInfo
import com.library.core.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String = Constants.PREF_NAME

//    @Provides
//    @DatabaseInfo
//    fun provideDatabaseName(): String? = Constants.DATABASE_NAME

    @Provides
    @Singleton
    internal fun providePreference(context: Context): SharedPreferences = context.getSharedPreferences(
        Constants.PREF_NAME, Context.MODE_PRIVATE)
}
