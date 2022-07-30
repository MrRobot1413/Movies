package ua.mrrobot1413.movies.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.utils.Constants.API_KEY
import ua.mrrobot1413.movies.utils.Constants.BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(@ApplicationContext context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->

                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url()
                val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", API_KEY).build()
                request.url(url)
                return@addNetworkInterceptor chain.proceed(request.build())
            }.
            connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .addNetworkInterceptor(ChuckerInterceptor(context)).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)

            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}