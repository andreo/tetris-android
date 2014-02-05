package com.tetris;

import android.graphics.Color;

public class Game {

    int width;
    int height;

    int[] color;
    boolean[] bits;

    Tetrimino current;
    Tetrimino next;

    private void check(int x, int y) {
        if (!(0 <= x && x < width)) throw new IndexOutOfBoundsException("x");
        if (!(0 <= y && y < height)) throw new IndexOutOfBoundsException("y");
    }

    private int index(int x, int y) {
        return y * width + x;
    }

    public Game() {
        current = new Tetrimino(3, 2,
                                Color.RED,
                                new boolean[] { false, true, false, true, true, true });

        width = 5;
        height = 5;

        bits = new boolean[width*height];
        color = new int[width*height];
        for (int i = 0; i < getWidth(); ++i) {
            for (int j = 0; j < getHeight(); ++j) {
                color[index(i, j)] = Color.GREEN;
            }
        }

        bits[index(0, height-1)] = true;
        color[index(0, height-1)] = Color.RED;

        bits[index(1, height-1)] = true;
        color[index(1, height-1)] = Color.BLUE;

        bits[index(2, height-1)] = true;
        color[index(2, height-1)] = Color.GREEN;
    }

    public int getColor(int x, int y) {
        check(x, y);
        return color[index(x, y)];
    }

    public boolean getBit(int x, int y) {
        check(x, y);
        return bits[index(x, y)];
    }

    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Tetrimino getCurrentTetrimino() {
        return current;
    }

    public Tetrimino getNextTetrimino() {
        return next;
    }

    public void start() {
        throw new UnsupportedOperationException("start");
    }

    public void stop() {
        throw new UnsupportedOperationException("stop");
    }

    public void pause() {
        throw new UnsupportedOperationException("pause");
    }

    public void resume() {
        throw new UnsupportedOperationException("resume");
    }

    public void left() {
        throw new UnsupportedOperationException("left");
    }
    
    public void right() {
        throw new UnsupportedOperationException("right");
    }
    
    public void down() {
        throw new UnsupportedOperationException("down");
    }
    
    public void rotateLeft() {
        throw new UnsupportedOperationException("rotateLeft");
    }
    
    public void rotateRight() {
        throw new UnsupportedOperationException("rotateRight");
    }
}
