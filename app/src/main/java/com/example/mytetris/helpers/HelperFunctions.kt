package com.example.mytetris.helpers

fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> =
    Array(sizeOuter) { ByteArray(sizeInner) }
// size outer - аргумент, указывающий количество строк создаваемого массива
// size inner - номер столбцов сгенерированного массива байтов
// метод array2dOfByte генерирует и возвращает новый массив байтов с указанными свойствами