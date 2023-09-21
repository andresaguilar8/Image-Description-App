package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.SpeechSynthesizer

class CameraViewModelFactory(
    private val imageInformationApiViewModel: ImageInformationApiViewModel,
    private val speechToSpeechViewModel: TextToSpeechViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CameraViewModel::class.java) {
            return CameraViewModel(imageInformationApiViewModel, speechToSpeechViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}