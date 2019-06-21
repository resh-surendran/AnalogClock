package com.example.resh.analogclock;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RegularPolygon {

    private float x0, y0, r;
    private int n;
    private float x[], y[];

    private Canvas c = null;
    private Paint p = null;

    public RegularPolygon(float x0, float y0, float r, int n, Canvas c, int color) {
        this.x0 = x0;
        this.y0 = y0;
        this.r = r;
        this.n = n;
        this.c = c;
        this.p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(10);

        x = new float[n];
        y = new float[n];

        for (int i=0; i<n; i++) {
            x[i] = (float) (x0 + r*Math.cos(2*Math.PI*i/n));
            y[i] = (float) (y0 + r*Math.sin(2*Math.PI*i/n));
        }
    }

    public float getX(int i) {
        return x[i%n];
    }

    public float getY(int i) {
        return y[i%n];
    }

    public void drawRegPoly() {

        for (int i=0; i <n; i++) {
            c.drawLine(x[i], y[i], x[(i+1)%n], y[(i+1)%n], p);
        }
    }

    public void drawPoints() {

        for (int i=0; i < n; i++) {
            c.drawCircle(x[i], y[i], 4, p);
        }
    }

    public void drawRadius(int i) {
        c.drawLine(x0, y0, x[i%n], y[i%n], p);
    }

    public void drawNumbers() {
        for (int hour = 0; hour < n; hour++) {
            String hourValue = String.valueOf((hour+12)%12);
            if (hourValue.equals("0")) {
                hourValue = "12";
            }
            p.setTextSize(30);
            c.drawText(hourValue, x[(hour+45)%n], y[(hour+45)%n], p);  // you can draw dots to denote hours as alternative
        }
    }
}
