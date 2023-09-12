package tesis.image_description_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository

class ApiRequestViewModel(cameraViewModel: CameraViewModel) : ViewModel() {

    //var base64Image: String = ""


    fun requestImageInfo(base64Image: String) {
        val repository = ImageInfoRepository(GoogleVisionApiService.instance)
        viewModelScope.launch {
            repository.getImageInfo().onSuccess {
                println()
            }.onFailure {
                println()
            }
        }
    }

}