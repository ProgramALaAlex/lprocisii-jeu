package rmi.interfaces;

import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import rmi.serveur.beans.JoueurBean;

import combats.Combattant;
import combats.Monstre;


/**
 * Les méthodes qu'on appelle du serveur.
 */
public interface DispatcherInterface extends Remote
{
    public void accepterInvitation(Player leader, Player invite) throws RemoteException;
    
    
    /**
     * Utilisé en combat lorsqu'un montre ou un joueur attaque
     * (si monstre, appelé par leader.)
     */
    public void attaquer(Player emetteur, Combattant cible, int degats) throws RemoteException;
    
    /**
	 * Converti la clef en ID et la supprime
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public int convertirClefEnID(String clef) throws RemoteException;
    
    /**
     * Suppression du groupe : tous les membres quittent ce groupe
     * @param groupID
     * @throws RemoteException
     */
    public void disbandGroup(UID groupID) throws RemoteException;
    
    /**
     * Lorsque le leader d'un groupe non vide entre en combat : on prévient les autres
     * @param leader
     * @param listeMonstre 
     * @throws RemoteException
     */
    public void entreEnModeCombat(Player leader, ArrayList<Player> listeAutresJoueurs, ArrayList<Monstre> listeMonstre) throws RemoteException;
    
    public void equiperArmure(Player emetteur, int armure) throws RemoteException;
    
    public JoueurBean getJoueurByID(String id) throws RemoteException;

    public String getJoueurMapByID(int id) throws RemoteException;
    
    public void incrementerCombat(int BDD_ID) throws RemoteException;
//	public String incrementerNbCombat(int id) throws RemoteException;
    
    public void incrementerMonstreTues(int BDD_ID) throws RemoteException;
    
	/**
     * Connecte un joueur au serveur
     * @param joueur
     */
    public void inscription(Player player, ReceiverInterface client) throws RemoteException;
	
	/**
     * @param leader
     * @param invite
     * @return true si l'invitation a été envoyée
     * @throws RemoteException
     */
    public boolean inviterJoueur(Player leader, Player invite) throws RemoteException;
	/**
     * @param un joueur
     * @return true si connecté
     * @throws RemoteException
     */
    public boolean isConnected(Player p) throws RemoteException;
	
	public void kick(String id) throws RemoteException;
	
	public void refuserInvitation(UID groupID, Player refus) throws RemoteException;
	/**
     * Utilisé en combat, lorsqu'un joueur utilise un potion
     * @param emetteur
     * @param soin
     * @throws RemoteException
     */
    public void seSoigner(Player emetteur, int soin) throws RemoteException;
	
	/**
     * Permet au joueur de récupérer la liste des autres joueurs updaté
     */
    public ArrayList<Player> updateListe(UID id, String idMap) throws RemoteException;
	
	/**
     * Permet au serveur de mettre à jour un joueur
     * @param p
     * @throws RemoteException
     */
    public void updatePosition(Player p) throws RemoteException;
    
}
