
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import beans.JoueurBean;
import beans.JoueurDB;
import javax.servlet.http.*;

/**
 * @author Yan
 */
public class SubscribeCommand implements Command {

    public SubscribeCommand(){
        super();
    }
    
    @Override
    public String getCommandName() {
        return "subscribeCommand";
    }
    
    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        System.out.println(this.getCommandName());
        
        if (request.getParameter("submit") != null) {
            String pseudo = ""+request.getParameter("pseudo");
            String mail = ""+request.getParameter("mail");
            String pass1 = ""+request.getParameter("pass1");
            String pass2 = ""+request.getParameter("pass2");
            int apparence = Integer.parseInt(request.getParameter("apparence"));
            int attaque = Integer.parseInt(request.getParameter("attaque"));
            int vitesse = Integer.parseInt(request.getParameter("vitesse"));
            int pv = Integer.parseInt(request.getParameter("pv"));
            
            request.setAttribute("distrib", 100 - attaque - vitesse - pv);

            JoueurDB db = new JoueurDB();
            
            // si les champs ne sont pas remplis
            if(pseudo.equals("") || mail.equals("") || pass1.equals("") || pass2.equals(""))
                request.setAttribute("erreur", "Erreur : tous les champs ne sont pas remplis.");
            else
            // Si tous les points de stat ne sont pas distribués
            if (attaque+vitesse+pv != 100)
                request.setAttribute("erreur", "Erreur : tous les points de statistique n'ont pas été distribués.");
            else
            // Si un utilisateur existe déjà
            if (db.existe(pseudo)) {
                request.setAttribute("erreur", "Erreur : ce pseudo existe déjà.");
            }
            // Si tout est OK
            else if(pass1.equals(pass2)){
                JoueurBean joueur = new JoueurBean(pseudo, mail, pass1);
                joueur.setAttaque(100+attaque);
                joueur.setVitesse(100+vitesse);
                joueur.setPvMax(100+pv);
                joueur.setPvActuels(100+pv);
                joueur.setIdApparance(apparence);
                joueur.setIdMap(1);
                joueur.setDernierX(250);
                joueur.setDernierY(330);
                
                // ID de map? Coordonnées ? Apparence ?
                
                db.creerJoueur(joueur);
                
                //on creer l'inventaire du joueur
                InventaireBean inv = new InventaireBean(joueur.getIdJoueur());
                InventaireDB idb = new InventaireDB();
                idb.createInvenaire(inv);
                
                // A l'inscription, connexion automatique
                HttpSession session = request.getSession(false);
                session.setAttribute("id", joueur.getIdJoueur());
                session.setAttribute("pseudo", joueur.getPseudo());
                session.setAttribute("groupe", joueur.getGroupe());
                return new ActionFlow(vue, vue + ".jsp", false);
            }
            else {
                request.setAttribute("erreur", "Erreur : mauvaise confirmation du mot de passe.");
            }
        }
        return new ActionFlow(vue, vue + ".jsp?action=inscription", false);
    }
        
}