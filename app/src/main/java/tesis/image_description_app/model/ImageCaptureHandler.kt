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
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.viewModel.ApiViewModel
import tesis.image_description_app.viewModel.CameraViewModel
import java.io.ByteArrayOutputStream

class ImageCaptureHandler(private val cameraViewModel: CameraViewModel, private val apiViewModel: ApiViewModel) {

    private val imageRotator = ImageRotator()

    fun takePhoto(
        imageCapture: ImageCapture,
        executor: Executor,
        onImageCaptured: (ByteBuffer) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        imageCapture.takePicture(executor, object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                //se obtiene el primer plano de la imagen
                val buffer = image.planes[0].buffer
                cameraViewModel.closeCamera()
                onImageCaptured(buffer)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Error", "Error taking photo", exception)
                onError(exception)
            }
        })
    }

    //TODO cleancode a este metodo
     fun handleImageCapture(imageBytes: ByteBuffer) {
        val byteArray = ByteArray(imageBytes.remaining())
         imageBytes.get(byteArray)

         val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
         val outputStream = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.WEBP, 0, outputStream)


         val webpByteArray = outputStream.toByteArray()
         val base64WebPImage = Base64.encodeToString(webpByteArray, Base64.DEFAULT)
         this.apiViewModel.base64Image = base64WebPImage

        //se ejecuta en un hilo de fondo de manera concurrente xq utiliza corrutina
         //this.apiViewModel.requestImageInfo()


         //TODO que sea una sola llamada a un metodo
        val orientation = imageRotator.getImageOrientation(byteArray)
        val rotatedBitmap = imageRotator.rotateBitmap(bitmap, orientation)

        //TODO imagen sin rotar
        //cameraViewModel.imageBitmap = bitmap.asImageBitmap()
        this.cameraViewModel.imageBitmap = rotatedBitmap.asImageBitmap()
        this.cameraViewModel.showImage()
    }

}