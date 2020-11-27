package com.victoria.eggcatch;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CMySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private Context mContext = null;
    private SurfaceHolder mHolder = null;
    private Canvas mCanvas = null;
    private boolean surfaceViewStatus = false, exitgame=false;
    private Thread mDrawThread = null;
    private SoundHandler soundHandler;
    CGameM GM;
    float downX, downY, upX, upY;

    public CMySurface(Context context, SoundHandler s) {
        super(context);
        this.soundHandler = s;
        init(context);
    }
	private void init(Context context) {
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        GM=new CGameM(mContext, soundHandler);
    }

  

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceViewStatus = true;
        if(mDrawThread == null) {
            mDrawThread = new Thread(this);
        }
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceViewStatus = false;
        GM.surfacestate = false;
        if(mDrawThread != null) {
            try {
                mDrawThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mDrawThread = null;
        }
    }

    public void run(){
        while(surfaceViewStatus) {
            GM.run();

            try {
                mCanvas = mHolder.lockCanvas();
                mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_wood), 0, 0, null);

                GM.DrawGame(mCanvas);
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
        /*Intent it = new Intent(mContext, mainmenu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(it);*/
        System.exit(0);
    }

    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()) { // 判斷觸控的動作
            case MotionEvent.ACTION_DOWN: // 按下
                downX = event.getX();
                downY = event.getY();
                //if(GM.GameOverState==true){
                    if((downX>= 350 && downX<650) && (downY>= 1200 && downY<1350)){
                        surfaceViewStatus= false;

                    }

                //}

                return true;
            case MotionEvent.ACTION_MOVE: // 拖曳
                return true;
            case MotionEvent.ACTION_UP: // 放開
                upX = event.getX();
                upY = event.getY();
                float x1= upX-downX;
                float x=Math.abs(upX-downX);
                float y=Math.abs(upY-downY);
                double z=Math.sqrt(x*x+y*y);
                int jiaodu=Math.round((float)(Math.asin(y/z)/Math.PI*180));//角度

                if (upY < downY && jiaodu>45) {//上
                    GM.MasterDir(0);
                }else if(upY > downY && jiaodu>45) {//下
                    GM.MasterDir(0);
                }else if(upX < downX && jiaodu<=45) {//左
                    int dir= Math.round(x1);
                    GM.MasterDir(dir);
                }else if(upX > downX && jiaodu<=45) {//右
                    int dir= Math.round(x1);
                    GM.MasterDir(dir);
                }
                return true;
        }

        return super.onTouchEvent(event);
    }
}

