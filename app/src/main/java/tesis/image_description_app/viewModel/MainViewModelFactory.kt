package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val cameraViewModel: CameraViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(cameraViewModel, textToSpeechViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}