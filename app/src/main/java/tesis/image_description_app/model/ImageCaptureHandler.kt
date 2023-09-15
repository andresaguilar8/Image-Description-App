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

class ImageCaptureHandler(private val cameraViewModel: CameraViewModel, private val apiRequestViewModel: ApiViewModel) {

    private val imageDecoder = ImageRotator()

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

     fun handleImageCapture(imageBytes: ByteBuffer) {
        val byteArray = ByteArray(imageBytes.remaining())
        this.apiRequestViewModel.base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)
         //se ejecuta en un hilo de fondo de manera concurrente xq utiliza corrutina
         //this.apiRequestViewModel.requestImageInfo()
        imageBytes.get(byteArray)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        //TODO que sea una sola llamada a un metodo
        Log.e("EMPIEZA DE ROTAR", "EMPIEZA DE ROTAR")
        val orientation = imageDecoder.getImageOrientation(byteArray)
        val rotatedBitmap = imageDecoder.rotateBitmap(bitmap, orientation)
        Log.e("TERMINA DE ROTAR", "TERMINA DE ROTAR")
        //cameraViewModel.imageBitmap = bitmap.asImageBitmap()
        this.cameraViewModel.imageBitmap = rotatedBitmap.asImageBitmap()
        this.cameraViewModel.showImage()
    }

}