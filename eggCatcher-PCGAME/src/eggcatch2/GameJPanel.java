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
import java.util.Random; 
import java.io.*;
import java.util.ArrayList;
import java.applet.*;

public class GameJPanel extends javax.swing.JPanel {
    
    CBasket basket;
    ArrayList<CEgg> egg;
    ArrayList<CChicken> chicken;
    int sx = 0, sy = 0;
    AudioClip clip;
    int scoreCount;
    final private static int NULL = 0;
    final private static int LEFT = 0;
    final private static int CENTER = 1;
    final private static int RIGHT = 2;
    final private static String WHITE = "white";
    final private static String CRACKED = "cracked";
    final private static String GOLD = "gold";
    boolean GameOverState;
    private int changeCount;
    private int prevPosition;
    int position;
    String color;
    Image bg, frame, startbtn;
    int delay;
    
    public GameJPanel() {
        init();
        this.addMouseListener(new CMyListener1());
        this.addMouseMotionListener(new CMyListener1());
        
        try {
            bg = ImageIO.read(getClass().getResource("/eggcatch2/resources/bg_wood2.png"));
            frame = ImageIO.read(getClass().getResource("/eggcatch2/resources/frame.png"));
            startbtn = ImageIO.read(getClass().getResource("/eggcatch2/resources/ic_play.png"));
        } catch (IOException e) {
        }
        
        Timer t1 = new Timer(90,
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if(!GameOverState){ 
                            basket.move();
                            for (int i = 0; i < egg.size(); i++) {
                                CEgg e= egg.get(i);
                                if (egg.get(i).move() == false) {                                
                                    if(e.score!=-1)
                                        basket.life--;
                                    egg.remove(i);
                                    if(basket.life<=0){
                                        GameOverState= true;
                                    }
                                } else {
                                    if (e.collidesWith(basket)) {
                                        if(e.score==-1)
                                            scoreCount--;
                                        else
                                            scoreCount+= e.score;
                                        egg.remove(i);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
        t1.start();
    }
    
    public void init(){
        initComponents();
        GameOverState= false;
        scoreCount=NULL;
        delay=NULL;
        changeCount=NULL;
        basket = new CBasket();
        egg = new ArrayList();
        chicken = new ArrayList();
        
        int x=400;
        for (int i=0;i<3;i++) {
            CChicken c= new CChicken(x);
            chicken.add(c);
            x+= 300;
        }
    }
    
    public void createEgg(int position, String color){   //隨機產生敵人
        CEgg e;
        int xpos=0, score, eggtype;

        switch (position) {//0 for left; 1 for center; 2 for right
            case 0:
                xpos=470;
                break;

            case 1:
                xpos= 770;
                break;

            case 2:
                xpos= 1070;
                break;

            default:
                break;
        }

        if(color.equals("white")) {
            eggtype = 0;
            score=1;
        }
        else if(color.equals("gold")){
            eggtype= 1;
            score=3;
        }
        else{
            eggtype= 2;
            score=-1;
        }

        delay++;
        if(delay==20){
            delay=0;
            e=new CEgg(eggtype, xpos, score);
            int s= e.y;
            egg.add(e);
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
    
    class CMyListener1 extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if((e.getX() >=620 && e.getX() <=740) && (e.getY() >=400 && e.getY() <=450) && GameOverState)
                init();
        }

        public void mouseMoved(MouseEvent e) {
            basket.changDir(e.getX());
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, 1400, 735 , this);
        basket.paint(g);
        for (int i = 0; i < chicken.size(); i++) {
            chicken.get(i).paint(g);
        }
            
        if(!GameOverState){     
            position = generateRandomPosition();
            color = getTypeOfEgg();
            createEgg(position, color);

            for (int i = 0; i < egg.size(); i++) {
                egg.get(i).paint(g);
            }
            
            g.drawImage(frame, 0, 45, 220, 180 , this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Score: " + scoreCount, 50, 130);
            g.drawString("Life: " + basket.life, 50, 160);
        }
        else if(GameOverState){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,3 * 0.1f));
            g2d.fillRect(0, 295, 1400, 65);
            
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,10 * 0.1f));
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
            g.drawString("Game Over!", 500, 350);
            
            g.drawImage(frame, 560, 150, 220, 120 , this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Score: " + scoreCount, 605, 220);
            
            g.drawImage(startbtn, 620, 400, 120, 50 , this);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2266, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1288, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
