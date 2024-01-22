package alexProject.util

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun Fragment.toast(message : String, duration : Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        requireContext(),
        message,
        duration
    ).show()
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun loadImage(
    imageView : ImageView,
    path : String,
    extension : String
) {
    Glide.with(imageView)
        .load("$path.$extension")
        .into(imageView)
}

fun String.limitDescription(characters : Int) : String {
    if (this.length > characters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, characters)}..."
    }
    return this
}