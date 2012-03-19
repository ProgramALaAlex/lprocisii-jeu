/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.sql.*;

/**
 *
 * @author Loic
 */
public class JoueurDB {
    
    public void creerJoueur( JoueurBean joueur){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306";
            String user = "root";
            String password = null;
            Connection con = DriverManager.getConnection( url, user, password ) ;
            Statement statement = con.createStatement(); 
            String query = "INSERT INTO joueur (pseudo, mail, pass, dateInscription, attaque, vitesse, pvMax, pvActuels, totalCombats, totalMonstres, dernierX, dernierY, idMap, idArme, idArmure, idApparance, newsletter) VALUES ("
                    + "'"+joueur.getPseudo()+"', "
                    + "'"+joueur.getMail()+"', "
                    + "'"+joueur.getPass()+"', "
                    + "'"+joueur.getDateInscription()+"', "
                    + "'"+joueur.getAttaque()+"', "
                    + "'"+joueur.getVitesse()+"', "
                    + "'"+joueur.getPvMax()+"', "
                    + "'"+joueur.getPvActuels()+"', "
                    + "'"+joueur.getTotalCombats()+"', "
                    + "'"+joueur.getTotalMonstres()+"', "
                    + "'"+joueur.getDernierX()+"', "
                    + "'"+joueur.getDernierY()+"', "
                    + "'"+joueur.getIdMap()+"', "
                    + "'"+joueur.getIdArme()+"', "
                    + "'"+joueur.getIdArmure()+"', "
                    + "'"+joueur.getIdApparance()+"', "
                    + "'"+joueur.getNewsletter()+"')"; 
            statement.executeUpdate(query);
            con.close();
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
        }
    }
}
