/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author isakj
 */
public class RegistrationControl {
    
    InfDB idb;
    
    public RegistrationControl() {
        idb = TestProjekt.getDB();
    }
    
    public void showRegistrations(JList list) {

        try {
            String query = "SELECT * FROM Granskning";
            ArrayList<HashMap<String, String>> requests = idb.fetchRows(query);
            System.out.println(requests);
            DefaultListModel requestsListModel = new DefaultListModel();
            if (requests != null) {
                for (HashMap<String, String> request : requests) {
                    String name = request.get("ANVANDARNAMN");
                    requestsListModel.addElement(name);
                }
                
            }
            list.setModel(requestsListModel);
        } catch (InfException ie) {
            System.out.println(ie.getMessage());
        }

    }
    
    public void showInformation(JList list, JLabel userName, JLabel name, JLabel email, JLabel phone) {
        if (list.getSelectedValue() != null) {
            HashMap<String, String> information;
            try {
                String namn = list.getSelectedValue().toString();
                String query = "SELECT * FROM Granskning WHERE Anvandarnamn = '" + namn + "';";
                information = idb.fetchRow(query);

                userName.setText(information.get("ANVANDARNAMN"));
                name.setText(information.get("NAMN"));
                email.setText(information.get("EPOST"));
                phone.setText(information.get("TELE"));
            } catch (InfException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }
    
    public void denyRegistration(String userName) {
        try {
            String query = "DELETE FROM Granskning WHERE Anvandarnamn = '" + userName + "';";
            idb.delete(query);
        } catch(InfException ie) {
            System.out.println(ie.getMessage());
        }
    }
    
    public void emptyFields(JLabel userName, JLabel name, JLabel email, JLabel phone) {
        userName.setText("");
        name.setText("");
        email.setText("");
        phone.setText("");
    }
    
    public void acceptRegistration(String userName){
    try{
        String query = "Select * from granskning where anvandarnamn = '" + userName + "'";
        HashMap<String, String> information = idb.fetchRow(query);
        String name = information.get("NAMN");
        String email = information.get("EPOST");
        String phone = information.get("TELE");
        String username = information.get("ANVANDARNAMN");
        String password = information.get("LOSEN");
        query = "Insert into anvandare values ('" + password + "', '" + "2" + "', '" + username + "', '" + email + "', '" + phone + "')";
        idb.insert(query);
        denyRegistration(username);
    
    } catch(InfException ie) {
    System.out.println(ie.getMessage());
    }
    }
    
}
