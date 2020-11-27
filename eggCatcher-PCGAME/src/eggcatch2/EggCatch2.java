/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eggcatch2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author victo
 */
public class EggCatch2 {

     public static void main(String[] args) {
         final JFrame f=new JFrame();
        final GameJPanel gm=new GameJPanel();
        f.setSize(1400,735);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gm);
        f.setVisible(true);
        
        Timer timer = new Timer(100,
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        f.repaint();
                    }
                });
        timer.start();
    }
    
}
