package com.tetris;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

public class Game implements Runnable {

    private final String TAG = "Game";

    public static final int MoveLeft = 1;
    public static final int MoveRight = 2;
    public static final int RotateLeft = 3;
    public static final int RotateRight = 4;
    public static final int MoveDown = 5;
    public static final int Tick = 6;
    public static final int Start = 7;

    int width;
    int height;

    int[] color;
    boolean[] bits;

    Tetromino next;
    int nextColor;
    Tetromino current;
    int currentX;
    int currentY;
    int currentColor;

    boolean allignLeft;
    boolean allignRight;

    Handler timer;
    GameHandler handler;

    public Game(int width, int height, GameHandler handler, Handler timer) {
        this.width = width;
        this.height = height;
        this.handler = handler;

        this.timer = timer;

        bits = new boolean[width*height];
        color = new int[width*height];
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
        initNext();
        newTetromino();
        initNext();
        tick();
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
        if (!isTouchedLeft(currentX - 1) && !isIntersected(currentX - 1, currentY)) {
            currentX -= 1;
            rememberAllignment();

            handler.invalidate();
        }
    }

    private void moveRight() {
        if (!isTouchedRight(currentX + 1) && !isIntersected(currentX + 1, currentY)) {
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

    private void initNext() {
        next = handler.nextTetromino();
        nextColor = handler.nextColor();
    }

    private void newTetromino() {
        current = next;
        currentColor = nextColor;
        currentY = 0;
        currentX = width/2 - current.getWidth()/2;
    }

    private void settleTetromino() {
        for (int y=0; y<current.getHeight(); ++y) {
            for (int x=0; x<current.getWidth(); ++x) {
                if (current.get(x, y)) {
                    bits[index(x+currentX, y+currentY)] = true;
                    color[index(x+currentX, y+currentY)] = currentColor;
                }
            }
        }
    }

    private void moveDown(int n) {
        for (int i=0; i<n; ++i) {
            if (!moveDown()) {
                break;
            }
        }

        handler.invalidate();
    }

    private boolean isIntersected(int X, int Y) {
        for (int y=0; y<current.getHeight(); ++y) {
            for (int x=0; x<current.getWidth(); ++x) {
                if (x + X < width && y + Y < height) {
                    if (bits[index(x + X, y + Y)] & current.get(x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isTouchedDown(int y) {
        return y + current.getHeight() < height;
    }

    private boolean isTouchedLeft(int x) {
        return x < 0;
    }

    private boolean isTouchedRight(int x) {
        return width <= x + current.getWidth();
    }

    private boolean moveDown() {
        if (isTouchedDown(currentY+1) && !isIntersected(currentX, currentY+1)) {
            currentY += 1;
            return true;
        }
        else {
            settleTetromino();
            newTetromino();
            initNext();
            return false;
        }
    }

    public void run() {
        tick();
    }

    private void tick() {
        moveDown();
        handler.invalidate();
        timer.postDelayed(this, 700);
    }

    public void handleMessage(int message) {
        Log.d(TAG, "message: " + message);

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

        case MoveDown:
            moveDown(height);
            break;

        case Tick:
            tick();
            break;

        case Start:
            start();
            break;

        default:
            throw new UnsupportedOperationException("message " + message);
        }
    }
}
