package com.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TetrisActivity extends Activity
{
    private static final String TAG = "TetrisActivity";
    private Game game;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);
        final TetrisView tetrisView = (TetrisView) findViewById(R.id.view_tetris);

        game = new Game(new GameHandler() {
                public void invalidate() {
                    tetrisView.invalidate();
                }
            });
        tetrisView.setGame(game);

        {
            final Button button = (Button) findViewById(R.id.button_rotate_right);
            Log.d(TAG, "button" + button);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        game.rotateRight();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_rotate_left);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        game.rotateLeft();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_move_left);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        game.moveLeft();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_move_right);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        game.moveRight();
                    }
                });
        }
    }
}
