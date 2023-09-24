package tesis.image_description_app.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONArray
import org.json.JSONObject
import tesis.image_description_app.data.imageInformation.request.*

class ImageInfoRepository(private val googleApiService: GoogleVisionApiService) {

    suspend fun getImageInfo(base64Image: String): Result<String> {
        val bodyRequest = generateBodyRequest(base64Image)
        val jsonBodyRequest = generateJSONBodyRequest(bodyRequest)

        return try {
            val response = googleApiService.fetchForImageInformation(jsonBodyRequest as String)
            var jsonStringToReturn = ""

            if (response.isSuccessful) {
                var jsonFromAPI = response.body()
                if (jsonFromAPI != null) {
                    val jsonObject = JSONObject(jsonFromAPI)
                    val responsesArray = jsonObject.getJSONArray("responses")
                    val responsesArrayAsString = responsesArray.toString()

                    if (this.stringIsTooBig(responsesArrayAsString)) {
                        val smallerJsonObject = this.getSmallJsonObject(responsesArray)
                        jsonStringToReturn = smallerJsonObject.toString()
                    }
                    else {
                        jsonStringToReturn = responsesArrayAsString
                    }
                    println("imprimo el json final")
                    println(jsonStringToReturn)
                     // jsonStringToReturn = generateStringFromJSONObject(jsonObject)

                }
            }
            else {
                Log.e("Google API bad request", "")
            }
            Result.success(jsonStringToReturn)
        }
        catch (exception: Exception) {
            Log.e("Google API exception", "$exception")
            Result.failure(exception)
        }
    }

   

    private fun stringIsTooBig(responsesArrayAsString: String): Boolean {
        var stringIsTooBig = false
        if (responsesArrayAsString.length > 8000) {
            stringIsTooBig =  true
        }
        println("Longitud del json (en la RESPONSE): ${responsesArrayAsString.length}")
        return stringIsTooBig
    }

    private fun getSmallJsonObject(responsesArray: JSONArray): JSONObject? {
//        removeUnnecessaryImageInformation

        //va a ser el objeto con todas las features
        val responsesFirstObject = responsesArray.getJSONObject(0)
        val textAnnotationsArray: JSONArray? = responsesFirstObject?.optJSONArray("textAnnotations")
        val fullTextAnnotationsObject: JSONObject? = responsesFirstObject?.optJSONObject("fullTextAnnotation")

        if (textAnnotationsArray != null) {
            responsesFirstObject.remove("textAnnotations")
            Log.e("remueve textAnott", "")
        }
        if (fullTextAnnotationsObject != null) {
            responsesFirstObject.remove("fullTextAnnotation")
            Log.e("remueve fullTextAnnotation", "")
        }

        return responsesFirstObject
    }

    private fun generateStringFromJSONObject(jsonObject: JSONObject): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(JSONObject::class.java)
        return adapter.toJson(jsonObject)
    }

    /*private fun generateStringFromObject(imageInformationResponse: ImageInformationResponse): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(ImageInformationResponse::class.java)
        return adapter.toJson(imageInformationResponse)
    }*/

    private fun generateBodyRequest(base64Image: String): ImageInfoBodyRequest {
        //TODO ver cuales sacar
        val labelDetectionFeature = Feature(maxResults = 10, type = "LABEL_DETECTION")
        val textDetectionFeature = Feature(type = "TEXT_DETECTION")
        //TODO chequear fulltext
        val fullTextDetectionFeature = Feature(type = "DOCUMENT_TEXT_DETECTION")
        val faceDetectionFeature = Feature(type = "FACE_DETECTION")
        val landmarkDetectionFeature = Feature(type = "LANDMARK_DETECTION")
        val logoDetectionFeature = Feature(type = "LOGO_DETECTION")
        val productSearchDetectionFeature = Feature(type = "PRODUCT_SEARCH")
        val objectLocalizationDetectionFeature = Feature(type = "OBJECT_LOCALIZATION")
        val cropHintsDetectionFeature = Feature(type = "CROP_HINTS")
        val imagePropertiesDetectionFeature = Feature(type = "IMAGE_PROPERTIES")
        val webDetectionFeature = Feature(type = "WEB_DETECTION")

      val image = Image(content = base64Image)

     //   val image = Image(content = this.getHarcodedBase64Image())
        val request = Request(image = image, features = listOf(
            faceDetectionFeature,
            landmarkDetectionFeature,
            logoDetectionFeature,
            labelDetectionFeature,
            objectLocalizationDetectionFeature,
            textDetectionFeature,
            fullTextDetectionFeature,
            imagePropertiesDetectionFeature,
            cropHintsDetectionFeature,
            webDetectionFeature,
            productSearchDetectionFeature,
            //TODO poner error...
        ))
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