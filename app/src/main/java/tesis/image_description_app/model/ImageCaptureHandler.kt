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
import tesis.image_description_app.viewModel.ApiViewModel
import tesis.image_description_app.viewModel.CameraViewModel
import java.io.ByteArrayOutputStream

class ImageCaptureHandler(private val cameraViewModel: CameraViewModel, private val apiViewModel: ApiViewModel) {

    private val imageRotator = ImageRotator()
    private var processingImage: Boolean = false
    private var imageBitmap: ImageBitmap? = null

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
                cameraViewModel.closeCamera()
                onImageCaptured(imagePixelsBuffer)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                //TODO
                Log.e("Error", "Error taking photo", exception)
                onError(exception)
            }
        })
    }

    //TODO cleancode a este metodo
     fun handleImageCapture(imageBytes: ByteBuffer) {
        this.processingImage = true
        val byteArray = ByteArray(imageBytes.remaining())
        imageBytes.get(byteArray)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        this.cameraViewModel.handleImageCompression(bitmap)

        val orientation = imageRotator.getImageOrientation(byteArray)
        val rotatedBitmap = imageRotator.rotateBitmap(bitmap, orientation)
        this.cameraViewModel.imageBitmap = rotatedBitmap.asImageBitmap()
        this.processingImage = false
        this.cameraViewModel.showImage()
    }


    fun isProcessingImage(): Boolean {
        return this.processingImage
    }

    fun getImageBitmap(): ImageBitmap? {
        return this.imageBitmap
    }

    fun compressImage(bitmap: Bitmap) {
        //en outputStream se escriben los datos compresos
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 45, outputStream)
        val webpByteArray = outputStream.toByteArray()
        val base64Image = Base64.encodeToString(webpByteArray, Base64.DEFAULT)
        apiViewModel.requestImageInfo(base64Image)
    }
}