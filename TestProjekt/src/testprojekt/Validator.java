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
            JOptionPane.showMessageDialog(null, "Fältet \"Titel\" måste vara ifyllt");
            valid = false;
        } else if (text.length() < 50) {
            JOptionPane.showMessageDialog(null, "Inlägget är för kort");
            valid = false;
        } else if (text.length() > 2500) {
            JOptionPane.showMessageDialog(null, "Inlägget får inte vara längre än 2500 tecken");
            valid = false;
        } else if (category.equals("-- Kategori --")) {
            JOptionPane.showMessageDialog(null, "Vänligen välj en kategori");
            valid = false;
        }
        return valid;
    }
    
    public static boolean tfIsNotEmpty(JTextField... textFields) {

        // Sätter resultat till sant
        boolean result = true;

        for (JTextField textField : textFields) {
        // If-sats som kontrollerar om rutan är tom
            if (textField.getText().isEmpty()) {
                result = false;
                break;
            }
        }
        if (!result) {
            JOptionPane.showMessageDialog(null, "En eller flera textrutor är tomma!");
        }

        // Returnerar resultat
        return result;
    }
    
     public static boolean passwordIsNotEmpty(JPasswordField... passwordFields) {

        // Sätter resultat till sant
        boolean result = true;

        for (JPasswordField passwordField : passwordFields) {
            String password = new String(passwordField.getPassword());
        // If-sats som kontrollerar om rutan är tom
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fältet 'Lösenord' är tomt");
                passwordField.requestFocus();
                result = false;
                break;
            }
        }
        // Returnerar resultat
        return result;
    }
     
    public boolean validateRegistration(String userName, String email) {
        boolean valid = false;
        try {
            valid = true;
            String query = "SELECT Anvandare.ANVANDAR_NAMN as anvardareNamn, Granskning.ANVANDARNAMN as granskningNamn FROM ANVANDARE, Granskning";
            ArrayList<HashMap<String, String>> results = idb.fetchRows(query);
            if (results != null) {
                for (HashMap<String, String> result : results) {
                    String name = result.get("ANVANDARENAMN");
                    String name2 = result.get("GRANSKNINGNAMN");
                    if (userName.equalsIgnoreCase(name) || userName.equalsIgnoreCase(name2)) {
                        valid = false;
                        JOptionPane.showMessageDialog(null, "Användarnamnet är upptaget");
                        break;
                    } 
                }
            }
        } catch(InfException ie) {
            System.out.println(ie.getMessage());
        }
        return valid;
    }
}
