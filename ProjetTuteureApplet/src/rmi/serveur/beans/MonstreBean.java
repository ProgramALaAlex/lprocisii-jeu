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
public class MonstreBean implements Serializable{
	private static final long serialVersionUID = -3440875158898401859L;
	int idMonstre;
	 int attaque; 
	 int pvMax;
	 float vitesse;
	 String sprite;

    public int getAttaque() {
        return attaque;
    }
    
    public int getIdMonstre() {
        return idMonstre;
    }
    
    public int getPvMax() {
        return pvMax;
    }

    public String getSprite() {
        return sprite;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public void setIdMonstre(int id) {
        this.idMonstre = id;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
         
         
}
