package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.network.ImageDescriptionLogic

class ImageDescriptionApiViewModelFactory(
    private val textToSpeechViewModel: TextToSpeechViewModel,
    private val imageDescriptionLogicImpl: ImageDescriptionLogic
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ImageDescriptionApiViewModel::class.java) {
            return ImageDescriptionApiViewModel(textToSpeechViewModel, imageDescriptionLogicImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}