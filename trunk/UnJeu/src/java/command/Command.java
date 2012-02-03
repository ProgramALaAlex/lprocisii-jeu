/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package command;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan
 */
public interface Command {
    public String getCommandName();
    public ActionFlow actionPerform(HttpServletRequest request);
}

