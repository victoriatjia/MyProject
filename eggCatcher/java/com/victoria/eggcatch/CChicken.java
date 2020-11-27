package com.victoria.eggcatch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CChicken {
    Bitmap img;
    int x, y;

    public CChicken(Bitmap bem, int xx){
        x=xx;
        y=70;
        img=bem;
    }

    public void Draw(Canvas g){
        g.drawBitmap(img, x, y,null);
    }
}
