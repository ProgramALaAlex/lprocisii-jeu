package game;

import inventaire.Armure;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import rmi.interfaces.ReceiverInterface;

import combats.Combat;
import combats.Combattant;
import combats.Monstre;

import constantes.Constantes;
import exploration.Exploration;

/**
 * TEMPORAIRE LE TEMPS DE TROUVER UNE AUTRE SOLUTION MAIS JE VOIS PAS LA
 */
public class Callbacker implements ReceiverInterface, Serializable{
	private static final long serialVersionUID = -4998818556133180735L;
	private Player player;
	

	public Callbacker(Player player) {
		super();
		this.player = player;
	}


	@Override
	public void addInvitation(Groupe g) throws RemoteException {
		player.addInvitation(g);
	}


	@Override
	public void attaquer(Combattant cible, int degats) throws RemoteException {
		Combat.attaqueOnline(cible, degats);
	}


	@Override
	public void disbandGroup() throws RemoteException {
		System.out.println("Groupe supprimé");
		MainGame.getPlayer().setGroupe(null);
	}


	@Override
	public void entrerEnCombat(ArrayList<Player> listeJoueurs, ArrayList<Monstre> listeMonstre) {
		Combat.setListes(listeJoueurs, listeMonstre);
		MainGame.getPlayer().demarrerCombat();
	}


	@Override
	public void equiperArmure(Player emetteur, int armure) throws RemoteException {
		if(Exploration.getListeJoueurLoc().contains(emetteur)){
			Exploration.getListeJoueurLoc().get(Exploration.getListeJoueurLoc().indexOf(emetteur)).getInventaire().addObjet(new Armure(armure));
			Exploration.getListeJoueurLoc().get(Exploration.getListeJoueurLoc().indexOf(emetteur)).getInventaire().equiperArmure(new Armure(armure));
			Exploration.newSkin(Exploration.getListeJoueurLoc().get(Exploration.getListeJoueurLoc().indexOf(emetteur)));
		}
	}


	@Override
	public Player getPlayer() throws RemoteException {
		return player;
	}


	@Override
	public void invitationAcceptee(Player invite) throws RemoteException {
		System.out.println(invite.getNom()+" a accepté l'invitation.");
		MainGame.getPlayer().getGroupe().add(invite.getId());
	}


	@Override
	public void invitationRefusee(Player refus) throws RemoteException {
		if(Exploration.getListeJoueurLoc().contains(refus)){
			Exploration.getListeJoueurLoc().get(Exploration.getListeJoueurLoc().indexOf(refus)).clearInvit();
		}
	}


	@Override
	public void kicked() throws RemoteException {
		Constantes.ONLINE = false;
		MainGame.getPlayer().setNom("Vous avez été banni : vous êtes maintenant en mode solo");
	}


	@Override
	public void removeInvitation(UID groupID) {
		if(MainGame.getPlayer().containsInvitation(groupID))
			try {
				MainGame.getPlayer().refuserInvitation(groupID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
	}


	@Override
	public void seSoigner(Player emetteur, int soin) throws RemoteException {
		Combat.seSoignerRecevoir(emetteur, soin);
	}

}
