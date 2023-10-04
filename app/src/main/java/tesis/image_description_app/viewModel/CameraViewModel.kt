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

class CameraViewModel(
    private val imageInformationApiViewModel: ImageInformationApiViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel,
) : ViewModel() {

    private var processingImage = false
    private var cameraState = mutableStateOf(CameraState())
    var imageTakeCommand = mutableStateOf(false)
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

    fun shouldShowCamera(): Boolean {
        return this.cameraState.value.shouldShowCamera
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
        this.closeCamera()
        this.imageCaptureHandler.handleImageCapture(imageBytes)
    }

    fun isProcessingImage(): Boolean {
        return this.processingImage
    }

    fun handleImageCompression(bitmap: Bitmap) {
        viewModelScope.launch {
            imageCaptureHandler.compressImage(bitmap)
            val base64Image = imageCaptureHandler.getEncodedImage()
            //imageInformationApiViewModel.requestImageInfo(base64Image)
        }
    }

    fun onImageCaptureSuccess() {
        this.textToSpeechViewModel.speak("Imagen capturada. La imagen está siendo procesada.")
        this.processingImage = true
    }

    fun onImageCaptureError() {
        this.textToSpeechViewModel.speak("Ocurrió un error al capturar la imagen. Por favor vuelve a intentar.")
    }

    fun activateTakePhotoCommand() {
        if (this.cameraIsOpen()) {
            this.imageTakeCommand.value = true
        }
        //TODO "decir camara no está abierta"
    }

    private fun cameraIsOpen(): Boolean {
        return this.cameraState.value.shouldShowCamera
    }

    fun closeCamera() {
        val newCameraState = this.cameraState.value.copy(
            shouldShowImage = this.cameraState.value.shouldShowImage,
            shouldShowCamera = false
        )
        this.cameraState.value = newCameraState
    }

    fun openCamera() {
        this.imageTakeCommand.value = false
        val newCameraState = this.cameraState.value.copy(
            shouldShowImage = false,
            shouldShowCamera = true
        )
        this.cameraState.value = newCameraState
    }

    fun setProcessingImageFinished() {
        this.processingImage = false
    }

    fun getBitmapImage(): ImageBitmap? {
        return this.imageBitmap
    }
}