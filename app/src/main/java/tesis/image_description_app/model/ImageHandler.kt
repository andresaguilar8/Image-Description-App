package tesis.image_description_app.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64
import java.nio.ByteBuffer
import androidx.compose.ui.graphics.asImageBitmap
import tesis.image_description_app.viewModel.ApiViewModel
import tesis.image_description_app.viewModel.CameraViewModel
import java.io.ByteArrayInputStream
import java.io.IOException

class ImageHandler(private val cameraViewModel: CameraViewModel, private val apiRequestViewModel: ApiViewModel) {


    fun handleImageCapture(imageBytes: ByteBuffer) {
        val byteArray = ByteArray(imageBytes.remaining())
        imageBytes.get(byteArray)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val orientation = getImageOrientation(byteArray)
        val rotatedBitmap = rotateBitmap(bitmap, orientation)

       // cameraViewModel.imageBitmap = bitmap.asImageBitmap()
        cameraViewModel.imageBitmap = rotatedBitmap.asImageBitmap()
        apiRequestViewModel.base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)
        cameraViewModel.showImage()
    }



    private fun getImageOrientation(imageBytes: ByteArray): Int {
        try {
            val inputStream = ByteArrayInputStream(imageBytes)
            val exifInterface = ExifInterface(inputStream)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            inputStream.close()
            return orientation
        } catch (e: IOException) {
            e.printStackTrace()
            //returns normal orientation
            return ExifInterface.ORIENTATION_NORMAL
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }

        return try {
            val rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            rotatedBitmap
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            bitmap
        }
    }
}