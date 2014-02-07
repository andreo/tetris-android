
package com.tetris;

public class TetrominoFactory {

    private Tetromino[] tetrominoList = new Tetromino[7];

    public TetrominoFactory() {

        tetrominoList[0] = new Tetromino(4, 1, new boolean[] {
                true, true, true, true
            });

        tetrominoList[1] = new Tetromino(3, 2, new boolean[] {
                true, false, false,
                true, true, true
            });

        tetrominoList[2] = new Tetromino(3, 2, new boolean[] {
                false, false, true,
                true, true, true
            });

        tetrominoList[3] = new Tetromino(2, 2, new boolean[] {
                true, true,
                true, true
            });

        tetrominoList[4] = new Tetromino(3, 2, new boolean[] {
                false, true, true,
                true, true, false
            });

        tetrominoList[5] = new Tetromino(3, 2, new boolean[] {
                false, true, false,
                true, true, true
            });

        tetrominoList[6] = new Tetromino(3, 2, new boolean[] {
                true, true, false,
                false, true, true
            });
    }

    public int getSize() {
        return tetrominoList.length;
    }

    public Tetromino get(int index) {
        return new Tetromino(tetrominoList[index]);
    }
}
