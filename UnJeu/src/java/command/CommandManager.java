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
        cmds.put("inscription", new SubscribeCommand());
        cmds.put("connexion", new ConnectCommand());
        cmds.put("deconnexion", new DisconnectCommand());
        cmds.put("inventaire", new InventaireCommand());
        cmds.put("supprimer", new DeleteCommand());
        cmds.put("modifier", new UpdateCommand());
        cmds.put("bannir", new BanCommand());
        cmds.put("debannir", new DebanCommand());
        cmds.put("equipe", new EquipCommand());
        cmds.put("actualise", new ActualiseCommand());
        cmds.put("ajouterNews", new AddNewsCommand());
    }

    public static Command getCommand(String commandName) {
        return cmds.get(commandName);
    }
}