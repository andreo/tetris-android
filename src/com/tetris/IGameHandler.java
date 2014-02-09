package com.tetris;

public interface IGameHandler {
    void moveTetromino(int x, int y, int rotation);
    void deleteFullRow(int y);
    Tetromino nextTetromino();
    int nextColor();
    void gameOver();
}
