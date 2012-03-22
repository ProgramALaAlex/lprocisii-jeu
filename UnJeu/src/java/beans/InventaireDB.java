/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loic
 */
public class InventaireDB {
   
    public InventaireBean jsonInventaire( String id){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.inventaire WHERE idJoueur="+id);
            ResultSet rs = ps.executeQuery();
            InventaireBean inventaire = new InventaireBean();
            if (rs.next()) {
                inventaire.setIdJoueur(rs.getInt(1));
                inventaire.setSlot1_idObjet(rs.getInt(2));
                inventaire.setSlot1_qte(rs.getInt(3));
                inventaire.setSlot2_idObjet(rs.getInt(4));
                inventaire.setSlot2_qte(rs.getInt(5));
                inventaire.setSlot3_idObjet(rs.getInt(6));
                inventaire.setSlot3_qte(rs.getInt(7));
                inventaire.setSlot4_idObjet(rs.getInt(8));
                inventaire.setSlot4_qte(rs.getInt(9));
                inventaire.setSlot5_idObjet(rs.getInt(10));
                inventaire.setSlot5_qte(rs.getInt(11));
                inventaire.setSlot6_idObjet(rs.getInt(12));
                inventaire.setSlot6_qte(rs.getInt(13));
                inventaire.setSlot7_idObjet(rs.getInt(14));
                inventaire.setSlot7_qte(rs.getInt(15));
                inventaire.setSlot8_idObjet(rs.getInt(16));
                inventaire.setSlot8_qte(rs.getInt(17));
                inventaire.setSlot9_idObjet(rs.getInt(18));
                inventaire.setSlot9_qte(rs.getInt(19));
                inventaire.setSlot10_idObjet(rs.getInt(20));
                inventaire.setSlot10_qte(rs.getInt(21));
                inventaire.setSlot11_idObjet(rs.getInt(22));
                inventaire.setSlot11_qte(rs.getInt(23));
                inventaire.setSlot12_idObjet(rs.getInt(24));
                inventaire.setSlot12_qte(rs.getInt(25));
                inventaire.setSlot13_idObjet(rs.getInt(26));
                inventaire.setSlot13_qte(rs.getInt(27));
                inventaire.setSlot14_idObjet(rs.getInt(28));
                inventaire.setSlot14_qte(rs.getInt(29));
                inventaire.setSlot15_idObjet(rs.getInt(30));
                inventaire.setSlot15_qte(rs.getInt(31));
                inventaire.setSlot16_idObjet(rs.getInt(32));
                inventaire.setSlot16_qte(rs.getInt(33));
            }
            return inventaire;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return null;
    }
    
    public void createInvenaire( InventaireBean inv ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "INSERT INTO unjeu.inventaire (idJoueur, slot1_idObjet, slot1_qte, slot2_idObjet, slot2_qte, slot3_idObjet, slot3_qte, slot4_idObjet, slot4_qte, slot5_idObjet, slot5_qte, slot6_idObjet, slot6_qte, slot7_idObjet, slot7_qte, slot8_idObjet, slot8_qte, slot9_idObjet, slot9_qte, slot10_idObjet, slot10_qte, slot11_idObjet, slot11_qte, slot12_idObjet, slot12_qte, slot13_idObjet, slot13_qte, slot14_idObjet, slot14_qte, slot15_idObjet, slot15_qte, slot16_idObjet, slot16_qte) VALUES ("
                     + "'"+ inv.getIdJoueur()+"', "
                    
                    + "'"+ inv.getSlot1_idObjet()+"', "
                    + "'"+ inv.getSlot1_qte()+"', "
                    
                    + "'"+ inv.getSlot2_idObjet()+"', "
                    + "'"+ inv.getSlot2_qte()+"', "
                    
                    + "'"+ inv.getSlot3_idObjet()+"', "
                    + "'"+ inv.getSlot3_qte()+"', "
                    
                    + "'"+ inv.getSlot4_idObjet()+"', "
                    + "'"+ inv.getSlot4_qte()+"', "
                    
                    + "'"+ inv.getSlot5_idObjet()+"', "
                    + "'"+ inv.getSlot5_qte()+"', "
                    
                    + "'"+ inv.getSlot6_idObjet()+"', "
                    + "'"+ inv.getSlot6_qte()+"', "
                    
                    + "'"+ inv.getSlot7_idObjet()+"', "
                    + "'"+ inv.getSlot7_qte()+"', "
                    
                    + "'"+ inv.getSlot8_idObjet()+"', "
                    + "'"+ inv.getSlot8_qte()+"', "
                    
                    + "'"+ inv.getSlot9_idObjet()+"', "
                    + "'"+ inv.getSlot9_qte()+"', "
                    
                    + "'"+ inv.getSlot10_idObjet()+"', "
                    + "'"+ inv.getSlot10_qte()+"', "
                    
                    + "'"+ inv.getSlot11_idObjet()+"', "
                    + "'"+ inv.getSlot11_qte()+"', "
                    
                    + "'"+ inv.getSlot12_idObjet()+"', "
                    + "'"+ inv.getSlot12_qte()+"', "
                    
                    + "'"+ inv.getSlot13_idObjet()+"', "
                    + "'"+ inv.getSlot13_qte()+"', "
                    
                    + "'"+ inv.getSlot14_idObjet()+"', "
                    + "'"+ inv.getSlot14_qte()+"', "
                    
                    + "'"+ inv.getSlot15_idObjet()+"', "
                    + "'"+ inv.getSlot15_qte()+"', "
                    
                    + "'"+ inv.getSlot16_idObjet()+"', "
                    + "'"+inv.getSlot16_qte()+"')"; 
            System.out.println(query);
            statement.executeUpdate(query);
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
            System.out.println(ex.toString());
        }
    }
    
    public void majInvenaire( InventaireBean inv ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "UPDATE unjeu.inventaire SET "
                    
                    + "slot1_idObjet = '"+ inv.getSlot1_idObjet()+"', "
                    + "slot1_qte = '"+ inv.getSlot1_qte()+"', "
                    
                    + "slot2_idObjet = '"+ inv.getSlot2_idObjet()+"', "
                    + "slot2_qte = '"+ inv.getSlot2_qte()+"', "
                    
                    + "slot3_idObjet = '"+ inv.getSlot3_idObjet()+"', "
                    + "slot3_qte = '"+ inv.getSlot3_qte()+"', "
                    
                    + "slot4_idObjet = '"+ inv.getSlot4_idObjet()+"', "
                    + "slot4_qte = '"+ inv.getSlot4_qte()+"', "
                    
                    + "slot5_idObjet = '"+ inv.getSlot5_idObjet()+"', "
                    + "slot5_qte = '"+ inv.getSlot5_qte()+"', "
                    
                    + "slot6_idObjet = '"+ inv.getSlot6_idObjet()+"', "
                    + "slot6_qte = '"+ inv.getSlot6_qte()+"', "
                    
                    + "slot7_idObjet = '"+ inv.getSlot7_idObjet()+"', "
                    + "slot7_qte = '"+ inv.getSlot7_qte()+"', "
                    
                    + "slot8_idObjet = '"+ inv.getSlot8_idObjet()+"', "
                    + "slot8_qte = '"+ inv.getSlot8_qte()+"', "
                    
                    + "slot9_idObjet = '"+ inv.getSlot9_idObjet()+"', "
                    + "slot9_qte = '"+ inv.getSlot9_qte()+"', "
                    
                    + "slot10_idObjet = '"+ inv.getSlot10_idObjet()+"', "
                    + "slot10_qte = '"+ inv.getSlot10_qte()+"', "
                    
                    + "slot11_idObjet = '"+ inv.getSlot11_idObjet()+"', "
                    + "slot11_qte = '"+ inv.getSlot11_qte()+"', "
                    
                    + "slot12_idObjet = '"+ inv.getSlot12_idObjet()+"', "
                    + "slot12_qte = '"+ inv.getSlot12_qte()+"', "
                    
                    + "slot13_idObjet = '"+ inv.getSlot13_idObjet()+"', "
                    + "slot13_qte = '"+ inv.getSlot13_qte()+"', "
                    
                    + "slot14_idObjet = '"+ inv.getSlot14_idObjet()+"', "
                    + "slot14_qte = '"+ inv.getSlot14_qte()+"', "
                    
                    + "slot15_idObjet = '"+ inv.getSlot15_idObjet()+"', "
                    + "slot15_qte = '"+ inv.getSlot15_qte()+"', "
                    
                    + "slot16_idObjet = '"+ inv.getSlot16_idObjet()+"', "
                    + "slot16_qte = '"+inv.getSlot16_qte()+"' "
            
                    + "WHERE idJoueur = '"+ inv.getIdJoueur()+"'";
            System.out.println(query);
            statement.executeUpdate(query);
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
            System.out.println(ex.toString());
        }
    }
}
