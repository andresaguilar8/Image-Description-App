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

    private var apiResponse by mutableStateOf("")

    fun requestImageInfo(base64Image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageInformationLogicImpl.getImageInformation(base64Image).onSuccess { response ->
                println(response)
                imageDescriptionApiViewModel.requestImageDescription(response)
            }.onFailure { response ->
                //TODO manejar errores
                //la response va a ser un string, hacer que el speak la reproduzca

                println(response)
            }

        }
    }


}