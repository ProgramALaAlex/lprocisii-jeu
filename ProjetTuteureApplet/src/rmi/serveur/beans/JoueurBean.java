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
public class JoueurBean implements Serializable{
	private static final long serialVersionUID = -507100696017731615L;

		public static java.sql.Date getCurrentJavaSqlDate() {
		    java.util.Date today = new java.util.Date();
		    return new java.sql.Date(today.getTime());
		}
	int idJoueur;
	String pseudo;
	String mail;
	String pass;
	Date dateInscription;
	float attaque;
	float vitesse;
	int pvMax;
	int pvActuels;
	int totalCombats;
	int totalMonstres;
	int dernierX;
	int dernierY;
	int idMap;
	int idArme;
	int idArmure;
	int idApparance;
        int newsletter;
        
    int groupe;
    
    public JoueurBean(String pseudo, String mail, String pass) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.pass = pass;
        this.dateInscription = getCurrentJavaSqlDate();
	this.attaque = 0;
	this.vitesse = 0;
	this.pvMax = 0;
	this.pvActuels = 0;
	this.totalCombats = 0;
	this.totalMonstres = 0;
	this.dernierX = 0;
	this.dernierY = 0;
	this.idMap = 0;
	this.idArme = 0;
	this.idArmure = 0;
	this.idApparance = 0;
	this.newsletter = 0;
        this.groupe = 1;
    }

    public float getAttaque() {
        return attaque;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public int getDernierX() {
        return dernierX;
    }
    
    public int getDernierY() {
        return dernierY;
    }
    
    public int getGroupe() {
        return groupe;
    }

    public int getIdApparence() {
        return idApparance;
    }

    public int getIdArme() {
        return idArme;
    }

    public int getIdArmure() {
        return idArmure;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public int getIdMap() {
        return idMap;
    }

    public String getMail() {
        return mail;
    }

    public int getNewsletter() {
        return newsletter;
    }

    public String getPass() {
        return pass;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getPvActuels() {
        return pvActuels;
    }

    public int getPvMax() {
        return pvMax;
    }

    public int getTotalCombats() {
        return totalCombats;
    }

    public int getTotalMonstres() {
        return totalMonstres;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setAttaque(float attaque) {
        this.attaque = attaque;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setDernierX(int dernierX) {
        this.dernierX = dernierX;
    }

    public void setDernierY(int dernierY) {
        this.dernierY = dernierY;
    }

    public void setGroupe(int groupe) {
        this.groupe = groupe;
    }

    public void setIdApparance(int idApparance) {
        this.idApparance = idApparance;
    }

    public void setIdArme(int idArme) {
        this.idArme = idArme;
    }

    public void setIdArmure(int idArmure) {
        this.idArmure = idArmure;
    }

    public void setIdJoueur(int id) {
        this.idJoueur = id;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNewsletter(int newsletter) {
        this.newsletter = newsletter;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPvActuels(int pvActuels) {
        this.pvActuels = pvActuels;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public void setTotalCombats(int totalCombats) {
        this.totalCombats = totalCombats;
    }

    public void setTotalMonstres(int totalMonstres) {
        this.totalMonstres = totalMonstres;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

        
        
}
