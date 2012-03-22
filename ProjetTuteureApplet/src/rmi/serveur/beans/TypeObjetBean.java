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
public class TypeObjetBean implements Serializable{
	private static final long serialVersionUID = -3122066339252827371L;
	int idType;
    String nom;

    public int getIdType() {
        return idType;
    }

    public String getNom() {
        return nom;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
