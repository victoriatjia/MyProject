package com.victoria.eggcatch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CEgg {

    Bitmap img;
    int x, y;
    int dx, dy;
    int score;

    public CEgg(Bitmap bmp, int xx, int ss){
        img=bmp;
        x=xx+135-35;    //x ayam + width ayam - (width egg/2)
        y=250;
        dy=25;
        score=ss;
    }


    public boolean move(){
        y+=dy;

        if(y>1525){
            y=1550;
            return false;
        }

        return true;
    }

    public void Draw(Canvas g){
        g.drawBitmap(img, x, y, null);
    }
}
