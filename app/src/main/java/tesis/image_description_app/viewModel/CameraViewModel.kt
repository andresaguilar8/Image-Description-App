package tesis.image_description_app.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel

class CameraViewModel:ViewModel() {
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    var imageBitmap: ImageBitmap? by mutableStateOf(null)
    var cameraOpened by mutableStateOf(false)
        private set

    fun shouldShowPhoto(): Boolean {
        return imageBitmap != null
    }
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