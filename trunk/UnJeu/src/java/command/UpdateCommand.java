
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import beans.JoueurBean;
import beans.JoueurDB;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import rmi.interfaces.DispatcherInterface;

/**
 * @author Yan
 */
public class UpdateCommand implements Command {

    @Override
    public String getCommandName() {
        return "updateCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        System.out.println(this.getCommandName());
        
        HttpSession session = request.getSession(false);
        String id = ""+request.getParameter("id");
        
        if (JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) && !id.equals("") && request.getParameter("submit") != null) {
            
            String pseudo = ""+request.getParameter("pseudo");
            String mail = ""+request.getParameter("mail");
            int attaque = Integer.parseInt(request.getParameter("attaque"));
            int vitesse = Integer.parseInt(request.getParameter("vitesse"));
            int pv = Integer.parseInt(request.getParameter("pv"));
            int totalCombats = Integer.parseInt(request.getParameter("totalCombats"));
            int totalMonstres = Integer.parseInt(request.getParameter("totalMonstres"));

            JoueurDB db = new JoueurDB();
            
            // si les champs ne sont pas remplis
            if(pseudo.equals("") || mail.equals(""))
                request.setAttribute("erreur", "Erreur : tous les champs ne sont pas remplis.");
            else
            // Si des nombres sont négatifs
            if (attaque<0 || vitesse<0 || pv<0 || totalCombats<0 || totalMonstres<0)
                request.setAttribute("erreur", "Erreur : les nombres doivent être positifs.");
            // Si tout est OK
            else {
                
                String IP_SERVEUR = "localhost";
                int REGISTRY_PORT = 25565;
                String REGISTRY_NAME = "RMI_JEU";
                System.out.println(IP_SERVEUR);
                System.out.println(REGISTRY_PORT);
                System.out.println(REGISTRY_NAME);
                
                try {
                    Registry registry = LocateRegistry.getRegistry(IP_SERVEUR, REGISTRY_PORT);
                    DispatcherInterface remoteReference = (DispatcherInterface) Naming.lookup("rmi://"+IP_SERVEUR+":"+REGISTRY_PORT+"/"+REGISTRY_NAME);
                    
                    remoteReference.kick(id);
                } catch (Exception ex) {
                    Logger.getLogger(UpdateCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JoueurBean joueur = db.getById(id);
                joueur.setPseudo(pseudo);
                joueur.setMail(mail);
                joueur.setAttaque(attaque);
                joueur.setVitesse(vitesse);
                joueur.setPvMax(pv);
                joueur.setPvActuels(pv);
                joueur.setTotalCombats(totalCombats);
                joueur.setTotalMonstres(totalMonstres);
                
                db.majJoueur(joueur);
                request.setAttribute("info", "Info : le joueur a été mis à jour.");
            }
        }
        return new ActionFlow(vue, vue + ".jsp?action=voir&id="+id, false);
    }
        
}
