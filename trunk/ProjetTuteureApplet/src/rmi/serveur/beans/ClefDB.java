/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.serveur.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 *
 * @author Loic
 */
public class ClefDB {
        public String createInvenaire( String id ){
            try {
                Connection con = Singleton.getInstance().getConnection();
                Statement statement = con.createStatement(); 
                String clef = UUID.randomUUID().toString();
                String query = "INSERT INTO unjeu.clef (clef, idJoueur) Values('"+clef+"', '"+id+"')";
                System.out.println(query);
                statement.executeUpdate(query);
                return clef;
            }catch(Exception e){
                System.out.println("Fail clef db");
            }
            return null;
        }
        
        /**
         * Recupère un ID et supprimer la clef
         * @param clef
         * @return
         */
        public int getId(String clef){
            try {
                Connection con = Singleton.getInstance().getConnection();
                String query = "SELECT idJoueur FROM unjeu.clef WHERE clef='"+clef+"'";
                System.out.println(query);
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                int id = 0;
                if (rs.next()) {
                    id =rs.getInt(1);
                    System.out.println("TROUVE");
                }
                query = "DELETE FROM unjeu.clef WHERE clef='"+clef+"'";
                System.out.println(query);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
                return id;
                
            }catch(Exception e){
                System.out.println("Fail clef db : ");
            }
            return 0;
        }
}
