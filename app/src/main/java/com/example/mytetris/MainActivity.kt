package com.example.mytetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mytetris.databinding.ActivityMainBinding
import com.example.mytetris.storage.AppPreferences
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()

        b.btnNewGame.setOnClickListener(this::onBtnNewGameClick)
        b.btnExit.setOnClickListener(this::onBtnExitClick)
        b.btnResetScore.setOnClickListener(this::onBtnResetScoreClick)

    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java) // this - MainActivity
        startActivity(intent)
    }

    private fun onBtnResetScoreClick(view: View) {
        val preferences = AppPreferences(this)
        preferences.clearHighScore()
    }

    private fun onBtnExitClick(view: View) {
        exitProcess(0)
    }
}