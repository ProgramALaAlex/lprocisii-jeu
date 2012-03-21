/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package command;

import java.util.concurrent.ConcurrentHashMap;
import sun.misc.BASE64Encoder;

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
        //cmds.put("test", new TestCommand());
        cmds.put("inscription", new SubscribeCommand());
        cmds.put("connexion", new ConnectCommand());
        cmds.put("deconnexion", new DisconnectCommand());
        cmds.put("supprimer", new DeleteCommand());
        

    }

    public static Command getCommand(String commandName) {
        return cmds.get(commandName);
    }
}