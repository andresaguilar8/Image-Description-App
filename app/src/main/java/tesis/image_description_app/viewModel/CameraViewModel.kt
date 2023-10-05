package tesis.image_description_app.viewModel

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.model.ImageCaptureHandler
import tesis.image_description_app.network.ImageDescriptionLogic
import tesis.image_description_app.network.ImageInformationLogic
import java.nio.ByteBuffer
import java.util.concurrent.Executor

class CameraViewModel(
    private val imageInformationLogicImpl: ImageInformationLogic,
    private val imageDescriptionLogicImpl: ImageDescriptionLogic
) : ViewModel() {

    private var processingImage = false
    private var hasImageResult = false
    private var cameraState = mutableStateOf(CameraState())
    private var captureImageCommand = mutableStateOf(false)
    private lateinit var imageCaptureHandler: ImageCaptureHandler
    private var imageDescriptionResult by  mutableStateOf("")


    fun shouldShowImage(): Boolean {
        return this.cameraState.value.shouldShowImage
    }

    fun shouldShowCamera(): Boolean {
        return this.cameraState.value.shouldShowCamera
    }

    fun isProcessingImage(): Boolean {
        return this.processingImage
    }

    fun cameraIsOpen(): Boolean {
        return this.cameraState.value.shouldShowCamera
    }

    fun setProcessingImageFinished() {
        this.processingImage = false
    }

    fun getBitmapImage(): ImageBitmap? {
        return this.imageCaptureHandler.getBitmapImage()
    }

    fun showImage() {
        println("muestra la imagen")
        this.updateCameraState(shouldShowImage = true, shouldShowCamera = false)
    }

    fun openCamera() {
        this.captureImageCommand.value = false
        this.updateCameraState(shouldShowImage = false, shouldShowCamera = true)
    }

    fun closeCamera() {
        this.updateCameraState(shouldShowImage = this.cameraState.value.shouldShowImage, shouldShowCamera = false)
    }

    private fun updateCameraState(shouldShowImage: Boolean, shouldShowCamera: Boolean) {
        val newCameraState = this.cameraState.value.copy(
            shouldShowImage = shouldShowImage,
            shouldShowCamera = shouldShowCamera
        )
        this.cameraState.value = newCameraState
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

    fun fetchForImageDescription(encodedImage: String) {
        viewModelScope.launch {
            runCatching {
                //val imageInformationResponse = imageInformationLogicImpl.getImageInformation(encodedImage)
                //val imageDescriptionResponse = imageDescriptionLogicImpl.getImageDescription(imageInformationResponse.getOrThrow())
                //imageDescriptionResult = imageDescriptionResponse.getOrThrow()
                hasImageResult = true
                imageDescriptionResult = "La imagen muestra jejeje"
            }.onFailure { throwable ->
                hasImageResult = true
                imageDescriptionResult = throwable.toString()
            }
        }
    }

    fun getImgDescriptionResult(): String {
        return this.imageDescriptionResult
    }

    fun onImageCaptureSuccess() {
        this.processingImage = true
    }

    fun activateTakePhotoCommand() {
        this.captureImageCommand.value = true
    }

    fun captureImageCommandActivated(): Boolean {
        return this.captureImageCommand.value
    }

    fun hasImageDescriptionResult(): Boolean {
        return this.hasImageResult
    }

    fun setImageCaptureHandler(imageCaptureHandler: ImageCaptureHandler) {
        this.imageCaptureHandler = imageCaptureHandler
    }

}