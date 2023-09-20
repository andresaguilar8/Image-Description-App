package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageInformationApiViewModelFactory(private val imageDescriptionApiViewModel: ImageDescriptionApiViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ImageInformationApiViewModel::class.java) {
            return ImageInformationApiViewModel(imageDescriptionApiViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
