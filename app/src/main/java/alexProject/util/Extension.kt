package alexProject.util

import alexProject.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun Fragment.toast(message : String, duration : Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        requireContext(),
        message,
        duration
    ).show()
}

fun View.setVisible(visible: Boolean? = null) {
    visibility = if (visible == true || visible == null) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun Activity.goToActivity(destinationActivity: Class<out Activity>) {
    val intent = Intent(this, destinationActivity)
    startActivity(intent)
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, observer)
}

@SuppressLint("ResourceType")
fun loadImage(
    imageView : ImageView,
    path : String,
    extension : String? = null
) {
    Glide.with(imageView)
        .load("$path.$extension")
        .placeholder(R.drawable.background_gradient_rounded)
        .into(imageView)
}

fun removeParenthesesOnTitle(title: String): String {
    val indexOpenParenthesis = title.indexOf('(')
    val indexCloseParenthesis = title.indexOf(')')

    return if (indexOpenParenthesis != -1 && indexCloseParenthesis != -1 && indexCloseParenthesis > indexOpenParenthesis) {
        title.substring(0, indexOpenParenthesis).trim()
    } else {
        title
    }
}
fun String.limitDescription(characters : Int) : String {
    if (this.length > characters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, characters)}..."
    }
    return this
}
fun FragmentActivity.setStatusBarColor(color: Int, isLight: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this@setStatusBarColor, color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val insetsController = WindowInsetsControllerCompat(window, window.decorView)
            insetsController.isAppearanceLightStatusBars = isLight
        }
    }
}

fun FragmentActivity.setFullScreenMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // Para Android 11+ (API 30+)
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        // Para versões anteriores
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}