
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
public class DeleteCommand implements Command {

    @Override
    public String getCommandName() {
        return "deleteCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        System.out.println(this.getCommandName());
        
        HttpSession session = request.getSession(false);
        String id = ""+request.getParameter("id");
        
        if (JoueurBean.estAdmin((Integer)session.getAttribute("groupe"))) {
            JoueurDB db = new JoueurDB();
            if (!id.equals(""))
                db.deleteJoueur(id);
                request.setAttribute("info", "Info : le joueur a été supprimé.");
                return new ActionFlow(vue, vue + ".jsp?action=armu", false);
        }
        return new ActionFlow(vue, vue + ".jsp?action=voir&id="+id, false);
    }
        
}