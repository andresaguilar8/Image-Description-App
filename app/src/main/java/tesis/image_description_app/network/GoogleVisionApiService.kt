package tesis.image_description_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tesis.image_description_app.data.imageInformation.response.ImageInformationResponse
import okhttp3.*
import okhttp3.Response

import tesis.image_description_app.BuildConfig
import tesis.image_description_app.data.imageInformation.request.ImageInfoBodyRequest

private const val GOOGLE_VISION_API_KEY = BuildConfig.GOOGLE_VISION_API_KEY

interface GoogleVisionApiService {

    companion object {
        private const val BASE_URL  = "https://vision.googleapis.com/v1/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val instance: GoogleVisionApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build()).build().create(GoogleVisionApiService::class.java)
    }

    @POST("./images:annotate")
    suspend fun fetchForImageInformation(
        @Body requestBody: ImageInfoBodyRequest,
        @Query("key") apiKey: String = GOOGLE_VISION_API_KEY
    ): ResponseBody

}