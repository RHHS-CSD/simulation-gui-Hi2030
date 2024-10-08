/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatastarter;

import java.awt.Color;
import utils.CardSwitcher;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;

/**
 * Predator and Prey Simulation GUI
 * Panel where the simulation takes place/runs
 * @author Haydn Yip
 */
public class SimulationPanel extends javax.swing.JPanel implements MouseListener {
    public static final String CARD_NAME = "game";
    CardSwitcher switcher;      //Parent panel
    Timer animTimer;            //Timer that will be used to update the panel
    
//Variables to control the GUI elements
    int x = 0;
    int y = 0;
    boolean customPred, customPrey, customFood = false;
    int startXCoord, startYCoord, endXCoord, endYCoord, boxSize;
    int frameNum, predPopulation, preyPopulation, numFood = 0;
    
    //Create an object for the engine in order to access its
    //variables and other methods
    SimulationGUIEngine engine = new SimulationGUIEngine();

    /**
     * Creates new form GamePanel
     */
    public SimulationPanel(CardSwitcher p) {
        initComponents();
        //Set size of the panel to be the same as the frame
        setSize(SimulationFrame.WIDTH, SimulationFrame.HEIGHT);

        this.setFocusable(true);

        //Tell the program we want to listen to the mouse
        addMouseListener(this);
        //Tells us the panel that controls this one
        switcher = p;
        //Create and start a Timer for the 'animation'/graphics
        animTimer = new Timer(100, new AnimTimerTick());
        //Variables is used to reset the count on the number of frames that have gone by
        frameNum = 0;
        //Call on the engine to set up the positions of the predator and prey
        //before the simulation starts
        engine.initialPositionSet();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Draw grid variables
        startXCoord = 11;
        startYCoord = 46;
        endXCoord = 511;
        endYCoord = 546;
        boxSize = 25;
        
        //Draw grid
        for(int i = 0; i <= engine.inputSize; i++){
            g.drawLine(startXCoord, (startYCoord + i * boxSize), endXCoord, (startYCoord + i * boxSize));
        }
        for(int j = 0; j <= engine.inputSize; j++){
            g.drawLine((startXCoord + j * boxSize), startYCoord, (startXCoord + j * boxSize), endYCoord);
        }
        
        //Draw predators, prey and food
        drawPredators(g, startXCoord, startYCoord, boxSize);
        drawPrey(g, startXCoord, startYCoord, boxSize);
        drawFood(g, startXCoord, startYCoord, boxSize);
    }
    
    //A method that controls the position in which each predator is drawn
    //based on their determined positions that are updated in the engine
    private void drawPredators(Graphics g, int startXCoord, int startYCoord, int boxSize){
        //Predators are represented in red
        g.setColor(Color.red);
        //For each predator
        for(int i = 0; i < (engine.predRowPosition).size(); i++){
            int predRow = engine.predRowPosition.get(i);
            int predColumn = engine.predColumnPosition.get(i);
            //Draw a predator based on where the top left of the grid starts and the graphical size of 
            //the predator, and do so for all the predators
            g.fillRect((startXCoord + predColumn * boxSize), (startYCoord + predRow * boxSize), boxSize, boxSize);
            //Update the number of predators in the grid
            predPopulation = engine.predRowPosition.size();
        }
    }
    
    //A method that controls the position in which each prey is drawn
    //based on their determined positions that are updated in the engine
    private void drawPrey(Graphics g, int startXCoord, int startYCoord, int boxSize){
        //Prey are represented in black
        g.setColor(Color.black);
        //For each prey
        for(int i = 0; i < (engine.preyRowPosition).size(); i++){
            int preyRow = engine.preyRowPosition.get(i);
            int preyColumn = engine.preyColumnPosition.get(i);
            //Draw a prey based on where the top left of the grid starts and the graphical size of 
            //the prey, and do so for all prey
            g.fillRect((startXCoord + preyColumn * boxSize), (startYCoord + preyRow * boxSize), boxSize, boxSize);
            //Update the number of prey in the grid
            preyPopulation = engine.preyRowPosition.size();
        }
    }
    
    //A method that controls the position in which each food item is drawn
    //based on their determined positions that are updated in the engine
    private void drawFood(Graphics g, int startXCoord, int startYCoord, int boxSize){
        //Predators are represented in green
        g.setColor(Color.green);
        //For each food
        for(int i = 0; i < (engine.foodRowPosition).size(); i++){
            int foodRow = engine.foodRowPosition.get(i);
            int foodColumn = engine.foodColumnPosition.get(i);
            //Draw a food item based on where the top left of the grid starts and the graphical size of 
            //the food item, and do so for all food items
            g.fillRect((startXCoord + foodColumn * boxSize), (startYCoord + foodRow * boxSize), boxSize, boxSize);
            //Update the number of food in the grid
            numFood = engine.foodRowPosition.size();
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

        backButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        speedSlider = new javax.swing.JSlider();
        speedLabel = new javax.swing.JLabel();
        customLabel = new javax.swing.JLabel();
        customComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        statusText = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));

        backButton.setText("Quit Simulation");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        speedSlider.setMaximum(500);
        speedSlider.setMinimum(10);
        speedSlider.setValue(100);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        speedLabel.setText("Speed");

        customLabel.setText("Custom");

        customComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Predator", "Prey", "Food" }));
        customComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customComboBoxActionPerformed(evt);
            }
        });

        statusText.setEditable(false);
        statusText.setColumns(20);
        statusText.setLineWrap(true);
        statusText.setRows(5);
        statusText.setText("Status");
        jScrollPane1.setViewportView(statusText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(speedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customComboBox, 0, 93, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(speedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(speedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        //When the back button is clicked, stop the simulation from running
        //and head back to the home screen
        switcher.switchToCard(IntroPanel.CARD_NAME);
        animTimer.stop();
    }//GEN-LAST:event_backButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        //When the play button is clicked, run the animation
        animTimer.start();
    }//GEN-LAST:event_playButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        //When the play button is clicked, stop the animation
        animTimer.stop();
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        //Change and update the speed of the animation everytime the slider is changed
        animTimer.setDelay(speedSlider.getValue());
    }//GEN-LAST:event_speedSliderStateChanged

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        //When the reset button is clicked, stop the animation
        animTimer.stop();
        //Reset the  arrays in the engine to zero 
        engine.predRowPosition.clear();
        engine.predColumnPosition.clear();
        engine.preyRowPosition.clear();
        engine.preyColumnPosition.clear();
        engine.foodRowPosition.clear();
        engine.foodColumnPosition.clear();
        //Set the grid back up with new predators and prey
        engine.initialPositionSet();
        //Reset the statuses
        frameNum = 0;
        predPopulation = engine.predRowPosition.size();
        preyPopulation = engine.preyRowPosition.size();
        numFood = engine.foodRowPosition.size();
        //Update the screen with the new predator, prey, and food positions
        repaint();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void customComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customComboBoxActionPerformed
        //Whenever the value in the drop down menu changes
        //If the user wants to add predators
        if(customComboBox.getSelectedItem().equals("Predator")){
            customPred = true;
            customPrey = false;
            customFood = false;
        } 
        //If the user wants to add prey
        else if(customComboBox.getSelectedItem().equals("Prey")){
            customPred = false;
            customPrey = true;
            customFood = false;
        } 
        //If the user wants to add food
        else if(customComboBox.getSelectedItem().equals("Food")){
            customPred = false;
            customPrey = false;
            customFood = true;
        } 
        //Otherwise, the user shouldn't be able to add anything
        else {
            customPred = false;
            customPrey = false;
            customFood = false;
        }
    }//GEN-LAST:event_customComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> customComboBox;
    private javax.swing.JLabel customLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton playButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JTextArea statusText;
    // End of variables declaration//GEN-END:variables

    /**
     * This event captures a click which is defined as pressing and releasing in the same area
     * @param me
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        //When the user clicks
        System.out.println("Click: " + me.getX() + ":" + me.getY());
        //Get position of the click
        x = me.getX();
        y = me.getY();
        //If the click is inside the grid
        if(x >= startXCoord && x <= endXCoord && y >= startXCoord && y <= endYCoord){
            //Translate the click X and Y positions to grid X and Y positions
            int xPos = topLeftX(x);
            int yPos = topLeftY(y);
            
            //If the user selected to add a predator
            if(customPred == true){
                //Add the new position to the global predator array in the engine
                engine.predRowPosition.add(yPos-1);
                engine.predColumnPosition.add(xPos-1);
            } 
            //If the user selected to add a prey
            else if (customPrey == true){
                //Add the new position to the global prey array in the engine
                engine.preyRowPosition.add(yPos-1);
                engine.preyColumnPosition.add(xPos-1);
            } 
            //If the user selected to add a food item
            else if (customFood == true){
                //Add the new position to the global food array in the engine
                engine.foodRowPosition.add(yPos-1);
                engine.foodColumnPosition.add(xPos-1);
            }
        }
        //Update the screen
        repaint();
    }
    
    //Method to translate the clicked X position to the grid's X position
    private int topLeftX(int currentX){
        int columnNum = 0;
        //Finds the point where the click's X position is less than a point on the grid
        for(int i = 0; i < engine.inputSize; i++){
            //When the point's X position is less than the grid
            //Then the point on the grid is based on the number of columns counted
            if(startXCoord + i * boxSize >= currentX){
                System.out.println(i);
                break;
            }
            columnNum++;
        }
        return columnNum;
    }
    
    //Method to translate the clicked Y position to the grid's Y position
    private int topLeftY(int currentY){
        int rowNum = 0;
        //Finds the point where the click's Y position is less than a point on the grid
        for(int i = 0; i < engine.inputSize; i++){
            //When the point's Y position is less than the grid
            //Then the point on the grid is based on the number of rows counted
            if(startYCoord + i * boxSize >= currentY){
                System.out.println(i);
                break;
            }
            rowNum++;
        }
        return rowNum;
    }

    /**
     * When the mouse button is pressed
     * @param me
     */
    public void mousePressed(MouseEvent me) {
        //System.out.println("Press: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse button is released
     * @param me
     */
    public void mouseReleased(MouseEvent me) {
        //System.out.println("Release: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse enters the area
     * @param me
     */
    public void mouseEntered(MouseEvent me) {
        //System.out.println("Enter: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse exits the panel
     * @param me
     */
    public void mouseExited(MouseEvent me) {
        //System.out.println("Exit: " + me.getX() + ":" + me.getY());
    }

    /**
     * Everything inside this actionPerformed will happen every time the
     * animation timer clicks.
     */
    private class AnimTimerTick implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            //Signal the engine to go to the next frame in the simulation animation
            engine.next(engine.predSign, engine.preySign, engine.foodSign);
            //Count number of frames
            frameNum++;
            //Update the status text field
            statusText.setText("Frame Number: " + frameNum + "\nPred. Pop.: " + predPopulation + 
                    "\nPrey. Pop.: " + preyPopulation + "\nFood Amount: " + numFood);
            //Update the screen
            repaint();
        }
    }
}
