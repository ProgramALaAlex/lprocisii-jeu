
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import beans.JoueurBean;
import beans.JoueurDB;
import beans.NewDB;
import javax.servlet.http.*;

/**
 * @author Yan
 */
public class DeleteNewsCommand implements Command {

    @Override
    public String getCommandName() {
        return "deleteNewsCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        System.out.println(this.getCommandName());
        
        HttpSession session = request.getSession(false);
        String id = ""+request.getParameter("id");
        
        if (JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) && !id.equals("")) {
            NewDB db = new NewDB();
            if (!id.equals(""))
                db.deleteNews(id);
                request.setAttribute("info", "Info : news supprimée.");
        }
        return new ActionFlow(vue, vue + ".jsp", false);
    }
        
}