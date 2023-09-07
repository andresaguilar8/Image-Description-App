package tesis.image_description_app.model

import android.util.Base64
import android.util.Log
import java.nio.ByteBuffer

class ImageHandler {


    fun handleImageCapture(bytes: ByteBuffer) {
        Log.i("Image", "Image captured $bytes")
        val base64 = convertByteBufferToBase64(bytes)
        Log.d("IMAGE EN BASE 64", base64)
        //shouldShowCamera.value = false
        //shouldShowPhoto.value = true
    }

    private fun convertByteBufferToBase64(byteBuffer: ByteBuffer): String {
        val byteArray = ByteArray(byteBuffer.remaining())
        byteBuffer.get(byteArray)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}