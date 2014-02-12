package com.tetris;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;

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

        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int iTmp = sp.load(this, R.raw.click, 1);

        game = new Game(10, 15,
                        new IGameHandler() {

                            public void moveTetromino(int x, int y, int r) {
                                tetrisView.invalidate();
                                if (x != 0 || r != 0 || 1 < y) {
                                    Log.d(TAG, "moveTetromino x = " + x + " y = " + y);
                                    sp.play(iTmp, 1, 1, 0, 0, 1);
                                }
                            }

                            public void deleteFullRow(int y) {
                                tetrisView.invalidate();
                            }

                            public Tetromino nextTetromino() {
                                return factory.get(rnd.nextInt(factory.getSize()));
                            }

                            public int nextColor() {
                                return 0xFF000000 | rnd.nextInt();
                            }

                            public void gameOver() {
                                TextView text = (TextView) findViewById(R.id.caption);
                                text.setText("Game Over!");
                            }
                        },
                        new Handler());
        tetrisView.setGame(game);

        bindMessage(R.id.button_move_left, Game.MoveLeft);
        bindMessage(R.id.button_move_right, Game.MoveRight);
        bindMessage(R.id.button_move_down, Game.MoveDown);

        tetrisView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        game.handleMessage(Game.RotateRight);
                        return true;
                    }
                    return false;
                }
            });

        game.handleMessage(Game.Start);
    }
}
