package com.example.mytetris

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mytetris.databinding.LandingScreenBinding
import com.example.mytetris.storage.AppPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var b: LandingScreenBinding
    private lateinit var preferences: AppPreferences
    private lateinit var tvHighScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = LandingScreenBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()

        preferences = AppPreferences(this)

        tvHighScore = b.tvHighScore
        b.btnNewGame.setOnClickListener(this::onBtnNewGameClick)
    }

    override fun onResume() {
        super.onResume()
        tvHighScore.text = preferences.getHighScore().toString()
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}