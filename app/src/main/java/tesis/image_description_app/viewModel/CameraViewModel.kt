package tesis.image_description_app.viewModel

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.ImageCaptureHandler
import java.nio.ByteBuffer
import java.util.concurrent.Executor

class CameraViewModel(apiViewModel: ApiViewModel) : ViewModel() {

    private var shouldShowImage by mutableStateOf(false)
    private var cameraOpened by mutableStateOf(false)
    private var imageCaptureHandler: ImageCaptureHandler = ImageCaptureHandler(this, apiViewModel)
    //TODO ver donde deberia ir imagebitmap
    var imageBitmap: ImageBitmap? = null


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

    fun isProcessingImage(): Boolean {
        return this.imageCaptureHandler.isProcessingImage()
    }

}