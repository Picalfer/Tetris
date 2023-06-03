package com.example.mytetris

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mytetris.databinding.LandingScreenBinding
import com.example.mytetris.storage.AppPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var b: LandingScreenBinding
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = LandingScreenBinding.inflate(layoutInflater).also { setContentView(it.root) }

        preferences = AppPreferences(this)

        b.btnNewGame.setOnClickListener(this::onBtnNewGameClick)
    }

    override fun onResume() {
        super.onResume()
        b.tvHighScore.text = preferences.getHighScore().toString()
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}