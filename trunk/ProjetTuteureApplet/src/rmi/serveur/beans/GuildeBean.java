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
    int idGuilde;
    String nom;
    int totalMonstres;

    public int getIdGuilde() {
        return idGuilde;
    }

    public void setIdGuilde(int idGuilde) {
        this.idGuilde = idGuilde;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTotalMonstres() {
        return totalMonstres;
    }

    public void setTotalMonstres(int totalMonstres) {
        this.totalMonstres = totalMonstres;
    }
    
    
}
