/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loic
 */
public class NewDB {
    public void AddNew( NewBean ne){
        try {
            Connection con = Singleton.getInstance().getConnection();
            Statement statement = con.createStatement(); 
            String query = "INSERT INTO unjeu.news (titre, contenu, date) VALUES ("
                    + "'"+ne.getTitre()+"', "
                    + "'"+ne.getContenu()+"', "
                    + "'"+ne.getDate()+"')"; 
            System.out.println(query);
            statement.executeUpdate(query);
        }catch(Exception e){
            System.out.println("fail add new");
        }
    }
    
    public NewBean getById( String id ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.news WHERE id="+id);
            ResultSet rs = ps.executeQuery();
            NewBean ne = null;
            if (rs.next()) {
                ne = new NewBean();
                ne.setContenu(rs.getString(3));
                ne.setId(rs.getInt(1));
                ne.setTitre(rs.getString(2));
                java.sql.Date mysqlDate = rs.getDate(4);
                ne.setDate(new java.util.Date(mysqlDate.getTime()));
                
            }
            return ne;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<NewBean> findAll(){
        ArrayList<NewBean> list = new ArrayList();
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id, titre, contenu, date FROM unjeu.news ORDER BY date");
            ResultSet rs = ps.executeQuery();
            NewBean ne = null;
            while (rs.next()) {
                ne = new NewBean();
                ne.setContenu(rs.getString(3));
                ne.setId(rs.getInt(1));
                ne.setTitre(rs.getString(2));
                java.sql.Date mysqlDate = rs.getDate(4);
                ne.setDate(new java.util.Date(mysqlDate.getTime()));
                list.add(ne);
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
