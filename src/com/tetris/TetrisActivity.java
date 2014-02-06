package com.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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
    }
}
