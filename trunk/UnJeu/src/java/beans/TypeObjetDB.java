/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loic
 */
public class TypeObjetDB {
    public ArrayList<TypeObjetBean> getListType(){
        ArrayList<TypeObjetBean> list = new ArrayList();
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.typeobjet");
            ResultSet rs = ps.executeQuery();
            TypeObjetBean ne = null;
            while (rs.next()) {
                ne = new TypeObjetBean();
                ne.setIdType(rs.getInt(1));
                ne.setNom(rs.getString(2));
                list.add(ne);
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public TypeObjetBean getById( int id ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.typeobjet WHERE idType="+id);
            ResultSet rs = ps.executeQuery();
            TypeObjetBean ne = null;
            if (rs.next()) {
                ne = new TypeObjetBean();
                ne.setIdType(rs.getInt(1));
                ne.setNom(rs.getString(2));
            }
            return ne;
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
