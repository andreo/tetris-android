package com.tetris;

public interface GameHandler {
    void invalidate();
    Tetromino nextTetromino();
    int nextColor();
}
