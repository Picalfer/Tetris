package com.example.mytetris.helpers

fun array2dOfByte(sizeOuter: Int, sizeInner: Int) : Array<ByteArray>
=  Array(sizeOuter) { ByteArray(sizeInner) }
// size outer - аргумент, указывающий номер строки создаваемового массива
// size inner - номер столбца сгенерированного массива байтов
// метод array2dOfByte генерирует и возвращает новый массив с указанными свойствами