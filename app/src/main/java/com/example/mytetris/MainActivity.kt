package com.example.mytetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.mytetris.databinding.ActivityMainBinding
import com.example.mytetris.databinding.LandingScreenBinding
import com.example.mytetris.storage.AppPreferences
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var b: LandingScreenBinding
    private lateinit var preferences: AppPreferences
    lateinit var tvHighScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = LandingScreenBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()

        preferences = AppPreferences(this)

        tvHighScore = b.tvHighScore
        b.btnNewGame.setOnClickListener(this::onBtnNewGameClick)
        /*b.btnExit.setOnClickListener(this::onBtnExitClick)
        b.btnResetScore.setOnClickListener(this::onBtnResetScoreClick)*/
    }

    override fun onResume() {
        super.onResume()
        tvHighScore.text = preferences.getHighScore().toString()
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java) // this - MainActivity
        startActivity(intent)
    }

/*    private fun onBtnResetScoreClick(view: View) {
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset", Snackbar.LENGTH_SHORT).show()
        b.tvHighScore.text = getString(R.string.high_score, preferences.getHighScore().toString())
    }

    private fun onBtnExitClick(view: View) {
        exitProcess(0)
    }*/
}