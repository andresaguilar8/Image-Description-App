package tesis.image_description_app.viewModel

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.ImageCaptureHandler
import java.nio.ByteBuffer
import java.util.concurrent.Executor

class CameraViewModel(apiViewModel: ApiViewModel):ViewModel() {
    private var shouldShowImage by mutableStateOf(false)
    private var cameraOpened by mutableStateOf(false)
    //TODO ver donde deberia ir imagebitmap
    var imageBitmap: ImageBitmap? by mutableStateOf(null)
    private var imageCaptureHandler: ImageCaptureHandler = ImageCaptureHandler(this, apiViewModel)


    fun shouldShowImage(): Boolean {
        return this.shouldShowImage
    }
    fun showImage() {
        Log.e("ahora muestra imagen", "ahora muestra imagen")
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
    fun takePhoto(imageCapture: ImageCapture, executor: Executor, onImageCaptured:  (ByteBuffer) -> Unit, onError: (ImageCaptureException) -> Unit) {
        imageCaptureHandler.takePhoto(
            imageCapture = imageCapture,
            executor = executor,
            onImageCaptured = onImageCaptured,
            onError = onError
        )
    }

    fun onImageCapture(imageBytes: ByteBuffer) {
        this.imageCaptureHandler.handleImageCapture(imageBytes)
    }
}