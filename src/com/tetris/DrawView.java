package com.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.graphics.RectF;

public class DrawView extends View {

    Game game = new Game();

    private Paint paint = new Paint();
    private float pointH = 50;
    private float pointW = 50;

    public DrawView(Context context) {
        super(context);            
    }

    private void drawPoint(Canvas canvas, float x, float y) {

        RectF rect = new RectF();
        rect.top = y;
        rect.bottom = y + pointH;
        rect.left = x;
        rect.right = x + pointW;

        canvas.drawOval(rect, paint);
    }

    private void drawTetromino(Canvas canvas, float x, float y, Tetromino tetrimino) {
        paint.setColor(tetrimino.getColor());
        for (int i = 0; i < tetrimino.getWidth(); ++i) {
            for (int j = 0; j < tetrimino.getHeight(); ++j) {
                if (tetrimino.get(i, j)) {
                    drawPoint(canvas, x + i*pointW, y + j*pointH);
                }
            }
        }
    }

    private void drawGame(Canvas canvas, float x, float y, Game game) {
        for (int i = 0; i < game.getWidth(); ++i) {
            for (int j = 0; j < game.getHeight(); ++j) {
                if (game.getBit(i, j)) {
                    paint.setColor(game.getColor(i, j));
                    drawPoint(canvas, x + i*pointW, y + j*pointH);
                }
            }
        }

        drawTetromino(canvas, 0, 0, game.getCurrentTetromino());
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
