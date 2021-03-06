package nam.tran.data.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import nam.tran.data.BuildConfig
import nam.tran.data.api.*
import nam.tran.data.local.IPreference
import nam.tran.data.local.PreferenceModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module(includes = [PreferenceModule::class])
class NetModule {

    init {
        System.loadLibrary("keys")
    }

    companion object {
        const val KEY_CONTENT_TYPE = "Content-Type"
        const val KEY_AUTHORIZATION = "Authorization"
        const val VALUE_CONTENT_TYPE = "application/json"
    }

    @Provides
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024  // 10 MB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideNetWorkMonitor(application: Application): INetworkMonitor {
        return LiveNetworkMonitor(application.applicationContext)
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    fun provideTrustManager(trustManagerController: TrustManagerController): Array<TrustManager> {
        return trustManagerController.trustManager
    }

    @Provides
    fun provideOkHttpClient(cache: Cache, iPreference: IPreference, iNetworkMonitor: INetworkMonitor, trustAllCerts: Array<TrustManager>): OkHttpClient {

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .cache(cache)
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    if (iNetworkMonitor.isConnected()) {
                        val request = chain.request()
                        val builder = request.newBuilder()

                        val headers = HashMap<String, String>()
                        headers[KEY_CONTENT_TYPE] = VALUE_CONTENT_TYPE
                        for ((key, value) in headers) {
                            builder.addHeader(key, value)
                        }
                        chain.proceed(builder.build())
                    } else {
                        throw NoNetWorkException()
                    }
                }
                .hostnameVerifier { _, _ -> true }
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun makeService(retrofit: Retrofit): IApi {
        return retrofit.create(IApi::class.java)
    }
}
