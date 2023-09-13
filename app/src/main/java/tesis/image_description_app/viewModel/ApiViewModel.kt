package tesis.image_description_app.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository

class ApiViewModel() : ViewModel() {

    var base64Image: String? by mutableStateOf(null)


    fun requestImageInfo() {
        val repository = ImageInfoRepository(GoogleVisionApiService.instance)
        viewModelScope.launch {
            base64Image?.let {
                repository.getImageInfo(it).onSuccess {
                    println()
                }.onFailure {
                    println()
                }
            }
        }
    }

    override fun onCleared() {
        //TODO probar esto. poner un Log.e y ver cuando se ejecuta
    }

}