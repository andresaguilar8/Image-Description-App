package tesis.image_description_app.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository

class ApiViewModel : ViewModel() {

    private val imageInfoRepository = ImageInfoRepository(GoogleVisionApiService.instance)
    private var fetchingApi by mutableStateOf(false)
    lateinit var base64Image: String
    var apiResponse by mutableStateOf("")

    fun requestImageInfo() {
        viewModelScope.launch {
            fetchingApi = true
            base64Image?.let {
                imageInfoRepository.getImageInfo(it).onSuccess { response ->
                    apiResponse = response
                    fetchingApi = false
                }.onFailure { response ->
                    //TODO manejar errores
                    apiResponse = response.toString()
                    fetchingApi = false
                }
            }
        }
    }

    fun isFetchingApi(): Boolean {
        return this.fetchingApi
    }

    fun cleanApiResponse() {
        this.apiResponse = ""
    }

}