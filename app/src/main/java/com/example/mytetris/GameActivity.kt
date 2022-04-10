package com.example.mytetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytetris.databinding.ActivityGameBinding
import com.example.mytetris.storage.AppPreferences

class GameActivity : AppCompatActivity() {

    private lateinit var b: ActivityGameBinding
    var appPreferences: AppPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGameBinding.inflate(layoutInflater)
        setContentView(b.root)

        appPreferences = AppPreferences(this)

        updateHighScore()
        updateCurrentScore()
    }

    private fun updateHighScore() {
        b.tvHighScore.text = "${appPreferences?.getHighScore()}"
    }

    private fun updateCurrentScore() {
        b.tvCurrentScore.text = "0"
    }
}