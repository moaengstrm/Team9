/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author isakj
 */
public class Validator {
    
    public Validator() {
    
    }
    
    public boolean validateNewPost(String title, String text, String category) {
        boolean valid = true;
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(null, "F�ltet \"Titel\" m�ste vara ifyllt");
            valid = false;
        } else if (text.length() < 50) {
            JOptionPane.showMessageDialog(null, "Inl�gget �r f�r kort");
            valid = false;
        } else if (text.length() > 2500) {
            JOptionPane.showMessageDialog(null, "Inl�gget f�r inte vara l�ngre �n 2500 tecken");
            valid = false;
        } else if (category.equals("-- Kategori --")) {
            JOptionPane.showMessageDialog(null, "V�nligen v�lj en kategori");
            valid = false;
        }
        return valid;
    }
    
        public static boolean tfIsNotEmpty(JTextField rutaAttKolla) {

        // S�tter resultat till sant
        boolean resultat = true;

        // If-sats som kontrollerar om rutan �r tom
        if (rutaAttKolla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "En eller flera textrutor �r tomma!");
            rutaAttKolla.requestFocus();
            resultat = false;
        }

        // Returnerar resultat
        return resultat;
    }
}
