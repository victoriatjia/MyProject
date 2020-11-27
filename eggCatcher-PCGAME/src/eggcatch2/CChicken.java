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

public class CChicken {
    BufferedImage img;
    public int x, y;
    
    public CChicken(int x){
        this.x=x; 
        this.y=30;
        try {
            img = ImageIO.read(getClass().getResource("/eggcatch2/resources/asset_title_chicken.png"));
        } catch (IOException e) {}
    }
    
    public boolean paint(Graphics g) {
        g.drawImage(img, x, y, 150, 100, null);
        return true;
    }
}

