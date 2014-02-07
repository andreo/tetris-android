package com.tetris;

import android.graphics.Color;
import android.os.Handler;

public class Game implements Runnable {

    public static final int MoveLeft = 1;
    public static final int MoveRight = 2;
    public static final int RotateLeft = 3;
    public static final int RotateRight = 4;
    public static final int Down = 5;
    public static final int Tick = 6;

    int width;
    int height;

    int[] color;
    boolean[] bits;

    Tetromino next;
    Tetromino current;
    int currentX;
    int currentY;
    int currentColor;

    boolean allignLeft;
    boolean allignRight;

    TetrominoFactory factory = new TetrominoFactory();

    Handler timer;
    GameHandler handler;

    public Game(int width, int height, GameHandler handler, Handler timer) {
        this.width = width;
        this.height = height;
        this.handler = handler;

        this.timer = timer;

        current = factory.get(1);
        currentColor = Color.GREEN;

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

    private void check(int x, int y) {
        if (!(0 <= x && x < width)) throw new IndexOutOfBoundsException("x");
        if (!(0 <= y && y < height)) throw new IndexOutOfBoundsException("y");
    }

    private int index(int x, int y) {
        return y * width + x;
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

    public Tetromino getCurrentTetromino() {
        return current;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public Tetromino getNextTetromino() {
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

    private void rememberAllignment() {
        allignLeft = currentX < 0;
        allignRight = width <= (currentX + current.getWidth() + 1);
    }

    private void allign() {
        if (allignLeft) {
            currentX = 0;
        }
        else if (allignRight) {
            currentX = width - 1 - current.getWidth();
        }
    }

    private void fixAllignment() {
        if (currentX < 0) {
            currentX = 0;
        }
        else if (width <= currentX + current.getWidth()) {
            currentX = width - 1 - current.getWidth();
        }
    }

    private void moveLeft() {
        if (0 <= (currentX - 1)) {
            currentX -= 1;
            rememberAllignment();

            handler.invalidate();
        }
    }

    private void moveRight() {
        if ((currentX + current.getWidth() + 1) < width) {
            currentX += 1;
            rememberAllignment();

            handler.invalidate();
        }
    }

    private void rotateLeft() {
        current.rotateLeft();
        fixAllignment();
        allign();

        handler.invalidate();
    }

    private void rotateRight() {
        current.rotateRight();
        fixAllignment();
        allign();

        handler.invalidate();
    }

    private void down(int n) {
        if ((currentY + n + current.getHeight()) < height) {
            currentY += n;
        }
    }

    public void run() {
        tick();
    }

    private void tick() {
        down(1);
        handler.invalidate();
        timer.postDelayed(this, 1000);
    }

    public void handleMessage(int message) {
        switch (message) {

        case RotateRight:
            rotateRight();
            break;

        case RotateLeft:
            rotateLeft();
            break;

        case MoveLeft:
            moveLeft();
            break;

        case MoveRight:
            moveRight();
            break;

        case Down:
            down(height);
            break;

        case Tick:
            tick();
            break;
        }
    }
}
