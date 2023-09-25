package tesis.image_description_app.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.viewModel.ImageInformationApiViewModel
import tesis.image_description_app.viewModel.CameraViewModel
import java.io.ByteArrayOutputStream

class ImageCaptureHandler(private val cameraViewModel: CameraViewModel) {

    private val imageRotator = ImageRotator()
  //  private var processingImage: Boolean = false
    private var imageBitmap: ImageBitmap? = null
    private lateinit var encodedImage: String

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
                //TODO cerrar la camara para dejar de ver la preview
                cameraViewModel.changeCameraState()
                onImageCaptured(imagePixelsBuffer)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                //TODO
                cameraViewModel.onImageCaptureError(exception)
                onError(exception)
            }
        })
    }

    //TODO cleancode a este metodo
     fun handleImageCapture(imageBytes: ByteBuffer) {
        this.cameraViewModel.processingImage = true

        val byteArray = ByteArray(imageBytes.remaining())
        imageBytes.get(byteArray)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        this.cameraViewModel.handleImageCompression(bitmap)
        val rotatedBitmap = imageRotator.getRotatedBitmap(byteArray, bitmap)
        if (rotatedBitmap != null) {
            this.cameraViewModel.imageBitmap = rotatedBitmap.asImageBitmap()
        }

        this.cameraViewModel.processingImage = false

        this.cameraViewModel.showImage()
    }


//    fun isProcessingImage(): Boolean {
//        return this.processingImage
//    }

    fun getImageBitmap(): ImageBitmap? {
        return this.imageBitmap
    }

    fun compressImage(bitmap: Bitmap) {
        //en outputStream se escriben los datos compresos
        val outputStreamByteArray = this.getCompressedImageByteArray(bitmap)
        this.encodedImage = this.decodeImage(outputStreamByteArray)
    }

    private fun getCompressedImageByteArray(bitmap: Bitmap): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 45, outputStream)
        return outputStream
    }

    private fun decodeImage(outputStreamByteArray: ByteArrayOutputStream): String {
        val webpByteArray = outputStreamByteArray.toByteArray()
        return Base64.encodeToString(webpByteArray, Base64.DEFAULT)
    }

    fun getEncodedImage(): String {
        return this.encodedImage
    }

}