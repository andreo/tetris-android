
package com.tetris;

import android.graphics.Color;

public class Tetromino {

    private boolean[] bits;
    private int width;
    private int height;

    public Tetromino(int width, int height, boolean[] bits) {
        if (!(0 <= width)) throw new IndexOutOfBoundsException("width");
        if (!(0 <= height)) throw new IndexOutOfBoundsException("height");
        if (bits == null) throw new NullPointerException("bits");
        if (bits.length != width*height) throw new IllegalArgumentException("bits");
            
        this.width = width;
        this.height = height;
        this.bits = bits;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void check(int x, int y) {
        if (!(0 <= x && x < width)) throw new IndexOutOfBoundsException("x");
        if (!(0 <= y && y < height)) throw new IndexOutOfBoundsException("y");
    }

    private int index(int x, int y) {
        return y*width + x;
    }

    public boolean get(int x, int y) {
        check(x, y);
        return bits[index(x, y)];
    }

    public void rotateRight() {
        // make in place
        boolean[] newBits = new boolean[width*height];
        int newWidth = height;
        int newHeight = width;

        for (int y=0; y<height; ++y) {
            for (int x=0; x<width; ++x) {
                int newY = x;
                int newX = height - y - 1;

                newBits[newWidth*newY + newX] = bits[index(x, y)];
            }
        }

        width = newWidth;
        height = newHeight;
        bits = newBits;
    }

    public void rotateLeft() {
        // make in place
        boolean[] newBits = new boolean[width*height];
        int newWidth = height;
        int newHeight = width;

        for (int y=0; y<height; ++y) {
            for (int x=0; x<width; ++x) {
                int newY = width - x - 1;
                int newX = y;

                newBits[newWidth*newY + newX] = bits[index(x, y)];
            }
        }

        width = newWidth;
        height = newHeight;
        bits = newBits;
    }
}
