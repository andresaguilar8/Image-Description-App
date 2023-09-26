package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository

class ImageInformationApiViewModel(private val imageDescriptionApiViewModel: ImageDescriptionApiViewModel) : ViewModel() {

    private val imageInfoRepository = ImageInfoRepository(GoogleVisionApiService.instance)

    private var fetchingApi by mutableStateOf(false)
    var apiResponse by mutableStateOf("")

    fun requestImageInfo(base64Image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchingApi = true
            imageInfoRepository.getImageInfo(base64Image).onSuccess { response ->
                apiResponse = response
                imageDescriptionApiViewModel.requestImageDescription(apiResponse)
                fetchingApi = false
            }.onFailure { response ->
                //TODO manejar errores
                apiResponse = response.toString()
                Log.e("error", "error en la response a la api degoogle, $apiResponse")
                fetchingApi = false
            }
        }

    }


    private fun modifyJson(): String {
        val originalJson = this.apiResponse //.trimIndent()

        val modifiedJson = originalJson
            //.replace("\"", "'")
            //.replace("\n", "")
            //.replace(" ", "")

        return modifiedJson
    }

    fun isFetchingApi(): Boolean {
        return this.fetchingApi
    }

    fun cleanApiResponse() {
        this.apiResponse = ""
    }

}