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
public class DisconnectCommand implements Command {

    @Override
    public String getCommandName() {
        return "disconnectCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        HttpSession session = request.getSession(false);
        session.invalidate();
        
        return new ActionFlow(vue, vue + ".jsp", false);
    }

}
