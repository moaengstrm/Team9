package BloggProjekt;


import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mollyy_
 */
public class BloggStart {
    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    
    private static InfDB idb;
    
    
    public static void main(final String[] args) {
        try {
            String aktuellMapp = System.getProperty("user.dir");
            String sokVag = aktuellMapp + "\\BloggData.FDB";
            idb = new InfDB(sokVag);
        }
        catch (InfException e) {
            JOptionPane.showMessageDialog(null, "N�got gick fel!");
            System.out.println("Internt felmeddelande:" + e.getMessage());
        }
        
        //new InloggSida(idb).setVisible(true); 
    }   

}
    
    
    

