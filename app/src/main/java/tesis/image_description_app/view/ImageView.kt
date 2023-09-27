package tesis.image_description_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

@Composable
fun ShowImage(imageBitmap: ImageBitmap?) {
    //TODO handle error si imagebitmap es null
    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}