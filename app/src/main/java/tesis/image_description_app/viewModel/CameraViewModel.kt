package tesis.image_description_app.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.model.ImageCaptureHandler
import java.nio.ByteBuffer
import java.util.concurrent.Executor

data class CombinedState(
    var shouldShowImage: Boolean = false,
    var cameraOpened: Boolean = false
)

class CameraViewModel(
    private val imageInformationApiViewModel: ImageInformationApiViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel
) : ViewModel() {

    private var combinedState = mutableStateOf(CombinedState())
    private var imageCaptureHandler: ImageCaptureHandler = ImageCaptureHandler(this)
    //TODO ver donde deberia ir imagebitmap
    var imageBitmap: ImageBitmap? = null

    fun shouldShowImage(): Boolean {
        return this.combinedState.value.shouldShowImage
    }

    fun showImage() {
        val newCombinedState = this.combinedState.value.copy(
            shouldShowImage = true,
            cameraOpened = false)
        this.combinedState.value = newCombinedState
    }

    fun changeCameraState() {
        val newCombinedState = this.combinedState.value.copy(
            shouldShowImage = this.combinedState.value.shouldShowImage,
            cameraOpened = !this.combinedState.value.cameraOpened)

        this.combinedState.value = newCombinedState
        if (this.combinedState.value.shouldShowImage && this.combinedState.value.cameraOpened)
            this.removeImagePreview()
    }

    private fun removeImagePreview() {
        val newCombinedState = this.combinedState.value.copy(
            shouldShowImage = false,
            cameraOpened = this.combinedState.value.cameraOpened)
        this.combinedState.value = newCombinedState
        this.imageBitmap = null
    }

    fun closeCamera() {
        //this.cameraOpened = false
    }

    fun shouldShowCamera(): Boolean {
        return this.combinedState.value.cameraOpened
    }

    fun takePhoto(imageCapture: ImageCapture,
                  executor: Executor,
                  onImageCaptured:  (ByteBuffer) -> Unit,
                  onError: (ImageCaptureException) -> Unit
    ) {
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

    fun handleImageCompression(bitmap: Bitmap) {
        viewModelScope.launch() {
            imageCaptureHandler.compressImage(bitmap)
            val base64Image = imageCaptureHandler.getEncodedImage()
            imageInformationApiViewModel.requestImageInfo(base64Image)
        }
    }

    fun onImageCaptureSuccess() {
        this.textToSpeechViewModel.speak("Imagen capturada. La imagen está siendo procesada")
    }

    fun onImageCaptureError(imageCaptureException: ImageCaptureException) {
        Log.e("Error", "Error taking photo", imageCaptureException)
    }

}