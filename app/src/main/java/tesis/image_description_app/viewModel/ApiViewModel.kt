package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository

class ApiViewModel() : ViewModel() {

    private var fetchingApi by mutableStateOf(false)
    //TODO lateint
    lateinit var base64Image: String
    var apiResponse by mutableStateOf("")

    fun requestImageInfo() {
        val repository = ImageInfoRepository(GoogleVisionApiService.instance)
        viewModelScope.launch {
            fetchingApi = true
            base64Image?.let {
                repository.getImageInfo(it).onSuccess { response ->
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
    override fun onCleared() {
        //TODO probar esto. poner un Log.e y ver cuando se ejecuta
    }

    fun cleanApiResponse() {
        this.apiResponse = ""
    }

}