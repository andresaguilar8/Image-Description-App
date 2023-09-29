package tesis.image_description_app.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONArray
import org.json.JSONObject
import tesis.image_description_app.data.imageInformation.request.*

class ImageInformationLogicImpl : ImageInformationLogic {

    override suspend fun getImageInformation(base64Image: String): Result<String> {
        val googleVisionAPI = GoogleVisionAPI.instance

        val bodyRequest = generateBodyRequest(base64Image)
        val jsonBodyRequest = generateJSONBodyRequest(bodyRequest)

        return try {
            val response = googleVisionAPI.fetchForImageInformation(jsonBodyRequest as String)
            var jsonStringToReturn = ""

            if (response.isSuccessful) {
                var jsonFromAPI = response.body()
                if (jsonFromAPI != null) {
                    val jsonObject = JSONObject(jsonFromAPI)
                    val responsesArray = jsonObject.getJSONArray("responses")
                    val responsesArrayAsString = responsesArray.toString()

                    jsonStringToReturn = if (this.stringIsTooBig(responsesArrayAsString)) {
                        val smallerJsonObject = this.getSmallJsonObject(responsesArray)
                        smallerJsonObject.toString()
                    } else {
                        responsesArrayAsString
                    }
                }
            }
            else {
                //TODO
                Log.e("Google API bad request", "")
            }
            Result.success(jsonStringToReturn)
        }
        catch (exception: Exception) {
            //TODO
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
        //features object
        val responsesFirstObject = responsesArray.getJSONObject(0)
        var textAnnotationsArray: JSONArray? = responsesFirstObject?.optJSONArray("textAnnotations")
        val fullTextAnnotationsObject: JSONObject? = responsesFirstObject?.optJSONObject("fullTextAnnotation")

        if (textAnnotationsArray != null) {
            val newTextAnnotationsArray = JSONArray()
            newTextAnnotationsArray.put(textAnnotationsArray[0])
            responsesFirstObject.remove("textAnnotations")
            responsesFirstObject.put("textAnnotations", newTextAnnotationsArray)
        }
        if (fullTextAnnotationsObject != null) {
            responsesFirstObject.remove("fullTextAnnotation")
            Log.e("remueve fullTextAnnotation", "")
        }

        return responsesFirstObject
    }


    private fun generateBodyRequest(base64Image: String): ImageInfoBodyRequest {
        val labelDetectionFeature = Feature(maxResults = 10, type = "LABEL_DETECTION")
        val textDetectionFeature = Feature(type = "TEXT_DETECTION")
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