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

public class CBasket {
    BufferedImage bmaster;
    int currentFrame = 0;
    public int x=600,y=500, w= 120, h= 70, xdest;
    int life=3;
    int dir=0;
    
    public CBasket() {
        try {
            bmaster = ImageIO.read(getClass().getResource("/eggcatch2/resources/basket.png"));
        } catch (IOException e) {}
    }
    
    public void changDir(int x){
        if(this.x > x) dir=1;
        else dir=0;
        xdest= x;
    }
    
    public void paint(Graphics g){
        g.drawImage(bmaster, x, y, w, h, null);
    }
     
    public void move(){
        if(xdest>=(1400-165))
            xdest=1400-165;
        else if(xdest<=0)
            xdest=0;    
        
        if(dir==0){
            while(x<xdest){
                x+=16;
            }
        }else{
            while(x>xdest){
                x-=16;
            }
        }
    }
}
