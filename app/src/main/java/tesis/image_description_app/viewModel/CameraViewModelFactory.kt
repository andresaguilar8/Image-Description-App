package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraViewModelFactory(private val apiViewModel: ApiViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CameraViewModel::class.java) {
            return CameraViewModel(apiViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}