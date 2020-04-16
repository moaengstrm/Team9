/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Mollyyyyy
 */
public class InloggSida extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
        private InfDB idb;
    /**
     * 
     * Creates new form InlogSida
     */
    public InloggSida(InfDB idb) {
       this.idb=idb;
       initComponents();
       testmetod();
        
        
    }
    
        public void testmetod () {
        
        try { 
        String utskrift = idb.fetchSingle("SELECT ANVANDAR_NAMN FROM ANVANDARE WHERE ANVANDAR_ID = 1");
        //System.out.println(utskrift);
        jLabel2.setText(utskrift);
            
        } catch (InfException ex) {
            
           
            Logger.getLogger(InloggSida.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblanvandarnamn = new javax.swing.JLabel();
        lbllosen = new javax.swing.JLabel();
        txtanvandarnamn = new javax.swing.JTextField();
        txtlosenord = new javax.swing.JTextField();
        btnloggain = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("namn");

        jLabel3.setFont(new java.awt.Font("Verdana Pro Semibold", 1, 24)); // NOI18N
        jLabel3.setText("V�lkommen!");

        lblanvandarnamn.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblanvandarnamn.setText("Anv�ndarnamn:");

        lbllosen.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lbllosen.setText("L�senord:");

        txtanvandarnamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtanvandarnamnActionPerformed(evt);
            }
        });

        txtlosenord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlosenordActionPerformed(evt);
            }
        });

        btnloggain.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnloggain.setText("Logga in");
        btnloggain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloggainActionPerformed(evt);
            }
        });

        jButton1.setText("Registrera");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(53, 53, 53)
                                .addComponent(btnloggain, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblanvandarnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbllosen, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtanvandarnamn)
                                    .addComponent(txtlosenord, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblanvandarnamn)
                            .addComponent(txtanvandarnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(lbllosen))
                    .addComponent(txtlosenord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnloggain)
                    .addComponent(jButton1))
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtanvandarnamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtanvandarnamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtanvandarnamnActionPerformed

    private void txtlosenordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlosenordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlosenordActionPerformed

    private void btnloggainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloggainActionPerformed

         String anvandaren = this.txtanvandarnamn.getText();
         String losen = this.txtlosenord.getText();
        if ( 1<2) 
        {
         //Validering.KollaTextFalt2st(this.alienTf, (JTextField)this.alienLosenTf) && Validering.KollaTextFaltOchSifferFalt(this.alienTf))
            
        try
        {
             String an = this.idb.fetchSingle("select ANVANDAR_ID from ANVANDARE where ANVANDAR_NAMN = '" + anvandaren + "'" );
             String lo = this.idb.fetchSingle("select LOSEN from ANVANDARE where ANVANDAR_ID =" + an);
             String he = this.idb.fetchSingle("Select ANVANDAR_NAMN from anvandare where ANVANDAR_ID =" + an); 
             String ads = this.idb.fetchSingle("select ADMINJANEJ from ANVANDARE where ANVANDAR_ID=" + an);
            
             if (anvandaren.equals(he) && losen.equals(lo)&& ads.equals("N")) {
                    this.setVisible(false);
      
                    new MainWindow(idb).setVisible(true);
                    //ValkommenAlien enAliensida = new ValkommenAlien(anvandaren,idb);
                    //enAliensida.setVisible(true);
                   
                }
           
             else if (anvandaren.equals(he) && losen.equals(lo)&& ads.equals("J"))  {
                     this.setVisible(false); 
                    JOptionPane.showMessageDialog(null, "admin inloggad");

                 
                 
               
                }
             
             else {
                 
                 JOptionPane.showMessageDialog(null, "Kunde inte hitta anvandaren");
             }
        }
        catch(InfException e)
        {
        JOptionPane.showMessageDialog(null, "Ett fel uppstod.");
                System.out.println("Internt felmeddelande:" + e.getMessage());
        }
        
        }


        // TODO add your handling code here:
    }//GEN-LAST:event_btnloggainActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       new Registration().setVisible(true);   
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnloggain;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblanvandarnamn;
    private javax.swing.JLabel lbllosen;
    private javax.swing.JTextField txtanvandarnamn;
    private javax.swing.JTextField txtlosenord;
    // End of variables declaration//GEN-END:variables
}
