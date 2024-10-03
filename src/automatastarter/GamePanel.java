/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatastarter;

import java.awt.Color;
import utils.CardSwitcher;
import utils.ImageUtil;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author michael.roy-diclemen
 */
public class GamePanel extends javax.swing.JPanel implements MouseListener {

    public static final String CARD_NAME = "game";

    CardSwitcher switcher; // This is the parent panel
    Timer animTimer;
    // Image img1 = Toolkit.getDefaultToolkit().getImage("yourFile.jpg");
    BufferedImage img1;
    //variables to control your animation elements
    int initialX = 150;
    int initialY = 40;
    int cellWidth;
    int cellHeight;
    int numPrey;
    int numPred;
    boolean add;
    PredatorSimulation simulation;

    /**
     * Creates new form GamePanel
     */
    public GamePanel(CardSwitcher p) {
        initComponents();

        //img1 = ImageUtil.loadAndResizeImage("yourFile.jpg", 300, 300);//, WIDTH, HEIGHT)//ImageIO.read(new File("yourFile.jpg"));
        widthSlider.setMinimum(5);
        widthSlider.setMaximum(30);
        widthSlider.setValue(10);
        widthSlider.setMajorTickSpacing(5);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintTrack(true);
        
        heightSlider.setMinimum(5);
        heightSlider.setMaximum(30);
        heightSlider.setValue(10);
        heightSlider.setMajorTickSpacing(5);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintTrack(true);
        
        preySlider.setMinimum(20);
        preySlider.setMaximum(85);
        preySlider.setValue(50);
        preySlider.setMajorTickSpacing(20);
        preySlider.setMinorTickSpacing(10);
        preySlider.setPaintLabels(true);
        preySlider.setPaintTicks(true);
        preySlider.setPaintTrack(true);
        
        predatorSlider.setMinimum(5);
        predatorSlider.setMaximum(15);
        predatorSlider.setValue(10);
        predatorSlider.setMajorTickSpacing(5);
        predatorSlider.setPaintLabels(true);
        predatorSlider.setPaintTicks(true);
        predatorSlider.setPaintTrack(true);
        
        speedSlider.setMinimum(1);
        speedSlider.setMaximum(5);
        speedSlider.setValue(2);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintTrack(true);

        add = false;
        startButton.hide();
        resetButton.hide();
        stopButton.hide();
        speedLabel.hide();
        speedSlider.hide();
        
        this.setFocusable(true);

        // tell the program we want to listen to the mouse
        addMouseListener(this);
        //tells us the panel that controls this one
        switcher = p;
        //create and start a Timer for animation
        animTimer = new Timer(60, new AnimTimerTick());

        //set up the key bindings
        setupKeys();

    }

    private void setupKeys() {
        //these lines map a physical key, to a name, and then a name to an 'action'.  You will change the key, name and action to suit your needs
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftKey");
        this.getActionMap().put("leftKey", new Move("LEFT"));

        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "wKey");
        this.getActionMap().put("wKey", new Move("w"));

        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "dKey");
        this.getActionMap().put("dKey", new Move("d"));

        this.getInputMap().put(KeyStroke.getKeyStroke("X"), "xKey");
        this.getActionMap().put("xKey", new Move("x"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (add){
            numPred = 0;
            numPrey = 0;
            for (int i = 0; i < simulation.grid.length; i++){
                for (int j = 0; j < simulation.grid[i].length; j++){
                    if (simulation.grid[i][j] == 2){
                        g.setColor(Color.RED);
                        numPred++;
                    } else if (simulation.grid[i][j] == 1){
                        g.setColor(Color.CYAN);
                        numPrey++;
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(initialX + cellWidth * i, initialY + cellHeight * j, cellWidth, cellHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(initialX + cellWidth * i, initialY + cellHeight * j, cellWidth, cellHeight);
                }
            }
            g.drawString("Number of prey: " + numPrey, 10, 540);
            g.drawString("Number of predators: " + numPred, 7, 560);
            if (numPrey == 0 && numPred == 0){
                animTimer.stop();
                resetButton.setVisible(true);
                startButton.setVisible(false);
                stopButton.setVisible(false);
            }
        }
        repaint();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        widLabel = new javax.swing.JLabel();
        widthSlider = new javax.swing.JSlider();
        heiLabel = new javax.swing.JLabel();
        heightSlider = new javax.swing.JSlider();
        preyLabel = new javax.swing.JLabel();
        preySlider = new javax.swing.JSlider();
        predLabel = new javax.swing.JLabel();
        predatorSlider = new javax.swing.JSlider();
        createButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        speedSlider = new javax.swing.JSlider();
        speedLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1080, 580));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        widLabel.setText("Width: ");

        heiLabel.setText("Height:");

        preyLabel.setText("% of prey:");

        predLabel.setText("% of predators:");

        createButton.setText("Create grid!");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        speedLabel.setText("Change speed:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(heiLabel))
                    .addComponent(widthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(widLabel))
                    .addComponent(heightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preyLabel)
                    .addComponent(preySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(predLabel)
                    .addComponent(predatorSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createButton)
                    .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetButton)
                    .addComponent(stopButton)
                    .addComponent(speedLabel)
                    .addComponent(startButton))
                .addContainerGap(950, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(widLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(widthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(heiLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(heightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(preyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(predLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(predatorSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(speedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addGap(5, 5, 5)
                .addComponent(stopButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton)
                .addContainerGap(91, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    }//GEN-LAST:event_formComponentShown

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        // TODO add your handling code here:
        if (!add){
            add = true;
            simulation = new PredatorSimulation(widthSlider.getValue(), heightSlider.getValue(), widthSlider.getValue() * heightSlider.getValue() * predatorSlider.getValue() / 100, widthSlider.getValue() * heightSlider.getValue() * preySlider.getValue() / 100);
            simulation.fillGrid();
            cellWidth = (int)(getWidth() / widthSlider.getValue() / 1.2);
            cellHeight = (int)(getHeight() / heightSlider.getValue() / 1.2);
            startButton.setVisible(true);
        }
    }//GEN-LAST:event_createButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        animTimer.start();
        stopButton.setVisible(true);
        resetButton.setVisible(false);
        speedLabel.setVisible(true);
        speedSlider.setVisible(true);
    }//GEN-LAST:event_startButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        add = false;
        resetButton.setVisible(false);
        startButton.setVisible(false);
        stopButton.setVisible(false);
        speedLabel.setVisible(false);
        speedSlider.setVisible(false);
    }//GEN-LAST:event_resetButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        animTimer.stop();
        resetButton.setVisible(true);
    }//GEN-LAST:event_stopButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JLabel heiLabel;
    private javax.swing.JSlider heightSlider;
    private javax.swing.JLabel predLabel;
    private javax.swing.JSlider predatorSlider;
    private javax.swing.JLabel preyLabel;
    private javax.swing.JSlider preySlider;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel widLabel;
    private javax.swing.JSlider widthSlider;
    // End of variables declaration//GEN-END:variables

    /**
     * This event captures a click which is defined as pressing and releasing in
     * the same area
     *
     * @param me
     */
    public void mouseClicked(MouseEvent me) {
        System.out.println("Click: " + me.getX() + ":" + me.getY());
        //x = 5;
        //y = 5;
    }

    /**
     * When the mountain is pressed
     *
     * @param me
     */
    public void mousePressed(MouseEvent me) {
        System.out.println("Press: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse button is released
     *
     * @param me
     */
    public void mouseReleased(MouseEvent me) {
        System.out.println("Release: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse enters the area
     *
     * @param me
     */
    public void mouseEntered(MouseEvent me) {
        System.out.println("Enter: " + me.getX() + ":" + me.getY());
    }

    /**
     * When the mouse exits the panel
     *
     * @param me
     */
    public void mouseExited(MouseEvent me) {
        System.out.println("Exit: " + me.getX() + ":" + me.getY());
    }

    /**
     * Everything inside here happens when you click on a captured key.
     */
    private class Move extends AbstractAction {

        String key;

        public Move(String akey) {
            key = akey;
        }

        public void actionPerformed(ActionEvent ae) {
            // here you decide what you want to happen if a particular key is pressed
            System.out.println("llll" + key);
            switch(key){
                //case "d": x+=2; break;
                case "x": animTimer.stop(); switcher.switchToCard(EndPanel.CARD_NAME); break;
            }
            if (key.equals("d")) {
                //x = x + 2;
            }
            
        }

    }

    /**
     * Everything inside this actionPerformed will happen every time the
     * animation timer clicks.
     */
    private class AnimTimerTick implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            //the stuff we want to change every clock tick
            simulation.movePrey();
            simulation.movePred();
            simulation.preyRep();
            simulation.predRep();
            simulation.forestFire();
            //force redraw
            repaint();
        }
    }
}
