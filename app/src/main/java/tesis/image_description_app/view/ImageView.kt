package tesis.image_description_app.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import tesis.image_description_app.R
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.MainViewModel

@Composable
fun CameraPreviewOrImage(
    context: Context,
    cameraViewModel: CameraViewModel,
    mainViewModel: MainViewModel
) {

    if (cameraViewModel.shouldShowCamera()) {
        OpenCamera(
            context,
            cameraViewModel,
            mainViewModel
        )
    }
    else
        if (cameraViewModel.shouldShowImage())
            ShowImage(imageBitmap = cameraViewModel.getBitmapImage()) {
                mainViewModel.notifyEventToUser(context.getString(R.string.show_captured_image_error))
            }
}
@Composable
fun ShowImage(
    imageBitmap: ImageBitmap?,
    onError: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            onError()
        }
    }
}