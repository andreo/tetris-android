package com.tetris;

public interface IGameHandler {
    void moveTetromino(int x, int y, int rotation);
    Tetromino nextTetromino();
    int nextColor();
}
