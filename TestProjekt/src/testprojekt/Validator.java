/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author isakj
 */
public class Validator {
    
    private InfDB idb;
    
    public Validator() {
        idb = TestProjekt.getDB();
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
    
    public static boolean tfIsNotEmpty(JTextField... textFields) {

        // S�tter resultat till sant
        boolean result = true;

        for (JTextField textField : textFields) {
        // If-sats som kontrollerar om rutan �r tom
            if (textField.getText().isEmpty()) {
                result = false;
            }
        }
        if (!result) {
            JOptionPane.showMessageDialog(null, "En eller flera textrutor �r tomma!");
        }

        // Returnerar resultat
        return result;
    }
    
     public static boolean passwordIsNotEmpty(JPasswordField... passwordFields) {

        // S�tter resultat till sant
        boolean result = true;

        for (JPasswordField passwordField : passwordFields) {
            String password = new String(passwordField.getPassword());
        // If-sats som kontrollerar om rutan �r tom
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "F�ltet 'L�senord' �r tomt");
                passwordField.requestFocus();
                result = false;
            }
        }
        // Returnerar resultat
        return result;
    }
     
    public boolean validateRegistration(String userName, String email) {
        boolean valid = false;
        try {
            valid = true;
            String query = "SELECT * FROM ANVANDARE, Granskning";
            ArrayList<HashMap<String, String>> results = idb.fetchRows(query);
            if (results != null) {
                for (HashMap<String, String> result : results) {
                    String name = result.get("ANVANDARNAMN");
                    System.out.println(name);
                    String emailAdress = result.get("EPOST");
                    if (userName.equals(name)) {
                        valid = false;
                        JOptionPane.showMessageDialog(null, "Anv�ndarnamnet �r upptaget");
                        break;
                    } 
                    if (email.equalsIgnoreCase(emailAdress)) {
                        valid = false;
                        JOptionPane.showMessageDialog(null, "E-postadressen anv�nds redan");
                        break;
                    }
                }
            }
        } catch(InfException ie) {
            
        }
        return valid;
    }
}
