package alexProject.util

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide

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

fun loadImage(
    imageView : ImageView,
    path : String,
    extension : String? = null
) {
    Glide.with(imageView)
        .load("$path.$extension")
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