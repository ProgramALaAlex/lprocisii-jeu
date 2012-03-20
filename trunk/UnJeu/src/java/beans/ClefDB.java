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
        public void createInvenaire( String id ){
            try {
                Connection con = Singleton.getInstance().getConnection();
                Statement statement = con.createStatement(); 
                String query = "INSERT INTO unjeu.clef (clef, idJoueur) Values('"+UUID.randomUUID().toString()+"', '"+id+"')";
                System.out.println(query);
                statement.executeUpdate(query);
            }catch(Exception e){
                System.out.println("Fail clef db");
            }
        }
}
