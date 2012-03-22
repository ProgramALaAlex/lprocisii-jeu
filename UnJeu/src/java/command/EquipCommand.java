/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import beans.JoueurBean;
import beans.JoueurDB;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Loic
 */
public class EquipCommand implements Command{

    @Override
    public String getCommandName() {
        return "equipCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        JoueurDB idb = new JoueurDB();
        JoueurBean joueur = idb.getById(request.getSession().getAttribute("id")+"");
        
        if(request.getParameter("type").compareTo("arme") == 0){
            joueur.setIdArme(new Integer(request.getParameter("id")));
        }else{
            joueur.setIdArmure(new Integer(request.getParameter("id")));
        }
        idb.majJoueur(joueur);
        return new ActionFlow(vue, vue + ".jsp", false);
    }
    
}
