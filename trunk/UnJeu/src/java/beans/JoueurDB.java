/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loic
 */
public class JoueurDB {
    
    public void creerJoueur( JoueurBean joueur){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "INSERT INTO unjeu.joueur (pseudo, mail, pass, dateInscription, attaque, vitesse, pvMax, pvActuels, totalCombats, totalMonstres, dernierX, dernierY, idMap, idArme, idArmure, idApparance, newsletter, groupe) VALUES ("
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
                    + "'"+joueur.getNewsletter()+"', "
                    + "'"+joueur.getGroupe()+"')"; 
            System.out.println(query);
            statement.executeUpdate(query);
            joueur.setIdJoueur(getByPseudo(joueur.getPseudo()).getIdJoueur());
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
            System.out.println(ex.toString());
        }
    }
    
    public JoueurBean getById( String id ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.joueur WHERE idJoueur="+id);
            ResultSet rs = ps.executeQuery();
            JoueurBean joueur = null;
            if (rs.next()) {
                joueur = new JoueurBean(rs.getString(2),rs.getString(3),rs.getString(4));
                joueur.setIdJoueur(new Integer(rs.getString(1)));
                
                java.sql.Date mysqlDate = rs.getDate(5);
                joueur.setDateInscription(new java.util.Date(mysqlDate.getTime()));
                
                joueur.setAttaque(new Integer(rs.getString(6)));
                joueur.setVitesse(new Integer(rs.getString(7)));
                joueur.setPvMax(new Integer(rs.getString(8)));
                joueur.setPvActuels(new Integer(rs.getString(9)));
                joueur.setTotalCombats(new Integer(rs.getString(10)));
                joueur.setTotalMonstres(new Integer(rs.getString(11)));
                joueur.setDernierX(new Integer(rs.getString(12)));
                joueur.setDernierY(new Integer(rs.getString(13)));
                joueur.setIdMap(new Integer(rs.getString(14)));
                joueur.setIdArme(new Integer(rs.getString(15)));
                joueur.setIdArmure(new Integer(rs.getString(16)));
                joueur.setIdApparance(new Integer(rs.getString(17)));
                joueur.setNewsletter(new Integer(rs.getString(18)));
                joueur.setGroupe(new Integer(rs.getString(19)));
            }
            return joueur;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public JoueurBean getByPseudo( String pseudo ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.joueur WHERE pseudo='"+pseudo+"'");
            ResultSet rs = ps.executeQuery();
            JoueurBean joueur = null;
            if (rs.next()) {
                joueur = new JoueurBean(rs.getString(2),rs.getString(3),rs.getString(4));
                joueur.setIdJoueur(new Integer(rs.getString(1)));
                
                java.sql.Date mysqlDate = rs.getDate(5);
                joueur.setDateInscription(new java.util.Date(mysqlDate.getTime()));
                
                joueur.setAttaque(new Integer(rs.getString(6)));
                joueur.setVitesse(new Integer(rs.getString(7)));
                joueur.setPvMax(new Integer(rs.getString(8)));
                joueur.setPvActuels(new Integer(rs.getString(9)));
                joueur.setTotalCombats(new Integer(rs.getString(10)));
                joueur.setTotalMonstres(new Integer(rs.getString(11)));
                joueur.setDernierX(new Integer(rs.getString(12)));
                joueur.setDernierY(new Integer(rs.getString(13)));
                joueur.setIdMap(new Integer(rs.getString(14)));
                joueur.setIdArme(new Integer(rs.getString(15)));
                joueur.setIdArmure(new Integer(rs.getString(16)));
                joueur.setIdApparance(new Integer(rs.getString(17)));
                joueur.setNewsletter(new Integer(rs.getString(18)));
                joueur.setGroupe(new Integer(rs.getString(19)));
            }
            return joueur;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<JoueurBean> getList( String pseudo ){
        ArrayList<JoueurBean> list = new ArrayList();
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.joueur WHERE pseudo LIKE '%"+pseudo+"%'");
            ResultSet rs = ps.executeQuery();
            JoueurBean joueur;
            while (rs.next()) {
                joueur = new JoueurBean(rs.getString(2),rs.getString(3),rs.getString(4));
                joueur.setIdJoueur(new Integer(rs.getString(1)));
                
                java.sql.Date mysqlDate = rs.getDate(5);
                joueur.setDateInscription(new java.util.Date(mysqlDate.getTime()));
                
                joueur.setAttaque(new Integer(rs.getString(6)));
                joueur.setVitesse(new Integer(rs.getString(7)));
                joueur.setPvMax(new Integer(rs.getString(8)));
                joueur.setPvActuels(new Integer(rs.getString(9)));
                joueur.setTotalCombats(new Integer(rs.getString(10)));
                joueur.setTotalMonstres(new Integer(rs.getString(11)));
                joueur.setDernierX(new Integer(rs.getString(12)));
                joueur.setDernierY(new Integer(rs.getString(13)));
                joueur.setIdMap(new Integer(rs.getString(14)));
                joueur.setIdArme(new Integer(rs.getString(15)));
                joueur.setIdArmure(new Integer(rs.getString(16)));
                joueur.setIdApparance(new Integer(rs.getString(17)));
                joueur.setNewsletter(new Integer(rs.getString(18)));
                joueur.setGroupe(new Integer(rs.getString(19)));
                list.add(joueur);
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public JoueurBean checkJoueur(String pseudo, String password){
        Connection con;
        try {
            con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT idJoueur, pseudo, mail, pass, dateInscription, attaque, vitesse, pvMax, "
                    +"pvActuels, totalCombats, totalMonstres, dernierX, dernierY, "
                    +"idMap, idArme, idArmure, idApparance, newsletter, groupe "
                    +"FROM unjeu.joueur WHERE pseudo='"+pseudo+"' AND pass='"+password+"'");
            ResultSet rs = ps.executeQuery();
            JoueurBean joueur = null;
            if (rs.next()) {
                joueur = new JoueurBean(rs.getString(2),rs.getString(3),rs.getString(4));
                joueur.setIdJoueur(new Integer(rs.getString(1)));
                
                java.sql.Date mysqlDate = rs.getDate(5);
                joueur.setDateInscription(new java.util.Date(mysqlDate.getTime()));

                joueur.setAttaque(new Integer(rs.getString(6)));
                joueur.setVitesse(new Integer(rs.getString(7)));
                joueur.setPvMax(new Integer(rs.getString(8)));
                joueur.setPvActuels(new Integer(rs.getString(9)));
                joueur.setTotalCombats(new Integer(rs.getString(10)));
                joueur.setTotalMonstres(new Integer(rs.getString(11)));
                joueur.setDernierX(new Integer(rs.getString(12)));
                joueur.setDernierY(new Integer(rs.getString(13)));
                joueur.setIdMap(new Integer(rs.getString(14)));
                joueur.setIdArme(new Integer(rs.getString(15)));
                joueur.setIdArmure(new Integer(rs.getString(16)));
                joueur.setIdApparance(new Integer(rs.getString(17)));
                joueur.setNewsletter(new Integer(rs.getString(18)));
                joueur.setGroupe(new Integer(rs.getString(19)));
            }
            return joueur;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public boolean existe(String pseudo){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT idJoueur FROM unjeu.joueur WHERE pseudo='"+pseudo+"'");
            ResultSet rs = ps.executeQuery();
            System.out.println("pseudo : "+pseudo);
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
     }
}
