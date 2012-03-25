
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.JoueurBean;
import beans.JoueurDB;
import javax.servlet.http.*;

/**
 * @author Yan
 */
public class DebanCommand implements Command {

    @Override
    public String getCommandName() {
        return "debanCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        System.out.println(this.getCommandName());
        
        HttpSession session = request.getSession(false);
        String id = ""+request.getParameter("id");
        
        if (JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) && !id.equals("")) {
            
            JoueurDB db = new JoueurDB();
            db.debannirJoueur(id);
            request.setAttribute("info", "Info : le joueur a été débanni.");
        }
        return new ActionFlow(vue, vue + ".jsp?action=voir&id="+id, false);
    }
        
}