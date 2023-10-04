package tesis.image_description_app.view

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat


fun showInitError(
    context: Context,
    errorMessage: String
) {
    val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_LONG)
    toast.show()

    toast.view?.let {
        ViewCompat.setImportantForAccessibility(
            it,
            ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES
        )
    }
    toast.view?.let {
        ViewCompat.setAccessibilityDelegate(it, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                if (host != null && info != null)
                    super.onInitializeAccessibilityNodeInfo(host, info)
                info.text = errorMessage
            }
        })
    }
}
