package com.example.mytetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mytetris.databinding.ActivityGameBinding
import com.example.mytetris.storage.AppPreferences

class GameActivity : AppCompatActivity() {

    private lateinit var b: ActivityGameBinding
    var appPreferences: AppPreferences? = null
    lateinit var tvCurrentScore: TextView
    lateinit var tvHighScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGameBinding.inflate(layoutInflater)
        setContentView(b.root)

        appPreferences = AppPreferences(this)

        tvCurrentScore = b.tvCurrentScore
        tvHighScore = b.tvHighScore

        updateHighScore()
        updateCurrentScore()
    }

    private fun updateHighScore() {
        tvHighScore.text = "${appPreferences?.getHighScore()}"
    }

    private fun updateCurrentScore() {
        tvCurrentScore.text = "0"
    }
}