
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.JoueurDB;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;

/**
 * @author Yan
 */
public class SubscribeCommand implements Command {

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

            JoueurDB db = new JoueurDB();

            // si les champs ne sont pas remplis
            if(pseudo.equals("") || mail.equals("") || pass1.equals("") || pass1.equals(""))
                request.setAttribute("erreur", "Erreur : tous les champs ne sont pas remplis.");
            else
            // Si un utilisateur existe déjà
            if (db.existe(pseudo)) {
                request.setAttribute("erreur", "Erreur : ce pseudo existe déjà.");
            }
            // Si tout est OK
            else if(pass1.equals(pass2)){
                
            }
            else {
                request.setAttribute("erreur", "Erreur : mot de passe.");
            }
        }
        return new ActionFlow(vue, vue + ".jsp", false);
    }
        
}