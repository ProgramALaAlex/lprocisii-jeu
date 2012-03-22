/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.JoueurBean;
import beans.JoueurDB;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Loic
 */
public class ActualiseCommand implements Command{

    @Override
    public String getCommandName() {
        return "actualiseCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
         String vue = "actualise";
        
        //JoueurDB idb = new JoueurDB();
        //JoueurBean joueur = idb.getById(request.getSession().getAttribute("id")+"");
        
        return new ActionFlow(vue, vue + ".jsp", false);
    }
    
}
