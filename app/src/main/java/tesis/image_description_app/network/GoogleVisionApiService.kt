package tesis.image_description_app.network

import retrofit2.Retrofit
import retrofit2.http.*
import okhttp3.*
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory
import tesis.image_description_app.BuildConfig
import java.util.concurrent.TimeUnit

private const val GOOGLE_VISION_API_KEY = BuildConfig.GOOGLE_VISION_API_KEY

interface GoogleVisionApiService {

    //TODO timeouts
    companion object {
        private const val BASE_URL  = "https://vision.googleapis.com/v1/"

        val instance: GoogleVisionApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build())
            .build()
            .create(GoogleVisionApiService::class.java)
    }

    @POST("./images:annotate")
    suspend fun fetchForImageInformation(
        @Body requestBody: String,
        @Query("key") apiKey: String = GOOGLE_VISION_API_KEY
    ): Response<String>

}