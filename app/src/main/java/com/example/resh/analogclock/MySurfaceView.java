package com.example.resh.analogclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

public class MySurfaceView extends SurfaceView implements Runnable {

    public static Boolean prefChanged = false;

    private Context context;
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;
    private Paint paint = null;
    private int outlineColor;
    private int hourMarkingsColor;
    private int hourHandColor;
    private int minuteHandColor;
    private int secondHandColor;

    private float length;

    public MySurfaceView(Context context, float l) {
        super(context);
        this.length = l;
        this.context = context;
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(10);
        surfaceHolder = getHolder();
        updateColorPreferences();
            }

    private void updateColorPreferences() {
        SharedPreferences sharedPref = context.getSharedPreferences("ColorSettings", Context.MODE_PRIVATE);
        outlineColor = Color.parseColor(sharedPref.getString("Clock Outline","#FF0000"));
        hourMarkingsColor = Color.parseColor(sharedPref.getString("Hour Markings","#00FF00"));
        hourHandColor = Color.parseColor(sharedPref.getString("Hour Hand","#FF0000"));
        minuteHandColor = Color.parseColor(sharedPref.getString("Minute Hand","#FF0000"));
        secondHandColor = Color.parseColor(sharedPref.getString("Second Hand","#0000FF"));
        prefChanged = false;
    }
    public void onResumeMySurfaceView() {

        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public void onPauseMySurfaceView() {

        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.d("DEBUG", "Thread join failed: " + e.getMessage());
            }
        }
    }

    public void run() {
        int hour = 0, min =0, sec = 0;
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.save();
                canvas.drawColor(Color.BLACK);
                if (prefChanged) {
                    updateColorPreferences();
                }
                RegularPolygon secMarks = new RegularPolygon(
                        getWidth() / 2,
                        getHeight() / 2,
                        length,
                        60,
                        canvas,
                        outlineColor);
                RegularPolygon hourMarks = new RegularPolygon(
                        getWidth()/2,
                        getHeight()/2,
                        length-20,
                        12,
                        canvas,
                        hourMarkingsColor
                );
                RegularPolygon hourNumbers = new RegularPolygon(
                        getWidth()/2,
                        getHeight()/2,
                        length-50,
                        12,
                        canvas,
                        hourMarkingsColor
                );
                RegularPolygon hourHand = new RegularPolygon(
                        getWidth()/2,
                        getHeight()/2,
                        length-100,
                        60,
                        canvas,
                        hourHandColor
                );
                RegularPolygon minHand = new RegularPolygon(
                        getWidth()/2,
                        getHeight()/2,
                        length-70,
                        60,
                        canvas,
                        minuteHandColor
                );
                RegularPolygon secHand = new RegularPolygon(
                        getWidth()/2,
                        getHeight()/2,
                        length-50,
                        60,
                        canvas,
                        secondHandColor
                );
                secMarks.drawPoints();
                hourMarks.drawPoints();
                hourNumbers.drawNumbers();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("DEBUG", "Sleep failed: " + e.getMessage());
                }
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR);
                min = calendar.get(Calendar.MINUTE);
                sec = calendar.get(Calendar.SECOND);

                secHand.drawRadius(sec+45);
                minHand.drawRadius(min+45);
                hourHand.drawRadius(hour*5 + (min/12) + 45);
                canvas.restore();
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

}
