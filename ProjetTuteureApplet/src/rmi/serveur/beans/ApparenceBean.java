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
public class ApparenceBean implements Serializable{
    int idApparence;
    String sprite;

    public int getIdApparence() {
        return idApparence;
    }

    public void setIdApparence(int idApparence) {
        this.idApparence = idApparence;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
    
}
