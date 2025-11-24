package com.example.mytetris.models

import com.example.mytetris.helpers.array2dOfByte

class Frame(private val width: Int) {
    // width - число столбцов в байтовом массиве фрейма
    private val data: ArrayList<ByteArray> =
        ArrayList() // Список строк фигуры (каждая строка - ByteArray)

    /**
     * Добавляет строку в фигуру.
     * @param byteStr Строка, где каждый символ ('0' или '1') определяет блок.
     * @return this для цепочки вызовов.
     */
    fun addRow(byteStr: String): Frame {
        val row = ByteArray(byteStr.length) // Создаем массив байтов длиной строки
        for (index in byteStr.indices) {
            row[index] = "${byteStr[index]}".toByte() // Преобразуем символ в байт
        }
        data.add(row) // Добавляем строку в data
        return this // Возвращаем this для fluent-интерфейса
    }

    /**
     * Преобразует список строк фигуры в двумерный массив байтов.
     * @return Двумерный массив, представляющий фигуру.
     */
    fun as2dByteArray(): Array<ByteArray> {
        val bytes = array2dOfByte(data.size, width) // Создаем двумерный массив
        return data.toArray(bytes) // Преобразуем список в массив
    }
}