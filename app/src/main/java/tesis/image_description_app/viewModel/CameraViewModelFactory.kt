package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.data.network.ImageDescriptionLogic
import tesis.image_description_app.data.network.ImageInformationLogic

class CameraViewModelFactory(
    private val imageDescriptionViewModel: ImageDescriptionViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CameraViewModel::class.java) {
            return CameraViewModel(imageDescriptionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}