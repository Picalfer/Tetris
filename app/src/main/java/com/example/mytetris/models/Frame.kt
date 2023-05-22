package com.example.mytetris.models

import com.example.mytetris.helpers.array2dOfByte

class Frame(private val width: Int) {
    // width - число столбцов в байтовом массиве фрейма
    private val data: ArrayList<ByteArray> = ArrayList() // список элементов массива

    /*Массив содержит определенное количество элементов и мы можем только считывать и изменять уже
      записанные элементы, но мы не можем добавлять новые после того как массив создан, список имеет
      дополнительные функции для добавления новых элементов и удаления, а также еще некоторые
      основные функции для манипуляции элементов*/

    fun addRow(byteStr: String): Frame {
        // обрабатывает строку, преобразуя каждый отдельный символ строки в байтовое представление
        // и добавляет байтовое представление в байтовый массив
        // после чего байтовый массив добавляется в список данных
        val row = ByteArray(byteStr.length)
        // создается байтовый массив размером в длину отправленной строки

        for (index in byteStr.indices) {
            row[index] = "${byteStr[index]}".toByte()
        }
        data.add(row)
        return this // в любом случае this - это ссылка на самого себя внутри экземпляра класса
    }

    fun as2dByteArray(): Array<ByteArray> {
        // преобразует список массива данных в байтовый массив и возвращает его
        val bytes = array2dOfByte(data.size, width)
        return data.toArray(bytes) // превращаем список в массив с новыми свойствами
        // toArray() преобразовывает список в массив
        // asList() преобразовывает массив в список
    }
}