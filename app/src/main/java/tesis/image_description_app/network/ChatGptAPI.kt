package tesis.image_description_app.network

import retrofit2.Response
import okhttp3.OkHttpClient
import retrofit2.http.Body
import retrofit2.http.Header
import tesis.image_description_app.data.imageDescription.request.ImageDescriptionBodyRequest
import tesis.image_description_app.data.imageDescription.response.ImageDescriptionResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.*
import tesis.image_description_app.BuildConfig

private const val OPEN_AI_API_KEY = BuildConfig.OPEN_AI_API_KEY

interface ChatGptAPI {

    companion object {
        private const val BASE_URL  = "https://api.openai.com/v1/chat/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val instance: ChatGptAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ChatGptAPI::class.java)
    }

    @POST("completions")
    suspend fun fetchForImageDescription(
        @Body requestBody: ImageDescriptionBodyRequest,
        @Header("Authorization") authorization: String = OPEN_AI_API_KEY,
    ): Response<ImageDescriptionResponse>

}