import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
        image = painterResource(id = R.drawable.desca),
        onClick = { onClick() }
    )
}

@Composable
fun PhotoButton(
    image: Painter,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(250.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RectangleShape)
        )
    }
}

@Preview
@Composable
fun NormalButtonPreview() {
    //NormalButton({ Unit })
    PhotoButton(
        image = painterResource(id = R.drawable.desca),
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
            .size(200.dp)
            .alpha(0.5f),
        shape = CircleShape,
        onClick = { onClick() }
    ) {
        Text(
            text = "textButton",
            modifier = Modifier.alpha(0f)
        )
    }
}


