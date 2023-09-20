package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tesis.image_description_app.network.ChatGptApiService
import tesis.image_description_app.network.ImageDescriptionRepository

class ImageDescriptionApiViewModel : ViewModel() {

    private val imageDescriptionRepository = ImageDescriptionRepository(ChatGptApiService.instance)
    private var fetchingApi by mutableStateOf(false)
    var apiResponse by mutableStateOf("")

    fun requestImageDescription(parsedStringJson: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchingApi = true
            imageDescriptionRepository.getImageDescription(parsedStringJson).onSuccess { response ->
                apiResponse = response
                fetchingApi = false
                Log.e("TODO OK CHATGPT", "$apiResponse")
                println(apiResponse)
            }.onFailure { response ->
                //TODO manejar errores
                apiResponse = response.toString()
                fetchingApi = false
                Log.e("ERROR API CHATGPT", "$apiResponse")
            }

        }
    }


}