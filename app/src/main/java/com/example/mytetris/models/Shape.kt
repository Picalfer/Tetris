package com.example.mytetris.models

enum class Shape(val frameCount: Int, val startPosition: Int) {
    // frameCount число возможных фреймов, в которых может находиться форма, к примеру,
    // тертрамино в форме "палки" может быть только в двух положениях
    // startPosition предполагаемая начальная позиция формы вдоль оси X в поле игрового процесса
    Tetromino(2,2) {
        override fun getFrame(frameNumber: Int) : Frame {
            return when (frameNumber) {
                0 -> Frame(4).addRow("1111")
                1 -> Frame(1)
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
            }
        }
    };

    abstract fun getFrame(frameNumber: Int) : Frame
}