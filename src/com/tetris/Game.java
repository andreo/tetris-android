package com.tetris;

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
    IGameHandler handler;

    public Game(int width, int height, IGameHandler handler, Handler timer) {
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

    private void cancelNextTick() {
        timer.removeCallbacks(this);
        Log.d(TAG, "cancelNextTick ");
    }

    private void postNextTick() {
        cancelNextTick();
        timer.postDelayed(this, 700);
    }

    public void start() {
        initNext();
        if (newTetromino()) {
            postNextTick();
        }
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
        if (!isTouchedLeft(current, currentX - 1)
            && !isIntersected(current, currentX - 1, currentY)) {
            currentX -= 1;
            rememberAllignment();

            handler.moveTetromino(-1, 0, 0);
        }
    }

    private void moveRight() {
        if (!isTouchedRight(current, currentX + 1)
            && !isIntersected(current, currentX + 1, currentY)) {
            currentX += 1;
            rememberAllignment();

            handler.moveTetromino(1, 0, 0);
        }
    }

    private void rotateLeft() {
        Tetromino newT = current.rotateLeft();
        if (!isTouchedRight(newT, currentX)
            && !isTouchedLeft(newT, currentX)
            && !isIntersected(newT, currentX, currentY)) {

            current = newT;
            handler.moveTetromino(0, 0, -1);
        }

        // current = newT;
        // fixAllignment();
        // allign();

        // handler.invalidate();
    }

    private void rotateRight() {
        Tetromino newT = current.rotateRight();
        if (!isTouchedRight(newT, currentX)
            && !isTouchedLeft(newT, currentX)
            && !isIntersected(newT, currentX, currentY)) {

            current = newT;
            handler.moveTetromino(0, 0, 1);
        }

        // current = current.rotateRight();
        // fixAllignment();
        // allign();

        // handler.invalidate();
    }

    private void initNext() {
        next = handler.nextTetromino();
        nextColor = handler.nextColor();
    }

    private boolean newTetromino() {
        int newX = width/2 - next.getWidth()/2;
        int newY = 0;
        if (isIntersected(next, newX, newY)) {
            cancelNextTick();
            handler.gameOver();
            return false;
        }
        else {
            current = next;
            currentColor = nextColor;
            currentY = 0;
            currentX = width/2 - current.getWidth()/2;

            initNext();

            handler.moveTetromino(0, 0, 0);
            return true;
        }
    }

    private void settleTetromino() {
        Log.d(TAG, "settleTetromino");
        for (int y=0; y<current.getHeight(); ++y) {
            for (int x=0; x<current.getWidth(); ++x) {
                if (current.get(x, y)) {
                    bits[index(x+currentX, y+currentY)] = true;
                    color[index(x+currentX, y+currentY)] = currentColor;
                }
            }
        }

        currentX = -1;
        currentY = -1;
        current = null;

        deleteFullRows();

        if (newTetromino()) {
            postNextTick();
        }
    }

    private void moveDown(int n) {
        Log.d(TAG, "moveDown " + n);
        int len = 0;
        boolean touchedDown = false;
        for (int i = 0; i < n; ++i) {
            touchedDown = !moveDown();
            if (!touchedDown) {
                len++;
            }
            else {
                break;
            }
        }

        handler.moveTetromino(0, len, 0);

        if (touchedDown) {
            settleTetromino();
        }
    }

    private boolean isFull(int y) {
        boolean full = true;
        for (int x = 0; x < width; ++x) {
            full = full && bits[index(x, y)];
            if (!full) {
                break;
            }
        }
        return full;
    }

    private void deleteFullRow(int y) {
        System.arraycopy(bits, 0, bits, width, width*y);
        System.arraycopy(color, 0, color, width, width*y);
    }

    private void deleteFullRows() {
        for (int y = 0; y < height; ++y) {
            if (isFull(y)) {
                deleteFullRow(y);
                handler.deleteFullRow(y);
            }
        }
    }

    private boolean isIntersected(Tetromino t, int X, int Y) {
        for (int y=0; y<t.getHeight(); ++y) {
            for (int x=0; x<t.getWidth(); ++x) {
                if (x + X < width && y + Y < height) {
                    if (bits[index(x + X, y + Y)] & t.get(x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isTouchedDown(Tetromino t, int y) {
        return height < y + t.getHeight();
    }

    private boolean isTouchedLeft(Tetromino t, int x) {
        return x < 0;
    }

    private boolean isTouchedRight(Tetromino t, int x) {
        return width < x + t.getWidth();
    }

    private boolean moveDown() {
        if (!isTouchedDown(current, currentY+1)
            && !isIntersected(current, currentX, currentY+1)) {
            currentY += 1;
            return true;
        }
        else {
            return false;
        }
    }

    public void run() {
        tick();
    }

    private void tick() {
        if (moveDown()) {
            handler.moveTetromino(0, 1, 0);
            postNextTick();
        }
        else {
            settleTetromino();
        }
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
