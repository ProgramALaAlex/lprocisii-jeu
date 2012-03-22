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
	private static final long serialVersionUID = 6837718287270242394L;
	int idApparence;
    String sprite;

    public int getIdApparence() {
        return idApparence;
    }

    public String getSprite() {
        return sprite;
    }

    public void setIdApparence(int idApparence) {
        this.idApparence = idApparence;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
    
}
