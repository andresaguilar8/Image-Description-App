package tesis.image_description_app.view_model

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.image_processing.ImageCaptureHandler
import java.nio.ByteBuffer
import java.util.concurrent.Executor

class CameraViewModel(
    private val imageDescriptionViewModel: ImageDescriptionViewModel
): ViewModel() {

    private var cameraState = mutableStateOf(CameraState())
    private lateinit var imageCaptureHandler: ImageCaptureHandler
    private var captureImageCommand = mutableStateOf(false)

    fun setImageCaptureHandler(imageCaptureHandler: ImageCaptureHandler) {
        this.imageCaptureHandler = imageCaptureHandler
    }

    fun shouldShowImage(): Boolean {
        return this.cameraState.value.shouldShowImage
    }

    fun shouldShowCamera(): Boolean {
        return this.cameraState.value.shouldShowCamera
    }

    fun openCamera() {
        this.captureImageCommand.value = false
        this.imageCaptureHandler.clearImageInfo()
        this.updateCameraState(shouldShowImage = false, shouldShowCamera = true)
    }

    fun closeCamera() {
        this.updateCameraState(shouldShowImage = this.cameraState.value.shouldShowImage, shouldShowCamera = false)
    }

    fun cameraIsOpen(): Boolean {
        return this.cameraState.value.shouldShowCamera
    }

    fun getBitmapImage(): ImageBitmap? {
        return this.imageCaptureHandler.getBitmapImage()
    }

    fun showImage() {
        this.updateCameraState(shouldShowImage = true, shouldShowCamera = false)
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

    fun onImageCaptureSuccess() {
        this.updateCameraState(this.cameraState.value.shouldShowImage, false)
        this.imageDescriptionViewModel.setProcessingImage()
    }

    fun activateTakePhotoCommand() {
        this.captureImageCommand.value = true
    }

    fun captureImageCommandActivated(): Boolean {
        return this.captureImageCommand.value
    }

    fun getEncodedImage(): String {
        return this.imageCaptureHandler.getEncodedImage()
    }

    fun hasCapturedImage(): Boolean {
        return this.imageCaptureHandler.hasCapturedImage()
    }

}