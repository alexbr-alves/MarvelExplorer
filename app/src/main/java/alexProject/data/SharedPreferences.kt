package alexProject.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferences @Inject constructor(context: Context) {
    private val preferences: android.content.SharedPreferences? =
        context.getSharedPreferences("MarvelPreferences", Context.MODE_PRIVATE)

    fun saveLastOffset(offset: Int) {
        preferences?.edit()?.putInt("last_offset", offset)?.apply()
    }

    fun getLastOffset(): Int {
        return preferences?.getInt("last_offset", 0) ?: 0
    }

    fun saveTotalCharacter(value: Int) {
        preferences?.edit()?.putInt("total_character", value)?.apply()
    }

    fun getTotalCharacter(): Int {
        return preferences?.getInt("total_character", 999999999) ?: 999999999
    }
}