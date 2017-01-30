/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irit.upnp;

import javax.swing.JButton;
import org.fourthline.cling.model.meta.LocalService;

/**
 *
 * @author mathieukostiuk
 */
public class ButtonFrame extends javax.swing.JFrame {

    
    private enum State {
        INIT;
    }
    
    public void activate(JButton... bn) {
        for (JButton b:bn)
            b.setEnabled(true);
    }
    
    public void deactivate(JButton... bn) {
        for (JButton b:bn)
            b.setEnabled(false);
    }
    
    private State currentState;
    private LocalService<Button> switchPowerService;
    
    private void init(LocalService<Button> buttonService) {
        this.currentState = State.INIT;
        activate(button);
        this.switchPowerService =buttonService; 
    }
    
    /**
     * Creates new form ButtonFrame
     */
    public ButtonFrame(LocalService<Button> switchPowerService) {
        initComponents();
        init(switchPowerService);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button.setText("Button");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(button)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(button)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        // TODO add your handling code here:
        
        switch(this.currentState) {
            case INIT:
                this.switchPowerService.getManager().getImplementation().setTarget(true);
                break;
        }
    }//GEN-LAST:event_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    // End of variables declaration//GEN-END:variables
}