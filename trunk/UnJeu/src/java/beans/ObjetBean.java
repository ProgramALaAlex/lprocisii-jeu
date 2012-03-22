/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Loic
 */
public class ObjetBean {
    int idObjet;
    String nom;
    int valeur;
    int idTypeObjet;
    String url;

    public int getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }

    public int getIdTypeObjet() {
        return idTypeObjet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    

    public void setIdTypeObjet(int idTypeObjet) {
        this.idTypeObjet = idTypeObjet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    
    
}
