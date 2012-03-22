/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.serveur.beans;

import java.io.Serializable;

/**
 *
 * @author Loic
 */
public class GuildeBean implements Serializable{
	private static final long serialVersionUID = -5059545182339164143L;
	int idGuilde;
    String nom;
    int totalMonstres;

    public int getIdGuilde() {
        return idGuilde;
    }

    public String getNom() {
        return nom;
    }

    public int getTotalMonstres() {
        return totalMonstres;
    }

    public void setIdGuilde(int idGuilde) {
        this.idGuilde = idGuilde;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTotalMonstres(int totalMonstres) {
        this.totalMonstres = totalMonstres;
    }
    
    
}
