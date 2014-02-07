package com.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

public class TetrisView extends View {

    private static final String TAG = "TetrisView";
    Game game = new Game();

    private Paint paint = new Paint();
    private float pointH = 50;
    private float pointW = 50;

    public TetrisView(Context context) {
        super(context);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawPoint(Canvas canvas, float x, float y, int color) {

        RectF rect = new RectF();
        rect.top = y;
        rect.bottom = y + pointH;
        rect.left = x;
        rect.right = x + pointW;

        paint.setColor(color);
        canvas.drawOval(rect, paint);
    }

    private void drawTetromino(Canvas canvas, float x, float y, int color, Tetromino tetromino) {
        for (int i = 0; i < tetromino.getWidth(); ++i) {
            for (int j = 0; j < tetromino.getHeight(); ++j) {
                if (tetromino.get(i, j)) {
                    drawPoint(canvas, x + i*pointW, y + j*pointH, color);
                }
            }
        }
    }

    private void drawGame(Canvas canvas, float x, float y, Game game) {
        for (int i = 0; i < game.getWidth(); ++i) {
            for (int j = 0; j < game.getHeight(); ++j) {
                if (game.getBit(i, j)) {
                    drawPoint(canvas, x + i*pointW, y + j*pointH, game.getColor(i, j));
                }
            }
        }

        drawTetromino(canvas,
                      game.getCurrentX() * pointH,
                      game.getCurrentY() * pointW,
                      game.getCurrentColor(),
                      game.getCurrentTetromino());
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public void onDraw(Canvas canvas) {

        // paint.setColor(Color.BLACK);
        // paint.setStrokeWidth(3);
        // canvas.drawRect(30, 30, 80, 80, paint);

        // paint.setStrokeWidth(0);
        // paint.setColor(Color.CYAN);
        // canvas.drawRect(33, 60, 77, 77, paint );

        // paint.setColor(Color.YELLOW);
        // canvas.drawRect(33, 33, 77, 60, paint );

        // drawPoint(canvas, 100, 100);

        drawGame(canvas, 0, 0, game);
    }
}
