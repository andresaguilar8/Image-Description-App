package tesis.image_description_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory
import tesis.image_description_app.BuildConfig
import java.util.concurrent.TimeUnit

private const val GOOGLE_VISION_API_KEY = BuildConfig.GOOGLE_VISION_API_KEY

interface GoogleVisionApiService {

    companion object {
        private const val BASE_URL  = "https://vision.googleapis.com/v1/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val instance: GoogleVisionApiService = Retrofit.Builder()

            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            //.addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS) // Increase the timeout here
                .readTimeout(30, TimeUnit.SECONDS).build())
            //TODO ver timouts
            .build()
            .create(GoogleVisionApiService::class.java)
    }

    @POST("./images:annotate")
    suspend fun fetchForImageInformation(
        @Body requestBody: String,
        @Query("key") apiKey: String = GOOGLE_VISION_API_KEY
    ): Response<String>

}