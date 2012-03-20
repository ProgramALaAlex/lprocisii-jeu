/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loic
 */
public class InventaireDB {
   
    public String JsonInventaire( int id){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.inventaire WHERE idJoueur="+id);
            ResultSet rs = ps.executeQuery();
            InventaireBean inventaire = new InventaireBean();
            if (rs.next()) {
                inventaire.setIdJoueur(rs.getInt(1));
                inventaire.setSlot1_idObjet(rs.getInt(2));
                inventaire.setSlot1_qte(rs.getInt(3));
                inventaire.setSlot1_idObjet(rs.getInt(4));
                inventaire.setSlot1_qte(rs.getInt(5));
                inventaire.setSlot1_idObjet(rs.getInt(6));
                inventaire.setSlot1_qte(rs.getInt(7));
                inventaire.setSlot1_idObjet(rs.getInt(8));
                inventaire.setSlot1_qte(rs.getInt(9));
                inventaire.setSlot1_idObjet(rs.getInt(10));
                inventaire.setSlot1_qte(rs.getInt(11));
                inventaire.setSlot1_idObjet(rs.getInt(12));
                inventaire.setSlot1_qte(rs.getInt(13));
                inventaire.setSlot1_idObjet(rs.getInt(14));
                inventaire.setSlot1_qte(rs.getInt(15));
                inventaire.setSlot1_idObjet(rs.getInt(16));
                inventaire.setSlot1_qte(rs.getInt(17));
                inventaire.setSlot1_idObjet(rs.getInt(18));
                inventaire.setSlot1_qte(rs.getInt(19));
                inventaire.setSlot1_idObjet(rs.getInt(20));
                inventaire.setSlot1_qte(rs.getInt(21));
                inventaire.setSlot1_idObjet(rs.getInt(22));
                inventaire.setSlot1_qte(rs.getInt(23));
                inventaire.setSlot1_idObjet(rs.getInt(24));
                inventaire.setSlot1_qte(rs.getInt(25));
                inventaire.setSlot1_idObjet(rs.getInt(26));
                inventaire.setSlot1_qte(rs.getInt(27));
                inventaire.setSlot1_idObjet(rs.getInt(28));
                inventaire.setSlot1_qte(rs.getInt(29));
                inventaire.setSlot1_idObjet(rs.getInt(30));
                inventaire.setSlot1_qte(rs.getInt(31));
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return null;
    }
}
