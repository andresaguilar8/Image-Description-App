package tesis.image_description_app.view

import tesis.image_description_app.network.GoogleVisionApiService
import tesis.image_description_app.network.ImageInfoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: ImageInfoRepository = ImageInfoRepository(GoogleVisionApiService.instance)
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getImageInfo().onSuccess {
                println()
            }.onFailure {
                println()
            }
        }
    }
}