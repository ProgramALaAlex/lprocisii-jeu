/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.serveur.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Loic
 */
public class NewBean implements Serializable{
	private static final long serialVersionUID = -6409271487417041030L;

	public static java.sql.Date getCurrentJavaSqlDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    int id;
    String titre, contenu;	

    Date date;
    
    public NewBean (){
        
    }
    public NewBean (String titre, String contenu){
        this.titre = titre;
        this.contenu = contenu;
        this.date = getCurrentJavaSqlDate();
    }
    
    public String getContenu() {
        return contenu;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
}
