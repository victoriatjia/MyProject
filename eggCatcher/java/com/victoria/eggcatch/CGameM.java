package com.victoria.eggcatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class CGameM{
    ArrayList<CEgg> eggs=new ArrayList<>();
    ArrayList<CChicken> chickens=new ArrayList<>();
    Bitmap src_basket, bbasket, src_chickens, bchickens, src_frame, bframe, src_exit, bexit;
    Bitmap[]  src_egg= new Bitmap[5], begg= new Bitmap[5];
    CBasket master;
    int position;
    String color;
    boolean surfacestate;
    boolean GameOverState;
    private int changeCount;
    private int prevPosition;
    final private static int NULL = 0;
    final private static int LEFT = 0;
    final private static int CENTER = 1;
    final private static int RIGHT = 2;
    final private static String WHITE = "white";
    final private static String CRACKED = "cracked";
    final private static String GOLD = "gold";
    private int bestScore;
    int delay=0;
    protected int scoreCount;
    public static SharedPreferences pref;
    private Context mContext;
    private SoundHandler soundHandler;


    public CGameM(Context ctx, SoundHandler soundHandler){
        src_basket= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.basket);
        bbasket= Bitmap.createScaledBitmap(src_basket, 300, 170, true);

        src_egg[0]= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.egg_white);
        begg[0]= Bitmap.createScaledBitmap(src_egg[0], 50, 70, true);
        src_egg[1]= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.egg_gold);
        begg[1]= Bitmap.createScaledBitmap(src_egg[1], 50, 70, true);
        src_egg[2]= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.poo);
        begg[2]= Bitmap.createScaledBitmap(src_egg[2], 100, 130, true);
        src_egg[3]= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.egg_bad_broken);
        begg[3]= Bitmap.createScaledBitmap(src_egg[3], 80, 80, true);
        src_egg[4]= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.egg_gold_broken);
        begg[4]= Bitmap.createScaledBitmap(src_egg[4], 80, 80, true);

        src_chickens= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.asset_title_chicken);
        bchickens= Bitmap.createScaledBitmap(src_chickens, 410, 300, true);

        src_frame= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.frame);
        bframe= Bitmap.createScaledBitmap(src_frame, 600, 400, true);

        src_exit= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.exit);
        bexit= Bitmap.createScaledBitmap(src_exit, 300, 150, true);

        scoreCount = NULL;
        this.soundHandler = soundHandler;
        this.mContext= ctx;
        getBestScore();

        master=new CBasket(bbasket);   //create basket
        surfacestate=true;
        GameOverState=false;
    }

    private void getBestScore() {
        pref = mContext.getSharedPreferences("com.victoria.eggCatcher", Context.MODE_PRIVATE);
        bestScore = pref.getInt("best", 0);
    }

    public void gameOver() {
        GameOverState=true;
        if(scoreCount > bestScore){
            bestScore = scoreCount;
            pref = mContext.getSharedPreferences("com.victoria.eggCatcher", Context.MODE_PRIVATE);
            Editor editor = pref.edit();
            editor.putInt("best", bestScore);
            editor.commit();
        }
    }

    public void DrawGame(Canvas g){
        for(int i=0; i<eggs.size(); i++){    //draw random eggs
            CEgg e=eggs.get(i);
            e.Draw(g);
        }
        master.Draw(g);    //draw basket
        int x= -70;
        for(int i=0; i<3; i++){    //draw 3 chickens
            CChicken c=new CChicken(bchickens, x);
            chickens.add(c);
            CChicken cf=chickens.get(i);
            cf.Draw(g);
            x+= 370;
        }

        Paint pen=new Paint();
        //pen.setColor(Color.WHITE);
        //pen.setStrokeWidth(3);
        //g.drawRect(320, 1800, 770, 1990, pen);
        g.drawBitmap(bframe, 255, 1680, pen);
        pen.setColor(Color.BLACK);
        pen.setTextSize(54);
        g.drawText("Score: " + scoreCount, 390,1880, pen );
        g.drawText("Best Score: " + bestScore, 390,1930, pen );

        if(GameOverState){   //if lose, then game over
            Paint pen2=new Paint();
            pen.setColor(Color.WHITE);
            pen.setStrokeWidth(3);
            g.drawRect(0, 1030, 1100, 1120, pen);

            pen2.setColor(Color.RED);
            pen2.setTextSize(64);
            g.drawText("Game Over!", 380,1100, pen2 );
            g.drawBitmap(bexit, 385, 1150, pen);
        }
    }

    public void createEgg(int position, String color){   //隨機產生敵人
        CEgg e;
        int xpos=0, score;
        Bitmap eggtype;

        switch (position) {//0 for left; 1 for center; 2 for right
            case 0:
                xpos=30;
                break;

            case 1:
                xpos= 400;
                break;

            case 2:
                xpos= 770;
                break;

            default:
                break;
        }

        if(color.equals("white")) {
            eggtype = begg[0];
            score=1;
        }
        else if(color.equals("gold")){
            eggtype= begg[1];
            score=3;
        }
        else{
            eggtype= begg[2];
            score=-1;
        }

        //int ran=(int)(Math.random()*101)+1;

        delay++;
        if(delay==18){
            delay=0;
            e=new CEgg(eggtype, xpos, score);
            int s= e.y;
            if(e.y== 250 && e.score== 3){
                soundHandler.playSoundEffect(1, 50);
            }
            eggs.add(e);
        }
    }

    protected int generateRandomPosition() {

        Random rand = new Random();
        int pos = rand.nextInt(3);
        //conditions prevent same position > 3 times
        if(prevPosition == pos){
            changeCount++;
        }else{
            changeCount = NULL;
        }
        if(changeCount == 3){
            if(pos == RIGHT){
                pos = rand.nextInt(1);
            }else if(pos == CENTER){
                pos = (rand.nextFloat() > 0.5) ? RIGHT : LEFT;
            }else{
                pos = (rand.nextFloat() > 0.5) ? CENTER : RIGHT;
            }
            changeCount = NULL;
        }
        prevPosition = pos;
        return pos;
    }

    private String getTypeOfEgg() {

        Random rand = new Random();
        Float randF = rand.nextFloat();
        String color;

        if(randF >= 0.75){
            color = GOLD;
        }else if(randF<0.75 && randF>=0.5){
            color = CRACKED;
        }else{
            color = WHITE;
        }
        return color;
    }

    public void MasterDir(int x){    //改變主角方向
        master.changeDir(x);
    }

    public void AllMove(){   //移動全部角色
        if(GameOverState) return;
        master.move();

        for(CEgg ce: eggs){
            if(!ce.move()){
                eggs.remove(ce);   //飛出畫面就移除
                break;
            }
        }
    }

    public boolean checkCollision(CEgg e, CBasket a){
        Rect em,aa;
        em=new Rect(e.x+5, e.y+5, e.x+80, e.y+80);

        aa=new Rect(a.x+10, a.y+10, a.x+150, a.y+150);

        if(em.intersect(aa)) return true;
        return false;
    }

    public void run(){
        if(surfacestate && GameOverState==false) {
            position = generateRandomPosition();
            color = getTypeOfEgg();
            createEgg(position, color);

            AllMove();

            for (int i=0; i<eggs.size(); i++) {
                CEgg e=eggs.get(i);
                boolean collide= checkCollision(e, master);
                if (collide) {
                    if(e.score== 1) {
                        soundHandler.playSoundEffect(3, 50);
                    }
                    else if(e.score== 3){
                        soundHandler.playSoundEffect(4, 50);
                    }
                    else{
                        soundHandler.playSoundEffect(0, 50);
                    }
                    scoreCount+= e.score;
                    eggs.remove(e);
                    break;
                }
                else if (e.y== 1525 && !collide) {
                    if(e.img==begg[0])
                        e.img= begg[3];
                    else if(e.img==begg[1])
                        e.img= begg[4];
                    if(GameOverState==false)
                        if(e.img == begg[2])
                            soundHandler.playSoundEffect(6, 50);
                        else
                            soundHandler.playSoundEffect(2, 50);
                    if(e.score != -1)
                        master.life--;
                    if(master.life<=0) {
                        gameOver();
                    }
                }
            }
        }
    }
}
