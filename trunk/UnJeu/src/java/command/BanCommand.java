
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.JoueurBean;
import beans.JoueurDB;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import rmi.interfaces.DispatcherInterface;

/**
 * @author Yan
 */
public class BanCommand implements Command {

    @Override
    public String getCommandName() {
        return "banCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        System.out.println(this.getCommandName());
        
        HttpSession session = request.getSession(false);
        String id = ""+request.getParameter("id");
        
        if (JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) && !id.equals("") && request.getParameter("submit") != null) {
            
            int semaines = Integer.parseInt(request.getParameter("semaines"));
            int jours = Integer.parseInt(request.getParameter("jours"));
            String raison = ""+request.getParameter("raison");
            String date = "";
            
            if(request.getParameter("definitif") == null) {
                jours += (semaines*7);
                date = "DATE_ADD(NOW(), INTERVAL "+jours+" DAY)";
            }
            
            
            JoueurDB db = new JoueurDB();
            
            String IP_SERVEUR = "localhost";
            int REGISTRY_PORT = 25565;
            String REGISTRY_NAME = "RMI_JEU";
            System.out.println(IP_SERVEUR);
            System.out.println(REGISTRY_PORT);
            System.out.println(REGISTRY_NAME);

            try {
                Registry registry = LocateRegistry.getRegistry(IP_SERVEUR, REGISTRY_PORT);
                DispatcherInterface remoteReference = (DispatcherInterface) Naming.lookup("rmi://"+IP_SERVEUR+":"+REGISTRY_PORT+"/"+REGISTRY_NAME);

                remoteReference.testSysout();
            } catch (Exception ex) {
                Logger.getLogger(UpdateCommand.class.getName()).log(Level.SEVERE, null, ex);
            }

            db.bannirJoueur(id, raison, date);
            request.setAttribute("info", "Info : le joueur a été banni.");
        }
        return new ActionFlow(vue, vue + ".jsp?action=voir&id="+id, false);
    }
        
}