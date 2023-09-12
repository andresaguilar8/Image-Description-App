package tesis.image_description_app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//hacer inj de dependencias
class ApiViewModel() : ViewModel() {
    private val imageInfoRepository: ImageInfoRepository = ImageInfoRepository(GoogleVisionApiService.instance)
    private val imageDescriptionRepository: ImageDescriptionRepository = ImageDescriptionRepository(ChatGptApiService.instance)

    init {

        viewModelScope.launch {

            //API GOOGLE
            imageInfoRepository.getImageInfo().onSuccess {
                println()
            }.onFailure {
                println()
            }

            //API CHATGPT
            /*imageDescriptionRepository.getImageDescription().onSuccess {
                println()
            }.onFailure {
                println()
            }*/

        }
    }
}



