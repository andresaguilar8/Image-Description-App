package tesis.image_description_app.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tesis.image_description_app.data.imageInformation.request.*
import tesis.image_description_app.data.imageInformation.response.ImageInformationResponse

class ImageInfoRepository(private val googleApi: GoogleVisionApiService) {

    suspend fun getImageInfo(base64Image: String): Result<ImageInformationResponse> {
        val bodyRequest = generateBodyRequest(base64Image)
        val bodyRequestJSON = generateJSONBodyRequest(bodyRequest)
        println(bodyRequestJSON)


        return try {
            val response = googleApi.fetchForImageInformation(bodyRequest)
            println(response)
            Log.e("aca", "$response")
            /*if (response.isSuccessful) {
                val stringJson = response.body()?.string()
                if (stringJson != null)
                    println(stringJson)
            }*/

            //val imageInfo = response.responses.first()
            Result.success(response)
        }
        catch (exception: Exception) {
            Log.e("aca", "$exception")
            Result.failure(exception)
        }
    }
    private fun generateBodyRequest(base64Image: String): ImageInfoBodyRequest {
        //FEATURES
        val labelDetectionFeature = Feature(maxResults = 10, type = "LABEL_DETECTION")
        val textDetectionFeature = Feature(type = "TEXT_DETECTION")
        /*
        ...
         */

        val image = Image(content = base64Image)
        val request = Request(image = image, features = listOf(labelDetectionFeature, textDetectionFeature))
        return ImageInfoBodyRequest(requests = listOf(request))
    }

    private fun generateJSONBodyRequest(bodyRequest: ImageInfoBodyRequest): Any {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(ImageInfoBodyRequest::class.java)
        return adapter.toJson(bodyRequest)
    }
}