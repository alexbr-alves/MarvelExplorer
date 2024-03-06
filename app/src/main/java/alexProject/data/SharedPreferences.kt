package alexProject.data

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

class SharedPreferences (context: Context) {
    private val preferences: SharedPreferences? =
        context.getSharedPreferences("MarvelPreferences", Context.MODE_PRIVATE)

}