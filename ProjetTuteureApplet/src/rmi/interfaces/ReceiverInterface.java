package rmi.interfaces;


import game.Groupe;
import game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import combats.Combattant;
import combats.Monstre;

/**
 * Les méthode que le serveur va appeler à distance.
 */
public interface ReceiverInterface extends Remote{
	
	public void addInvitation(Groupe g) throws RemoteException;
	public void invitationAcceptee(Player invite) throws RemoteException;
	public Player getPlayer() throws RemoteException;
	public void disbandGroup()  throws RemoteException;
	
	
	
	// -------------
	// COMBAT
	// -------------
	/**
	 * Rejoint le leader en combat
	 */
	public void entrerEnCombat(ArrayList<Player> listeJoueurs, ArrayList<Monstre> listeMonstre) throws RemoteException;
    
	public void attaquer(Combattant cible, int degats) throws RemoteException;
	
	public void seSoigner(Player emetteur, int soin) throws RemoteException;
}
