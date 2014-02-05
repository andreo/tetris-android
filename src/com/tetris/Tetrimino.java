
package com.tetris;

import android.graphics.Color;

public class Tetrimino {

    private boolean[] bits;
    private int width;
    private int height;
    private int color;

    public Tetrimino(int width, int height, int color, boolean[] bits) {
        if (!(0 <= width)) throw new IndexOutOfBoundsException("width");
        if (!(0 <= height)) throw new IndexOutOfBoundsException("height");
        if (bits == null) throw new NullPointerException("bits");
        if (bits.length != width*height) throw new IllegalArgumentException("bits");
            
        this.width = width;
        this.height = height;
        this.color = color;
        this.bits = bits;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColor() {
        return color;
    }

    public boolean get(int x, int y) {
        if (!(0 <= x && x < width)) throw new IndexOutOfBoundsException("x");
        if (!(0 <= y && y < height)) throw new IndexOutOfBoundsException("y");
            
        return bits[y*width + x];
    }
}

