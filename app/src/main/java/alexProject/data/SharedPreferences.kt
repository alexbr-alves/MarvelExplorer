package alexProject.data

import android.content.Context
import javax.inject.Inject

class SharedPreferences @Inject constructor(context: Context) {
    private val preferences: android.content.SharedPreferences? =
        context.getSharedPreferences("MarvelPreferences", Context.MODE_PRIVATE)

    fun saveLastOffsetCharacter(offset: Int) {
        preferences?.edit()?.putInt("last_offset_character", offset)?.apply()
    }

    fun getLastOffsetCharacter(): Int {
        return preferences?.getInt("last_offset_character", 0) ?: 0
    }

    fun saveTotalCharacter(value: Int) {
        preferences?.edit()?.putInt("total_character", value)?.apply()
    }

    fun getTotalCharacter(): Int {
        return preferences?.getInt("total_character", 999999999) ?: 999999999
    }

    fun saveLastOffsetComic(offset: Int) {
        preferences?.edit()?.putInt("last_offset_comic", offset)?.apply()
    }

    fun getLastOffsetComic(): Int {
        return preferences?.getInt("last_offset_comic", 0) ?: 0
    }

    fun saveTotalComic(value: Int) {
        preferences?.edit()?.putInt("total_comic", value)?.apply()
    }

    fun getTotalComic(): Int {
        return preferences?.getInt("total_comic", 999999999) ?: 999999999
    }
    class ComicsPreferences {

    }
}