package com.example.notesapplication.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.notesapplication.utils.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(@ApplicationContext context: Context) {

    private var prefs : SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, 0)
        editor = prefs.edit()
    }

    fun setData(key: String, value: String){
        try {
            editor.putString(key, value).apply()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getData(key: String): String? {
        return prefs.getString(key, null)
    }


}