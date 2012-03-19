/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Loic
 */
public class MonstreBean {
    	 int idMonstre;
	 int attaque; 
	 int pvMax;
	 float vitesse;
	 String sprite;

    public int getIdMonstre() {
        return idMonstre;
    }
    
    public void setIdMonstre(int id) {
        this.idMonstre = id;
    }
    
    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getPvMax() {
        return pvMax;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
         
         
}
