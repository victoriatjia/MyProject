/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eggcatch2;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class CEgg {

    BufferedImage[] img= new BufferedImage[5];
    int score = 0;
    public int x, y = 100, dy, w= 23, h= 23;
    int type;

    public CEgg(int t, int sx, int score) {
        this.type= t;
        this.x = sx;
        this.score= score;
        dy=25;
        try {
            img[0] = ImageIO.read(getClass().getResource("/eggcatch2/resources/egg_white.png"));
            img[1] = ImageIO.read(getClass().getResource("/eggcatch2/resources/egg_gold.png"));
            img[2] = ImageIO.read(getClass().getResource("/eggcatch2/resources/egg_bad.png"));
            img[3] = ImageIO.read(getClass().getResource("/eggcatch2/resources/egg_bad_broken.png"));
            img[4] = ImageIO.read(getClass().getResource("/eggcatch2/resources/egg_gold_broken.png"));
        } catch (IOException e) {}
    }

    public boolean paint(Graphics g) {
        g.drawImage(img[type], x, y, w, h, null);
        return true;
    }

    public boolean move() {
        y+=dy;
        if(y==500){
            if(this.type==1)
                type=4;
            else
                type= 3;
        } 
        if(y>550){
            y=550;
            dy=0;
            return false;
        }
        return true;
    }
    
    public boolean collidesWith(CBasket b){
        int left1, left2;
	int right1, right2;
	int top1, top2;
	int bottom1, bottom2;

	left1 = x;              //egg x
	left2 = b.x;            //basket x
	right1 = x +w;          //egg width
	right2 = b.x+b.w;       //basket width
	top1 = y;               //egg y
 	top2 = b.y;             //basket y
	bottom1 = y + h;        //egg height
	bottom2 = b.y + b.h;    //basket height

	if (bottom1 < top2) return false;   //if bagian bwh egg belum kena bagian atas basket 
	if (right1 < left2) return false;   //if bagian kanan egg belum kena bagian kiri basket
	if (left1 > right2) return false;   //if bagian kiri egg blm kena bgian kanan basket
        
	return true;
    }
}

