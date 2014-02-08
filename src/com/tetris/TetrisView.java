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
    Game game;

    private Paint paint = new Paint();
    private float pointH = 15;
    private float pointW = 15;

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

        if (game.getCurrentTetromino() != null) {
            drawTetromino(canvas,
                          game.getCurrentX() * pointH,
                          game.getCurrentY() * pointW,
                          game.getCurrentColor(),
                          game.getCurrentTetromino());
        }
    }

    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, game.getWidth() * pointW, game.getHeight() * pointH, paint);
        drawGame(canvas, 0, 0, game);
    }
}
