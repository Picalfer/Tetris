package com.example.mytetris.models

import com.example.mytetris.constants.FieldConstants
import com.example.mytetris.helpers.array2dOfByte
import com.example.mytetris.storage.AppPreferences

class AppModel {
    var scope: Int = 0
    private var preferences: AppPreferences? = null

    var currentBlock: Block? = null
    var currentState: String = Statuses.AWAITING_START.name

    private var field: Array<ByteArray> = array2dOfByte( // генерируем поле 20 строк на 10 столбцов
        FieldConstants.ROW_COUNT.value,
        FieldConstants.COLUMN_COUNT.value
    )

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