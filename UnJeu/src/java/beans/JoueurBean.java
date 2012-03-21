/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Loic
 */
public class JoueurBean {
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
        
    public static java.sql.Date getCurrentJavaSqlDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    
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

    public int getGroupe() {
        return groupe;
    }

    public static boolean estAdmin(int groupe) {
        return (groupe == 2);
    }
    
    public void setGroupe(int groupe) {
        this.groupe = groupe;
    }

    public int getIdJoueur() {
        return idJoueur;
    }
    
    public void setIdJoueur(int id) {
        this.idJoueur = id;
    }
    
    public int getAttaque() {
        return (int)attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public int getDernierX() {
        return dernierX;
    }

    public void setDernierX(int dernierX) {
        this.dernierX = dernierX;
    }

    public int getDernierY() {
        return dernierY;
    }

    public void setDernierY(int dernierY) {
        this.dernierY = dernierY;
    }

    public int getIdApparance() {
        return idApparance;
    }

    public void setIdApparance(int idApparance) {
        this.idApparance = idApparance;
    }

    public int getIdArme() {
        return idArme;
    }

    public void setIdArme(int idArme) {
        this.idArme = idArme;
    }

    public int getIdArmure() {
        return idArmure;
    }

    public void setIdArmure(int idArmure) {
        this.idArmure = idArmure;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(int newsletter) {
        this.newsletter = newsletter;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getPvActuels() {
        return pvActuels;
    }

    public void setPvActuels(int pvActuels) {
        this.pvActuels = pvActuels;
    }

    public int getPvMax() {
        return pvMax;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public int getTotalCombats() {
        return totalCombats;
    }

    public void setTotalCombats(int totalCombats) {
        this.totalCombats = totalCombats;
    }

    public int getTotalMonstres() {
        return totalMonstres;
    }

    public void setTotalMonstres(int totalMonstres) {
        this.totalMonstres = totalMonstres;
    }

    public int getVitesse() {
        return (int)vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
        
        
}
