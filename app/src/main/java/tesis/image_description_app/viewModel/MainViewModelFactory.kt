package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.SpeechSynthesizer

class MainViewModelFactory(
    private val cameraViewModel: CameraViewModel,
    private val imageViewModel: ImageDescriptionViewModel,
    private val speechSynthesizer: SpeechSynthesizer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(cameraViewModel, imageViewModel, speechSynthesizer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}