package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionAPI
import tesis.image_description_app.network.ImageInformationLogic
import tesis.image_description_app.network.ImageInformationLogicImpl

class ImageInformationApiViewModel(
    private val imageDescriptionApiViewModel: ImageDescriptionApiViewModel,
    private val imageInformationLogicImpl: ImageInformationLogic
) : ViewModel() {

    private var fetchingApi by mutableStateOf(false)
    var apiResponse by mutableStateOf("")

    fun requestImageInfo(base64Image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchingApi = true
            imageInformationLogicImpl.getImageInformation(base64Image).onSuccess { response ->
                apiResponse = response
                println(apiResponse)
                imageDescriptionApiViewModel.requestImageDescription(apiResponse)
                fetchingApi = false
            }.onFailure { response ->
                //TODO manejar errores
                apiResponse = response.toString()
                Log.e("Error", "Google API response error: , $apiResponse")
                fetchingApi = false
            }
        }

    }

    fun cleanApiResponse() {
        this.apiResponse = ""
    }

}