package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import exploration.Player;


/**
 * Les méthodes qu'on appelle du serveur.
 */
public interface DispatcherInterface extends Remote
{
    /**
     * Connecte un joueur au serveur
     * @param joueur
     */
    public void inscription(ReceiverInterface joueur) throws RemoteException;
    
    /**
     * envoie un joueur déjà existant au serveur
     */
//    public void updateJoueur(ReceiverInterface joueur, Player p) throws RemoteException;
    
    /**
     * Permet au joueur de récupérer la liste des autres joueurs updaté
     */
    public ArrayList<Player> updateListe(UID id, String idMap) throws RemoteException;
    
    public void updatePosition(Player p) throws RemoteException;
}
