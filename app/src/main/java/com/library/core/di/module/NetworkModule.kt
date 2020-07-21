package com.library.core.di.module
import android.content.SharedPreferences
import com.library.core.di.qualifier.BaseUrl
import com.library.core.di.qualifier.PreferenceInfo
import com.library.core.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .callTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC).setLevel(
                    HttpLoggingInterceptor.Level.BODY
                ).setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .addInterceptor { chain: Interceptor.Chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader(
                            "Authorization",
                            "Bearer " + sharedPreferences.getString(Constants.ACCESS_TOKEN,"")
                        )
                        .build()
                )
            }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@BaseUrl baseUrl : String, sharedPreferences: SharedPreferences): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(providesClient(sharedPreferences))
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create<ApiService>(ApiService::class.java)

}
