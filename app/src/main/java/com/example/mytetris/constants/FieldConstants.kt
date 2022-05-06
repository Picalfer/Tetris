package com.example.mytetris.constants

enum class FieldConstants(val value: Int) { // аргумент как раз, чтобы у нас не просто было
    // перечисление, а у каждого элемента перечисления было свое значение, которое можно получить
    // через функцию get + название аргумента, в данном случае ф-я называется getValue()
    COLUMN_COUNT(10), ROW_COUNT(20);
    // обозначили, что наше игровое поле имеет 10 столбцов и 20 строчек
}