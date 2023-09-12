package tesis.image_description_app.model

import android.util.Base64
import java.nio.ByteBuffer
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.viewModel.MainViewModel

class ImageHandler(private val mainViewModel: MainViewModel) {
    fun handleImageCapture(bytes: ByteBuffer) {
        //TODO explicar en informe
        val byteArray = ByteArray(bytes.remaining())
        bytes.get(byteArray)
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        mainViewModel.imageBitmap = bitmap.asImageBitmap()
    }

    private fun convertByteBufferToBase64(byteBuffer: ByteBuffer): String {
        val byteArray = ByteArray(byteBuffer.remaining())
        byteBuffer.get(byteArray)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}