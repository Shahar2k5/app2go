package com.example.android.app2go;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by baryariv1 on 8/28/15.
 */
public class SimpleGestureFilter extends GestureDetector.SimpleOnGestureListener {

    private final static int MODE_SOLID = 1;
    private final static int MODE_DYNAMIC = 2;

    private final static int ACTION_FAKE = -13; //just an unlikely number
    private static final String TAG = SimpleGestureFilter.class.getSimpleName();
    private int swipe_Min_Distance = 0;
    private int swipe_Max_Distance = 350;
    private int swipe_Min_Velocity = 0;

    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;

    private final Activity context;
    private final GestureDetector detector;
    private final SimpleGestureListener listener;

    private boolean startX;
    private boolean startY;

    private float lastY;

    public SimpleGestureFilter(Activity context, SimpleGestureListener sgl) {

        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = sgl;

        detector.setIsLongpressEnabled(false);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.running)
            return false;

        boolean result = this.detector.onTouchEvent(event);

        if (this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {
            if (event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }
        }

        Log.d(TAG, "Touch event is: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_UP ) {
            if (listener != null) {
                listener.onTouchEnded(startX, startY);
            }
        }

        //else just do nothing, it's Transparent
        return true;
    }

    public void setMode(int m) {
        this.mode = m;
    }

    public int getMode() {
        return this.mode;
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public void setSwipeMaxDistance(int distance) {
        this.swipe_Max_Distance = distance;
    }

    public void setSwipeMinDistance(int distance) {
        this.swipe_Min_Distance = distance;
    }

    public void setSwipeMinVelocity(int distance) {
        this.swipe_Min_Velocity = distance;
    }

    public int getSwipeMaxDistance() {
        return this.swipe_Max_Distance;
    }

    public int getSwipeMinDistance() {
        return this.swipe_Min_Distance;
    }

    public int getSwipeMinVelocity() {
        return this.swipe_Min_Velocity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        listener.onTouchEnded(startX, startY);
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg) {

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg) {

        if (this.mode == MODE_DYNAMIC) {// we owe an ACTION_UP, so we fake an
            arg.setAction(ACTION_FAKE); //action which will be converted to an ACTION_UP later.
            this.context.dispatchTouchEvent(arg);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float xDistance = 0;
        if(e1 != null && e2 != null)
            xDistance = Math.abs(e1.getX() - e2.getX());
        distanceX = Math.abs(distanceX);

        //Horizontal slide
        if (distanceX > this.swipe_Min_Velocity && xDistance > this.swipe_Min_Distance) {
            listener.onHorSlide(e1.getX());
            listener.onChangeX(e2.getX());
        }
        //Vertical slide
        if (e1 != null && e2 != null && e1.getAction() == MotionEvent.ACTION_DOWN) {
            listener.onVerSlide(e1.getY());
            listener.onChangeY(e2.getY());
        }

        if (e1 != null && e2 != null) {
            startX = e1.getX() <= e2.getX();
            startY = e1.getY() <= e2.getY();
        }

        lastY = e2.getY();

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        listener.onTouchStart(lastY < e.getY());
        return super.onDown(e);
    }

    public interface SimpleGestureListener {
        void onChangeY(float y);
        void onChangeX(float x);
        void onVerSlide(float y);
        void onHorSlide(float x);
        void onTouchEnded(boolean toRight, boolean toBottom);
        void onTouchStart(boolean toBottom);
    }

}
