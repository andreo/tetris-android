package com.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TetrisActivity extends Activity
{
    private static final String TAG = "TetrisActivity";
    private TetrisView drawView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.main);

        final TetrisView tetrisView = (TetrisView) findViewById(R.id.view_tetris);

        {
            final Button button = (Button) findViewById(R.id.button_rotate_right);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tetrisView.getGame().rotateRight();
                        tetrisView.invalidate();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_rotate_left);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tetrisView.getGame().rotateLeft();
                        tetrisView.invalidate();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_move_left);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tetrisView.getGame().moveLeft();
                        tetrisView.invalidate();
                    }
                });
        }

        {
            final Button button = (Button) findViewById(R.id.button_move_right);
            button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tetrisView.getGame().moveRight();
                        tetrisView.invalidate();
                    }
                });
        }
    }
}
