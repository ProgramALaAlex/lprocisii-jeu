package game;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import combats.Combat;
import combats.Combattant;
import combats.Monstre;

import rmi.interfaces.ReceiverInterface;

/**
 * TEMPORAIRE LE TEMPS DE TROUVER UNE AUTRE SOLUTION MAIS JE VOIS PAS LA
 */
public class Callbacker implements ReceiverInterface, Serializable{
	private Player player;
	

	public Callbacker(Player player) {
		super();
		this.player = player;
	}


	@Override
	public void addInvitation(Groupe g) throws RemoteException {
		player.addInvitation(g);
	}


	public Player getPlayer() throws RemoteException {
		return player;
	}


	@Override
	public void invitationAcceptee(Player invite) throws RemoteException {
		System.out.println(invite.getNom()+" a accepté l'invitation.");
		MainGame.getPlayer().getGroupe().add(invite.getId());
	}


	@Override
	public void disbandGroup() throws RemoteException {
		System.out.println("Groupe supprimé");
		MainGame.getPlayer().setGroupe(null);
	}


	@Override
	public void entrerEnCombat(ArrayList<Monstre> listeMonstre) {
		Combat.setListeMonstre(listeMonstre);
		MainGame.getPlayer().demarrerCombat();
	}


	@Override
	public void attaquer(Combattant cible, int degats) throws RemoteException {
		Combat.attaqueOnline(cible, degats);
	}

}
