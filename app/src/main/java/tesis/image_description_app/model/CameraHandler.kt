package tesis.image_description_app.model

import android.util.Log
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy

class CameraHandler {

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
                onImageCaptured(buffer)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Error", "Error taking photo", exception)
                onError(exception)
            }
        })
    }
}