/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.JoueurBean;
import beans.JoueurDB;
import javax.servlet.http.*;

/**
 *
 * @author Yan
 */
public class ConnectCommand implements Command {

    @Override
    public String getCommandName() {
        return "connectCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        if (request.getParameter("submit") != null) {
            HttpSession session = request.getSession(false);
            
            String login = ""+request.getParameter("login");
            String password = ""+request.getParameter("pass");

            if (!login.equals("") && !password.equals("")) {
                JoueurDB db = new JoueurDB();
                JoueurBean joueur;
                if ((joueur = db.checkJoueur(login, password)) != null) {
                    String[] ban = db.estBanni(joueur.getIdJoueur());
                    if (ban == null) {
                        session.setAttribute("id", joueur.getIdJoueur());
                        session.setAttribute("pseudo", joueur.getPseudo());
                        session.setAttribute("groupe", joueur.getGroupe());
                        return new ActionFlow(vue, vue + ".jsp", false);
                    } else {
                        request.setAttribute("erreur", "Erreur : vous avez été banni ( Echéance: "+ban[1]+(ban[0].equals("") ? "" : " / Raison: "+ban[0])+" )");
                    }
                } else {
                    request.setAttribute("erreur", "Erreur : identifiants invalides.");
                }
            } else {
                request.setAttribute("erreur", "Erreur : entrez un pseudo et un mot de passe.");
            }
        }
        return new ActionFlow(vue, vue + ".jsp?action=connexion", false);
    }

}
