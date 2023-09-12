package tesis.image_description_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tesis.image_description_app.data.imageInformation.response.Response
import tesis.image_description_app.data.imageInformation.request.*

class ImageInfoRepository(private val googleApi: GoogleVisionApiService) {

    suspend fun getImageInfo(): Result<Response> {

       val feature = Feature(maxResults = 10, type = "LABEL_DETECTION")

        val source = Source(imageUri = "https://upload.wikimedia.org/wikipedia/commons/7/76/Pagina_web_autoreferente.jpg")

        val image = Image(source = source)

        val request = Request(image = image, features = listOf(feature) )

        val bodyRequest = ImageInformationBodyRequest(requests = listOf(request))

        //Prueba para ver el json serializado
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(ImageInformationBodyRequest::class.java)
        val json = adapter.toJson(bodyRequest)
        println(json)


        return try {
            val response = googleApi.fetchForImageInformation(bodyRequest)
             println(response)
            val imageInfo = response.responses.first()
            Result.success(imageInfo)
        }
        catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}

/*

        val body = "{" +
                "  \"requests\": [" +
                "    {" +
                "      \"image\": {" +
                "        \"source\": {" +
                "          \"imageUri\": \"https://upload.wikimedia.org/wikipedia/commons/7/76/Pagina_web_autoreferente.jpg\"" +
                "        }" +
                "      }," +
                "      \"features\": [" +
                "        {" +
                "          \"type\": \"LABEL_DETECTION\"," +
                "          \"maxResults\": 10" +
                "        }," +
                "        {" +
                "          \"type\": \"TEXT_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"FACE_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"LANDMARK_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"LOGO_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"SAFE_SEARCH_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"DOCUMENT_TEXT_DETECTION\"" +
                "        }," +
                "        {" +
                "          \"type\": \"IMAGE_PROPERTIES\"" +
                "        }," +
                "        {" +
                "          \"type\": \"PRODUCT_SEARCH\"" +
                "        }," +
                "        {" +
                "          \"type\": \"OBJECT_LOCALIZATION\"" +
                "        }" +
                "      ]" +
                "    }" +
                "  ]" +
                "}"
 */