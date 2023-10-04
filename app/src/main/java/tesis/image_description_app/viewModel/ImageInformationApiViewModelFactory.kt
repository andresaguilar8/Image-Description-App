package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.network.ImageInformationLogic

class ImageInformationApiViewModelFactory(
    private val textToSpeechViewModel: TextToSpeechViewModel,
    private val imageDescriptionApiViewModel: ImageDescriptionApiViewModel,
    private val imageInformationLogicImpl: ImageInformationLogic
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ImageInformationApiViewModel::class.java) {
            return ImageInformationApiViewModel(textToSpeechViewModel, imageDescriptionApiViewModel,imageInformationLogicImpl ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
