/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package automatastarter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Panel to draw the animation in the intro screen
 * @author Haydn Yip
 */
public class IntroAnimation extends javax.swing.JPanel {
    //Declare global variables
    Timer animTimer;
    
    //Declare variables for where and the size of the animation being drawn
    int[] animXPositions = {0,25,50,75,100,125,150,175,200};
    int animYPosition = 60;
    int predPreySize = 25;
    
    //Declare variable for the frame number of the animation
    int frame = 0;
            
    /**
     * Creates new form IntroAnimation
     */
    public IntroAnimation() {
        initComponents();
        //Starts a timer to update
        animTimer = new Timer(250, new IntroAnimation.AnimTimerTick());
        animTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Write a string onto the panel
        g.drawString("Predator and Prey", 60, 50);  
        //Every time paintComponent is updated, it will show a certain frame
        //in the animation like a film/film tape
        switch(frame){
            //For each frame, call on the method predPreyAnimPositions to set the positions
            //of the parts of the simple animation
            //There is a total of 12 frames
            case 1: 
                predPreyAnimPositions(0,-1,g);
                break;
            case 2: 
                predPreyAnimPositions(1,-1,g);
                break;
            case 3: 
                predPreyAnimPositions(2,0,g);
                break;
            case 4: 
                predPreyAnimPositions(3,1,g);
                break;
            case 5: 
                predPreyAnimPositions(4,2,g);
                break;
            case 6: 
                predPreyAnimPositions(5,3,g);
                break;
            case 7: 
                predPreyAnimPositions(6,4,g);
                break;
            case 8: 
                predPreyAnimPositions(7,5,g);
                break;
            case 9: 
                predPreyAnimPositions(8,6,g);
                break;
            case 10: 
                predPreyAnimPositions(-1,7,g);
                break;
            case 11: 
                predPreyAnimPositions(-1,8,g);
                break;
            default: 
                predPreyAnimPositions(-1,-1,g);
                break;
        }
    }
    
    //Helper method to change the positions in the animation
    private void predPreyAnimPositions(int preyPos, int predPos, Graphics g){
        //If the prey is not not on the screen
        if(preyPos != -1){
            //Draw a prey at a specific position based on 
            //predetermined positions in the global array
            g.setColor(Color.black);
            g.fillRect(animXPositions[preyPos], animYPosition, predPreySize, predPreySize);
        }
        //If the predator is not not on the screen
        if(predPos != -1){
            //Draw a predator at a specific position based on 
            //predetermined positions in the global array
            g.setColor(Color.red);
            g.fillRect(animXPositions[predPos], animYPosition, predPreySize, predPreySize);
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

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Everything inside this actionPerformed will happen every time the animation timer clicks
     */
    private class AnimTimerTick implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            //Update the animation
            repaint();
            //Change the frame
            frame++;
            //Once the animation has reached the end, loop it back again to the beginning
            if(frame > 11){
                frame = 0;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
