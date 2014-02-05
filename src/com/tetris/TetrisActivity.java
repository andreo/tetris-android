package com.tetris;

import android.app.Activity;
import android.os.Bundle;

public class TetrisActivity extends Activity
{
    private DrawView drawView;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);
    }
}
