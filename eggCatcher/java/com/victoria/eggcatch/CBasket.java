package com.victoria.eggcatch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CBasket {
    Bitmap bmaster;
    int x,y;
	int dx;
    int life;
    int done=1;
    int tot;
    public CBasket(Bitmap bmp){
        bmaster=bmp;
        x=400; y=1525;
        dx=0;
        life=3;
    }

    public void Draw(Canvas g){
        if(life>=1){ g.drawBitmap(bmaster, x, y, null); }
    }

    public void move(){
        if(done==1){
            tot= x+dx;	//current x + current cursor x
        }

        if(tot>780)
            tot=780;
        else if(tot<0)
            tot=0;

        if((dx>0 && x<tot) || (dx<0 && x>tot)){	//if kanan n masi < tot (mau lanjut tambah), or if kiri n masi > tot (mau lanjut kurang)
            done=0;
            x+=dx/4;	//biar gerakan basket lebi shun

            if(x>780) x=780;	//kdg bisa kelewatan atau kekurangan
            else if(x<0) x=0;
        }
        else{
            dx=0;
            done=1;
        }
    }
	
    public void changeDir(int xx){	//+ for right, - for left
        dx= xx;
    }
}





