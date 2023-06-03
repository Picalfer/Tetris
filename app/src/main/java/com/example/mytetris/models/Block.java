package com.example.mytetris.models;

import android.graphics.Color;
import android.graphics.Point;
import androidx.annotation.NonNull;
import com.example.mytetris.constants.FieldConstants;
import java.util.Random;

public class Block {
    private final int shapeIndex;
    private int frameNumber;
    private final BlockColor color;
    private Point position;

    private Block(int shapeIndex, BlockColor blockColor) {
        this.frameNumber = 0;
        this.shapeIndex = shapeIndex;
        this.color = blockColor;
        this.position = new Point(FieldConstants.COLUMN_COUNT.getValue() / 2, 0);
        // помещаем по горизонтали в центр элемент, а по вертикали в самый верх
    }

    public static Block createBlock() {
        Random random = new Random();
        int shapeIndex = random.nextInt(Shape.values().length); // получаем количество возможных
        // тетромино
        BlockColor blockColor = BlockColor.values()
                [random.nextInt(BlockColor.values().length)];
        Block block = new Block(shapeIndex, blockColor);
        block.position.x = block.position.x - Shape.values()
                [shapeIndex].getStartPosition();
        return block;
    }

    public static int getColor(byte value) {
        // проходимся по перечислению цветов и если байт совпадет с аргументом - возвращаем rgb
        for (BlockColor color : BlockColor.values()) {
            if (value == color.byteValue) {
                return color.rgbValue;
            }
        }
        return -1;
    }

    public final void setState(int frame, Point position) {
        this.frameNumber = frame;
        this.position = position;
    }

    @NonNull
    public final byte[][] getShape(int frameNumber) {
        return Shape.values()[shapeIndex].getFrame(frameNumber).as2dByteArray();
    }

    public Point getPosition() {
        return this.position;
    }

    public final int getFrameCount() {
        return Shape.values()[shapeIndex].getFrameCount();
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getColor() {
        return color.rgbValue;
    }

    public byte getStaticValue() {
        return color.byteValue;
    }

    public enum BlockColor {
        PINK(Color.rgb(255, 105, 180), (byte) 2),
        GREEN(Color.rgb(0, 128, 0), (byte) 3),
        ORANGE(Color.rgb(255, 140, 0), (byte) 4),
        YELLOW(Color.rgb(255, 255, 0), (byte) 5),
        CYAN(Color.rgb(0, 255, 255), (byte) 6);

        BlockColor(int rgbValue, byte byteValue) {
            this.rgbValue = rgbValue;
            this.byteValue = byteValue;
        }

        private final int rgbValue;
        private final byte byteValue;
    }
}
