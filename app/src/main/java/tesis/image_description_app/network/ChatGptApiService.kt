package tesis.image_description_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import tesis.image_description_app.BuildConfig
import tesis.image_description_app.data.imageDescription.request.ImageDescriptionBodyRequest
import tesis.image_description_app.data.imageDescription.response.ImageDescriptionResponse

private const val OPEN_AI_API_KEY = BuildConfig.GOOGLE_VISION_API_KEY

interface ChatGptApiService {

    companion object {
        private const val BASE_URL  = "https://api.openai.com/v1/chat/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val instance: ChatGptApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ChatGptApiService::class.java)
    }

    @POST("completions")
    suspend fun getImageDescription(
        @Body requestBody: ImageDescriptionBodyRequest,
        @Header("Authorization") authorization: String = OPEN_AI_API_KEY,
    ): ImageDescriptionResponse

}