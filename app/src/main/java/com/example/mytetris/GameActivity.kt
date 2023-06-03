package com.example.mytetris

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mytetris.databinding.GameScreenBinding
import com.example.mytetris.models.AppModel
import com.example.mytetris.storage.AppPreferences
import com.example.mytetris.view.TetrisView

class GameActivity : AppCompatActivity() {

    private lateinit var b: GameScreenBinding

    var appPreferences: AppPreferences? = null
    private lateinit var tetrisView: TetrisView
    private val appModel: AppModel = AppModel()

    lateinit var tvCurrentScore: TextView
    lateinit var tvHighScore: TextView

    @SuppressLint("ClickableViewAccessibility")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = GameScreenBinding.inflate(layoutInflater).also { setContentView(it.root) }

        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        tvCurrentScore = b.tvCurrentScore
        tvHighScore = b.tvHighScore
        tetrisView = b.viewTetris

        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)
        tetrisView.setOnTouchListener(this::onTetrisViewTouch)

        b.btnRestart.setOnClickListener {
            appModel.restartGame()
        }

        updateHighScore()
        updateCurrentScore()
    }

    private fun onTetrisViewTouch(view: View, event: MotionEvent): Boolean {
        if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(AppModel.Motions.DOWN)
        } else if (appModel.isGameActive()) {
            when (resolveTouchDirection(view, event)) {
                0 -> moveTetromino(AppModel.Motions.LEFT)
                1 -> moveTetromino(AppModel.Motions.ROTATE)
                2 -> moveTetromino(AppModel.Motions.DOWN)
                3 -> moveTetromino(AppModel.Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent): Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction: Int = if (y > x) {
            if (x > 1 - y) 2 else 0
        } else {
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetromino(motion: AppModel.Motions) {
        if (appModel.isGameActive()) {
            tetrisView.setGameCommand(motion)
        }
    }

    fun updateHighScore() {
        tvHighScore.text = "${appPreferences?.getHighScore()}"
    }

    private fun updateCurrentScore() {
        tvCurrentScore.text = "0"
    }
}