/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import javax.servlet.http.*;

/**
 *
 * @author Yan
 */
public class ViewCommand implements Command {

    @Override
    public String getCommandName() {
        return "viewCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        return new ActionFlow(vue, vue+".jsp", false);
    } //actionPerform
}
