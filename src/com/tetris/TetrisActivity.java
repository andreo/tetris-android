package com.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import java.util.Random;

public class TetrisActivity extends Activity
{
    private static final String TAG = "TetrisActivity";
    private Game game;
    private final TetrominoFactory factory = new TetrominoFactory();
    private final Random rnd = new Random();

    private void bindMessage(int resource, final int message) {
        final Button button = (Button) findViewById(resource);
        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    game.handleMessage(message);
                }
            });
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);
        final TetrisView tetrisView = (TetrisView) findViewById(R.id.view_tetris);

        game = new Game(10, 20,
                        new GameHandler() {

                            public void moveTetromino(int x, int y, int rotation) {
                                tetrisView.invalidate();
                            }

                            public Tetromino nextTetromino() {
                                return factory.get(rnd.nextInt(factory.getSize()));
                            }

                            public int nextColor() {
                                return 0xFF000000 | rnd.nextInt();
                            }
                        },
                        new Handler());
        tetrisView.setGame(game);

        bindMessage(R.id.button_rotate_right, Game.RotateRight);
        bindMessage(R.id.button_rotate_left, Game.RotateLeft);
        bindMessage(R.id.button_move_left, Game.MoveLeft);
        bindMessage(R.id.button_move_right, Game.MoveRight);
        bindMessage(R.id.button_move_down, Game.MoveDown);

        game.handleMessage(Game.Start);
    }
}
