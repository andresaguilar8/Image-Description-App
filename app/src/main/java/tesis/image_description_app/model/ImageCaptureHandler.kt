package tesis.image_description_app.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.R
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.ImageDescriptionViewModel
import tesis.image_description_app.viewModel.MainViewModel
import java.io.ByteArrayOutputStream

class ImageCaptureHandler(
    private val imageRotator: ImageRotator,
    private val context: Context
) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var imageViewModel: ImageDescriptionViewModel
    private var imageBitmap: ImageBitmap? = null
    private lateinit var encodedImage: String

    fun setViewModels(mainViewModel: MainViewModel, cameraViewModel: CameraViewModel, imageViewModel: ImageDescriptionViewModel) {
        this.mainViewModel = mainViewModel
        this.cameraViewModel = cameraViewModel
        this.imageViewModel = imageViewModel
    }

    fun takePhoto(
        imageCapture: ImageCapture,
        executor: Executor,
        onImageCaptured: (ByteBuffer) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        imageCapture.takePicture(executor, object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                //se obtiene el primer plano de la imagen
                val imagePixelsBuffer = image.planes[0].buffer
                cameraViewModel.onImageCaptureSuccess()
                mainViewModel.notifyEventToUser(context.getString(R.string.captured_image))
                mainViewModel.notifyEventToUser(context.getString(R.string.img_being_processed))
                onImageCaptured(imagePixelsBuffer)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        })
    }

    //TODO  cleancode a este metodo
     fun handleImageCapture(imageBytes: ByteBuffer) {
        val byteArray = ByteArray(imageBytes.remaining())
        imageBytes.get(byteArray)
        val imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        val outputStreamByteArray = this.getCompressedImageByteArray(imageBitmap)
        this.encodedImage = this.encodeImage(outputStreamByteArray)
        this.imageViewModel.fetchForImageDescription(this.encodedImage)
        val rotatedBitmap = this.imageRotator.getRotatedBitmap(byteArray, imageBitmap)
        if (rotatedBitmap != null)
            this.imageBitmap = rotatedBitmap.asImageBitmap()
        this.cameraViewModel.showImage()
    }

    private fun getCompressedImageByteArray(bitmap: Bitmap): ByteArrayOutputStream {
        var outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        return outputStream
    }

    private fun encodeImage(outputStreamByteArray: ByteArrayOutputStream): String {
        val webpByteArray = outputStreamByteArray.toByteArray()
        return Base64.encodeToString(webpByteArray, Base64.DEFAULT)
    }

    fun getBitmapImage(): ImageBitmap? {
        return this.imageBitmap
    }

    fun getEncodedImage(): String {
        return this.encodedImage
    }

    fun clearImageInfo() {
        this.imageBitmap = null
        this.encodedImage = ""
    }

    fun hasCapturedImage(): Boolean {
        return this.encodedImage != ""
    }

}