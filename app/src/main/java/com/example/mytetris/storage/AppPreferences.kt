package com.example.mytetris.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.mytetris.Constants

class AppPreferences(ctx: Context) { // сюда мы будем передавать "this" при
    // создании экзмпляра этого класса
    private val data: SharedPreferences = ctx
        .getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveHighScore(highScore: Int) {
        data.edit().putInt(Constants.HIGH_SCORE.name, highScore).apply()
    }

    fun getHighScore(): Int {
        return data.getInt(Constants.HIGH_SCORE.name, 0)
    }

    fun clearHighScore() {
        data.edit().putInt(Constants.HIGH_SCORE.name, 0).apply()
    }
}