package tesis.image_description_app.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel

class CameraViewModel:ViewModel() {
    private var shouldShowImage by mutableStateOf(false)
    private var cameraOpened by mutableStateOf(false)
    //TODO ver donde deberia ir imagebitmap
    var imageBitmap: ImageBitmap? by mutableStateOf(null)

    fun shouldShowImage(): Boolean {
        return this.shouldShowImage
    }
    fun showImage() {
        this.shouldShowImage = true
    }
    fun changeCameraState() {
        this.cameraOpened = !this.cameraOpened
        if (this.cameraOpened && this.shouldShowImage) {
            this.removeImagePreview()
        }
    }
    private fun removeImagePreview() {
        this.shouldShowImage = false
        this.imageBitmap = null
    }
    fun closeCamera() {
        this.cameraOpened = false
    }

    fun shouldShowCamera(): Boolean {
        return this.cameraOpened
    }

}