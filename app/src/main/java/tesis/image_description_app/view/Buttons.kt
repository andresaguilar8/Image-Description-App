import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tesis.image_description_app.R

@Composable
fun InvisibleButton(
    onClick: () -> Unit
) {
    PhotoButton(
        image = painterResource(id = R.drawable.camera_button),
        onClick = { onClick() }
    )
}

@Composable
fun PhotoButton(
    image: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(245.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )
    }
}

@Preview
@Composable
fun NormalButtonPreview() {
    //NormalButton({ Unit })
    NormalButton(
//        image = painterResource(id = R.drawable.camera_button),
        onClick = {
        }
    )
}
@Composable
fun NormalButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .size(250.dp)
            .alpha(0.5f)
            .clip(CircleShape),
        onClick = { onClick() }
    ) {
        Icon(
            Icons.Rounded.Mic,
            modifier = Modifier.size(100.dp),
            contentDescription = null
        )
    }
}


