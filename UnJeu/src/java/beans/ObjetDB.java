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
public class ObjetDB {
    public ObjetBean getById( int id ){
        try {
            Connection con = Singleton.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM unjeu.objet WHERE idObjet="+id);
            ResultSet rs = ps.executeQuery();
            ObjetBean ne = null;
            if (rs.next()) {
                ne = new ObjetBean();
                ne.setIdObjet(rs.getInt(1));
                ne.setNom(rs.getString(2));
                ne.setValeur(rs.getInt(3));
                ne.setIdTypeObjet(rs.getInt(4));
                ne.setUrl(rs.getString(5));
                return ne;
            }
        } catch (Exception ex) {
            Logger.getLogger(JoueurDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
