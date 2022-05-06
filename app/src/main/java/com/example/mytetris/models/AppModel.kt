package com.example.mytetris.models

import android.graphics.Point
import com.example.mytetris.constants.CellConstants
import com.example.mytetris.constants.FieldConstants
import com.example.mytetris.helpers.array2dOfByte
import com.example.mytetris.storage.AppPreferences

class AppModel {
    var score: Int = 0
    private var preferences: AppPreferences? = null

    var currentBlock: Block? = null
    var currentState: String = Statuses.AWAITING_START.name

    private var field: Array<ByteArray> = array2dOfByte( // генерируем поле 20 строк на 10 столбцов
        FieldConstants.ROW_COUNT.value,
        FieldConstants.COLUMN_COUNT.value
    )

    fun setPreferences(preferences: AppPreferences?) { // устанавливаем в качестве аргумента
        // присланный SharedPreferences файл
        this.preferences = preferences
    }

    fun getCellStatus(row: Int, column: Int) : Byte? {
        return field[row][column]
    }

    private fun setCellStatus(row: Int, column: Int, status: Byte?) {
        if (status != null) {
            field[row][column] = status
        }
    }

    fun isGameActive() : Boolean {
        return currentState == Statuses.ACTIVE.name
    }

    fun isGameAwaitingStart() : Boolean {
        return currentState == Statuses.AWAITING_START.name
    }

    fun isGameOver() : Boolean {
        return currentState == Statuses.OVER.name
    }

    private fun boostScore() {
        score += 10
        if (score > preferences?.getHighScore() as Int) preferences?.saveHighScore(score)
    }

    private fun generateNextBlock() {
        currentBlock = Block.createBlock()
    }

    private fun validTranslation(position: Point, shape: Array<ByteArray>): Boolean {
        // 1-ые 3 условия проверяют, находится ли в поле позиция, куда передвигаем блок
        // в else проверяется свободны ли клетки куда передвигаем блок
        return if (position.y < 0 || position.x < 0) {
            false
        } else if (position.y + shape.size > FieldConstants.ROW_COUNT.value) {
            false
        } else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value) {
            false
        } else {
            for (i in 0 until shape.size) {
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (CellConstants.EMPTY.value != shape[i][j] &&
                        CellConstants.EMPTY.value != field[x][y]
                    ) {
                        return false
                    }
                }
            }
            true
        }
    }

    private fun moveValid(position: Point, frameNumber: Int?) : Boolean {
        // функция для запуска функции validTranslation
        val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber as Int)
        return validTranslation(position, shape as Array<ByteArray>)
    }

    fun generateField(action: String) {
        if (isGameActive()) {
            resetField()
            var frameNumber: Int? = currentBlock?.frameNumber
            val coordinate: Point? = Point()
            coordinate?.x = currentBlock?.position?.x
            coordinate?.y = currentBlock?.position?.y

            when (action) {
                Motions.LEFT.name -> {
                    coordinate?.x = currentBlock?.position?.x?.minus(1)
                }
                Motions.RIGHT.name -> {
                    coordinate?.x = currentBlock?.position?.x?.plus(1)
                }
                Motions.DOWN.name -> {
                    coordinate?.y = currentBlock?.position?.y?.plus(1)
                }
                Motions.ROTATE.name -> {
                    frameNumber = frameNumber?.plus(1)
                    if (frameNumber != null) {
                        if (frameNumber >= currentBlock?.frameCount as Int) {
                            frameNumber = 0
                            // если frame number станет больше чем возможное количество
                            // форм, то оно вернется в исходное состояние
                        }
                    }
                }
            }

            if (!moveValid(coordinate as Point, frameNumber)) {
                translateBlock(currentBlock?.position as Point, currentBlock?.frameNumber as Int)
                if (Motions.DOWN.name == action) {
                    boostScore()
                    persistCellData()
                    assessField()
                    generateNextBlock()
                    if (!blockAdditionPossible()) {
                        currentState = Statuses.OVER.name
                        currentBlock = null
                        resetField(false)
                    }
                }
            } else {
                if (frameNumber != null) {
                    translateBlock(coordinate, frameNumber)
                    currentBlock?.setState(frameNumber, coordinate)
                }
            }
        }
    }

    enum class Statuses {
        AWAITING_START, // состояние игры до ее запуска
        ACTIVE, // игровой процесс выполняется
        INACTIVE, // игровой процесс не выполняется
        OVER // игра завершена
    }

    enum class Motions {
        LEFT, RIGHT, DOWN, ROTATE
    }

}