package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageDescriptionApiViewModelFactory(
    private val textToSpeechViewModel: TextToSpeechViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ImageDescriptionApiViewModel::class.java) {
            return ImageDescriptionApiViewModel(textToSpeechViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}