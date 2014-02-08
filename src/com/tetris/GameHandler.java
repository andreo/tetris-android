package com.tetris;

public interface GameHandler {
    void moveTetromino(int x, int y, int rotation);
    Tetromino nextTetromino();
    int nextColor();
}
