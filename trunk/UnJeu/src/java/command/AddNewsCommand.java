
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import beans.JoueurBean;
import beans.JoueurDB;
import beans.NewBean;
import beans.NewDB;
import javax.servlet.http.*;

/**
 * @author Yan
 */
public class AddNewsCommand implements Command {

    public AddNewsCommand(){
        super();
    }
    
    @Override
    public String getCommandName() {
        return "addNewsCommand";
    }
    
    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        System.out.println(this.getCommandName());
        
        if (request.getParameter("submit") != null) {
            String titre = ""+request.getParameter("titre");
            String contenu = ""+request.getParameter("contenu");

            NewDB db = new NewDB();
            
            // si les champs ne sont pas remplis
            if(titre.equals("") || contenu.equals(""))
                request.setAttribute("erreur", "Erreur : tous les champs ne sont pas remplis.");
            // Si tout est OK
            else {
                NewBean news = new NewBean(titre, contenu);
                db.AddNew(news);
                request.setAttribute("info", "Info : news ajoutée.");
            }
        }
        return new ActionFlow(vue, vue + ".jsp", false);
    }
        
}