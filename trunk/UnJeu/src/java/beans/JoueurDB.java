/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.sql.*;
import java.text.SimpleDateFormat;
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
            PreparedStatement ps = con.prepareStatement( 
            "INSERT INTO unjeu.joueur (pseudo, mail, pass, dateInscription, "
                    + "attaque, vitesse, pvMax, pvActuels, totalCombats, "
                    + "totalMonstres, dernierX, dernierY, idMap, idArme, "
                    + "idArmure, idApparance, newsletter, groupe, inventaire) VALUES ("
                    + "?,?,?,"
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
                    + "'"+joueur.getGroupe()+"', "
                    + "'')");
            ps.setString(1, joueur.getPseudo());
            ps.setString(2, joueur.getMail());
            ps.setString(3, joueur.getPass());
            ps.executeUpdate();
            joueur.setIdJoueur(getByPseudo(joueur.getPseudo()).getIdJoueur());
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
            System.out.println(ex.toString());
        }
    }
    
    public void majJoueur(JoueurBean joueur){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "UPDATE unjeu.joueur SET "
                    +"pseudo='"+joueur.getPseudo()
                    + "', mail='"+joueur.getMail()
                    + "', pass='"+joueur.getPass()
                    + "', attaque='"+joueur.getAttaque()
                    + "', vitesse='"+joueur.getVitesse()
                    + "', pvMax='"+joueur.getPvMax()
                    + "', pvActuels='"+joueur.getPvActuels()
                    + "', totalCombats='"+joueur.getTotalCombats()
                    + "', totalMonstres='"+joueur.getTotalMonstres()
                    + "', dernierX='"+joueur.getDernierX()
                    + "', dernierY='"+joueur.getDernierY()
                    + "', idMap='"+joueur.getIdMap()
                    + "', idArme='"+joueur.getIdArme()
                    + "', idArmure='"+joueur.getIdArmure()
                    + "', idApparance='"+joueur.getIdApparance()
                    + "', newsletter='"+joueur.getNewsletter()
                    + "', groupe='"+joueur.getGroupe()
                    + "' WHERE idJoueur='"+joueur.getIdJoueur()+"'";
            System.out.println(query);
            statement.executeUpdate(query);
        }
        catch(Exception ex) 
        { 
            System.out.println("Fail du chargement du driver J mysql");
            System.out.println(ex.toString());
        }
    }
    
    public void deleteJoueur(String id){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "DELETE FROM unjeu.joueur WHERE idJoueur='"+id+"'";
            System.out.println(query);
            statement.executeUpdate(query);
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.joueur WHERE idJoueur='"+id+"'");
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
            
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
     }
     
     public void bannirJoueur(String id, String raison, String date){
        Connection con;
        try {
            con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT idJoueur, raison, dateFin FROM unjeu.blacklist WHERE idJoueur='"+id+"'");
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                ps = con.prepareStatement("UPDATE unjeu.blacklist SET raison=?, dateFin="+(!date.equals("") ? date : "null"));
                ps.setString(1, raison);
                ps.executeUpdate();
                
                
            } else {
                ps = con.prepareStatement("INSERT INTO unjeu.blacklist(idJoueur, raison, dateFin) VALUES('"+id+"',?"+(!date.equals("") ? ","+date+")" : ",null)"));
                ps.setString(1, raison);
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void debannirJoueur(String id){
        Connection con;
        try {
            con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM unjeu.blacklist WHERE idJoueur='"+id+"'");
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public String[] estBanni(int id){
        String[] ban = null;
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT idJoueur, raison, dateFin FROM unjeu.blacklist WHERE idJoueur='"+id+"'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(3) != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date dateBan = sdf.parse(rs.getString(3));
                    if (dateBan.before(new java.util.Date())) {
                        this.debannirJoueur(""+id);
                        return null;
                    }
                }
                
                ban = new String[2];
                ban[0] = rs.getString(2);
                ban[1] = rs.getString(3) != null ? rs.getString(3) : "définitif";
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ban;
     }
}
