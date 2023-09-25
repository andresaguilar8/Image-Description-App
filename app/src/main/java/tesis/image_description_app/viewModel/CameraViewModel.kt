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

data class CameraState(
    var shouldShowImage: Boolean = false,
    var shouldShowCamera: Boolean = false
)

class CameraViewModel(
    private val imageInformationApiViewModel: ImageInformationApiViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel
) : ViewModel() {

    var processingImage: Boolean = false
    private var cameraState = mutableStateOf(CameraState())
    private var imageCaptureHandler: ImageCaptureHandler = ImageCaptureHandler(this)
    //TODO ver donde deberia ir imagebitmap
    var imageBitmap: ImageBitmap? = null

    fun shouldShowImage(): Boolean {
        return this.cameraState.value.shouldShowImage
    }

    fun showImage() {
        val newCameraState = this.cameraState.value.copy(
            shouldShowImage = true,
            shouldShowCamera = false)
        this.cameraState.value = newCameraState
    }

    fun changeCameraState() {
        val newCameraState = this.cameraState.value.copy(
            shouldShowImage = this.cameraState.value.shouldShowImage,
            shouldShowCamera = !this.cameraState.value.shouldShowCamera
        )
        this.cameraState.value = newCameraState
        if (this.cameraIsClosed())
            this.textToSpeechViewModel.speak("La cámara está cerrada.")
    }

    private fun cameraIsClosed(): Boolean {
        return !this.cameraState.value.shouldShowCamera
    }

    fun removeImagePreview() {
        if (this.cameraState.value.shouldShowImage && this.cameraState.value.shouldShowCamera) {
            this.imageBitmap = null
            val newCombinedState = this.cameraState.value.copy(
                shouldShowImage = false,
                shouldShowCamera = this.cameraState.value.shouldShowCamera
            )
            this.cameraState.value = newCombinedState
        }
    }

    fun shouldShowCamera(): Boolean {
        return this.cameraState.value.shouldShowCamera
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
        return this.processingImage
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