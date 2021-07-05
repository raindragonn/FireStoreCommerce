package com.raindragonn.firestorecommerce.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceManager(
    private val pref: SharedPreferences
) {
    companion object {
        private const val USER_KEY = "KEY_USER_ID"
    }

    var userId: String?
        get() = pref.getString(USER_KEY, null)
        set(value) {
            pref.edit {
                putString(USER_KEY, value)
            }
        }
}