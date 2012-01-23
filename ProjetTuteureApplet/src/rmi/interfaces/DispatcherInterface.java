package rmi.interfaces;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import rmi.paquetJoueur.PaquetJoueur;


/**
 * Les méthodes qu'on appelle du serveur.
 */
public interface DispatcherInterface extends Remote
{
    String REGISTRY_NAME = "RMI_JEU";
    int REGISTRY_PORT = 3273;

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
    public ArrayList<PaquetJoueur> updateListe(UID id, String idMap) throws RemoteException;
    
    public void updatePosition(PaquetJoueur p) throws RemoteException;
}
