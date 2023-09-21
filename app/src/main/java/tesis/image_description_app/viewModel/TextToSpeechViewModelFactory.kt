package tesis.image_description_app.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TextToSpeechViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == TextToSpeechViewModel::class.java) {
            return TextToSpeechViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}