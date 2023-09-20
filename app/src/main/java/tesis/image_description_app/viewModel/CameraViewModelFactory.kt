package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraViewModelFactory(private val imageInformationApiViewModel: ImageInformationApiViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CameraViewModel::class.java) {
            return CameraViewModel(imageInformationApiViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}