/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author isakj
 */
public class TestProjekt {   
    
        private static InfDB idb;
        
        public static InfDB getDB() {
            return idb;
            
        }
    
        public static void main(String[] args) {
            try {
                String aktuellMapp = System.getProperty("user.dir");
                String os = System.getProperty("os.name").toLowerCase();
                String sokvagDatabas;
                if (os.contains("mac")) {
                    sokvagDatabas = aktuellMapp + ("/db/BloggData.FDB");
                } else {
                    sokvagDatabas = aktuellMapp + ("\\db\\BloggData.FDB");
                }
                System.out.println(sokvagDatabas);
                idb = new InfDB(sokvagDatabas);
            } catch (InfException ettUndantag) {
                JOptionPane.showMessageDialog(null, "Databasfel!");
            }

            new MainWindow(idb).setVisible(true);
        }
    
}
    

