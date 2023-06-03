package com.example.mytetris.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.mytetris.GameActivity
import com.example.mytetris.constants.CellConstants
import com.example.mytetris.constants.FieldConstants
import com.example.mytetris.models.AppModel
import com.example.mytetris.models.Block

class TetrisView : View {
    // расширяемая классом View, общим классом для всех представлений
    private val paint = Paint()

    // класс Paint включает в себе информацию о стиле и цвете, рисование текстов, растровых
    // изображений, геометрических построений
    private var lastMove: Long = 0

    // для отслеживания промежутка времени в миллисекундах, в течении которого
    // выполняется перемещение
    private var model: AppModel? = null
    private var activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize: Dimension = Dimension(0, 0)

    // размер ячеек в игре
    private var frameOffset: Dimension = Dimension(0, 0)
    // смещение фрейма ячеек

    // создали два вторичных конструктора класса, из них будет запускаться тот который нужен
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private class ViewHandler(private val owner: TetrisView) : Handler() {
        override fun handleMessage(message: Message) {
            if (message.what == 0) {
                if (owner.model != null) {
                    if (owner.model!!.isGameOver()) {
                        owner.model?.endGame()
                        owner.activity?.tvCurrentScore?.text = "0"
                        Toast.makeText(owner.activity, "Game over", Toast.LENGTH_LONG).show()
                    }
                    if (owner.model!!.isGameActive()) {
                        owner.setGameCommandWithDelay(AppModel.Motions.DOWN)
                    }
                }
            }
        }

        fun sleep(delay: Long) {
            this.removeMessages(0)
            sendMessageDelayed(obtainMessage(0), delay)
        }
    }

    private data class Dimension(val width: Int, val height: Int)

    fun setModel(model: AppModel) {
        this.model = model
    }

    fun setActivity(gameActivity: GameActivity) {
        this.activity = gameActivity
    }

    fun setGameCommand(move: AppModel.Motions) {
        if (null != model && (model?.currentState == AppModel.Statuses.ACTIVE.name)) {
            if (AppModel.Motions.DOWN == move) {
                model?.generateField(move.name)
                invalidate()
                return
            }
            setGameCommandWithDelay(move)
        }
    }

    fun setGameCommandWithDelay(move: AppModel.Motions) {
        // устанавливает исполняемую игрой текущую команду перемещения
        val now = System.currentTimeMillis()
        if (now - lastMove > DELAY) {
            model?.generateField(move.name)
            invalidate()
            lastMove = now
        }
        updateScores()
        viewHandler.sleep(DELAY.toLong())
    }

    private fun updateScores() {
        activity?.tvCurrentScore?.text = "${model?.score}"
        activity?.tvHighScore?.text = "${activity?.appPreferences?.getHighScore()}"
        activity?.updateHighScore()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFrame(canvas)
        if (model != null) {
            for (i in 0 until FieldConstants.ROW_COUNT.value) {
                for (j in 0 until FieldConstants.COLUMN_COUNT.value) {
                    drawCell(canvas, i, j)
                }
            }
        }
    }

    private fun drawFrame(canvas: Canvas) {
        paint.color = Color.BLACK // выбираем цвет заднего фона игрового поля
        canvas.drawRect(
            frameOffset.width.toFloat(),
            frameOffset.height.toFloat(),
            width - frameOffset.width.toFloat(),
            height - frameOffset.height.toFloat(),
            paint
        )
    }

    private fun drawCell(canvas: Canvas, row: Int, col: Int) {
        val cellStatus = model?.getCellStatus(row, col)
        if (CellConstants.EMPTY.value != cellStatus) {
            val color = if (CellConstants.EPHEMERAL.value == cellStatus) {
                model?.currentBlock?.color
            } else {
                Block.getColor(cellStatus as Byte)
            }
            drawCell(canvas, col, row, color as Int)
        }
    }

    private fun drawCell(canvas: Canvas, x: Int, y: Int, rgbColor: Int) {
        paint.color = rgbColor
        val top: Float = (frameOffset.height + y * cellSize.height + BLOCK_OFFSET).toFloat()
        val left: Float = (frameOffset.width + x * cellSize.width + BLOCK_OFFSET).toFloat()
        val bottom: Float =
            (frameOffset.height + (y + 1) * cellSize.height - BLOCK_OFFSET).toFloat()
        val right: Float = (frameOffset.width + (x + 1) * cellSize.height - BLOCK_OFFSET).toFloat()
        val rectangle = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectangle, 4F, 4F, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cellWidth = (w - 2 * FRAME_OFFSET_BASE) / FieldConstants.COLUMN_COUNT.value
        val cellHeight = (h - 2 * FRAME_OFFSET_BASE) / FieldConstants.ROW_COUNT.value
        val n = cellWidth.coerceAtMost(cellHeight)
        this.cellSize = Dimension(n, n)
        val offsetX = (w - FieldConstants.COLUMN_COUNT.value * n) / 2
        val offsetY = (h - FieldConstants.ROW_COUNT.value * n) / 2
        this.frameOffset = Dimension(offsetX, offsetY)
    }

    companion object {
        private const val DELAY = 500
        private const val BLOCK_OFFSET = 2
        private const val FRAME_OFFSET_BASE = 10
    }
}