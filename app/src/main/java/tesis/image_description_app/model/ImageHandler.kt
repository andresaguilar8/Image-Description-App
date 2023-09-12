package tesis.image_description_app.model

import android.util.Base64
import java.nio.ByteBuffer
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.viewModel.ApiRequestViewModel
import tesis.image_description_app.viewModel.CameraViewModel

class ImageHandler(private val cameraViewModel: CameraViewModel, private val apiRequestViewModel: ApiRequestViewModel) {

    private lateinit var imageBytes: ByteBuffer

    fun handleImageCapture(imageBytes: ByteBuffer) {
        //TODO explicar en informe
        this.imageBytes = imageBytes
        val byteArray = ByteArray(imageBytes.remaining())
        imageBytes.get(byteArray)
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        cameraViewModel.imageBitmap = bitmap.asImageBitmap()
        apiRequestViewModel.base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}