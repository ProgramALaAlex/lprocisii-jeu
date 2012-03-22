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
public class ObjetBean implements Serializable{
	private static final long serialVersionUID = -6245078275352435765L;
	int idObjet;
    String nom;
    int valeur;
    int idTypeObjet;

    public int getIdObjet() {
        return idObjet;
    }

    public int getIdTypeObjet() {
        return idTypeObjet;
    }

    public String getNom() {
        return nom;
    }

    public int getValeur() {
        return valeur;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }

    public void setIdTypeObjet(int idTypeObjet) {
        this.idTypeObjet = idTypeObjet;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    
    
}
