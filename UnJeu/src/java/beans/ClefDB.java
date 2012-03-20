/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
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
}
