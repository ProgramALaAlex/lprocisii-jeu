/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package command;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Yan
 */
public class CommandManager {

    protected static ConcurrentHashMap<String, Command> cmds = new ConcurrentHashMap<String, Command>();

    static {
        init();
    }

    public static void init() {
        cmds.put("view", new ViewCommand());
        cmds.put("test", new TestCommand());
        //cmds.put("supprimer", new RemoveCommand());
        //cmds.put("payer", new CheckoutCommand());

    }

    public static Command getCommand(String commandName) {
        return cmds.get(commandName);
    }
}