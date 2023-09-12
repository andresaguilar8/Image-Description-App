package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.ImageHandler

class MainViewModel:ViewModel() {
    var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    var imageBitmap: ImageBitmap? by mutableStateOf(null)
    var cameraOpened by mutableStateOf(false)
        private set

    //TODO acomodar
    fun changeShowFotoState() {
        shouldShowPhoto.value = !shouldShowPhoto.value
    }

    fun changeCameraState() {
        cameraOpened = !cameraOpened
        if (cameraOpened) {
            imageBitmap = null
        }
    }

    fun closeCamera() {
        cameraOpened = false
    }
}