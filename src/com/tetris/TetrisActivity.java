package com.tetris;

import android.app.Activity;
import android.os.Bundle;

public class TetrisActivity extends Activity
{
    private TetrisView drawView;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        drawView = new TetrisView(this);
        setContentView(drawView);
    }
}
