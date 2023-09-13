package tesis.image_description_app.view

import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import tesis.image_description_app.model.CameraHandler
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import tesis.image_description_app.viewModel.CameraViewModel

@Composable
fun CameraView(
    executor: Executor,
    onImageCaptured: (ByteBuffer) -> Unit,
    cameraViewModel: CameraViewModel,
    cameraHandler: CameraHandler,
    previewView: PreviewView,
    onError: (ImageCaptureException) -> Unit
) {

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    //val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    LaunchedEffect(cameraViewModel.shouldShowCamera()) {
        cameraProvider = context.getCameraProvider()
        cameraProvider!!.unbindAll()
            cameraProvider!!.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    DisposableEffect(Unit) {
        onDispose {
            preview.setSurfaceProvider(null)
            //cameraProvider?.unbindAll()
        }
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier.padding(bottom = 40.dp),
            onClick = {
                Log.i("IconButton", "onClick of the button icon")
                cameraHandler.takePhoto(
                    imageCapture = imageCapture,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )

            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Lens,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        )
    }
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}



