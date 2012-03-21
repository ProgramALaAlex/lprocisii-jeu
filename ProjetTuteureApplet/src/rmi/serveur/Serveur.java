package rmi.serveur;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

import combats.Combattant;
import combats.Monstre;

import constantes.Constantes;
import game.Callbacker;
import game.Player;

import rmi.interfaces.ChatRemoteInterface;
import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;

/**
 * LE SERVEUR RMI
 * NE PAS IMPORTER DANS LE JEU
 */
public class Serveur implements DispatcherInterface {
	private Vector<ReceiverInterface> listeRefJoueurs;
	private Vector<Player> listeJoueurs;

	public Serveur(){
		listeRefJoueurs = new Vector<ReceiverInterface>();
		listeJoueurs = new Vector<Player>();
		new SupprimerOfflineThread(this).start();
	}

	public static void main(String[] args){
		System.out.println("Dispatcher");
		System.setSecurityManager (null);
		try {
			//			System.setProperty("java.rmi.server.hostname", Constantes.IP_SERVEUR);
			DispatcherInterface server = new Serveur();
			DispatcherInterface proxy = (DispatcherInterface) UnicastRemoteObject.exportObject(server, 25465);
			Registry registry = LocateRegistry.createRegistry(Constantes.REGISTRY_PORT);
			registry.rebind(Constantes.REGISTRY_NAME, proxy);

			System.out.println("Serveur du Jeu : OK");

			ChatRemoteInterface remoteReference = (ChatRemoteInterface) UnicastRemoteObject.exportObject(new Chat(), 7777);
			registry.rebind(Constantes.REGISTRY_NAME_CHAT, remoteReference);

			System.out.println("Serveur du Chat : OK");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void inscription(Player p, ReceiverInterface client) throws RemoteException {
		this.listeRefJoueurs.add(client);
		this.listeJoueurs.add(p);
	}

	/**
	 * @return les joueurs d'une même map et toujours le leader du groupe auquel appartient le joueur
	 */
	@Override
	public ArrayList<Player> updateListe(UID id, String idMap) throws RemoteException {
		ArrayList<Player> res = new ArrayList<Player>();
		for (Player p : listeJoueurs)
			if(!p.getId().equals(id) && (p.getMapId().equals(idMap) || (p.getGroupe()!=null && p.getGroupe().contains(id))))
				res.add(p);
		return res;
	}

	/**
	 * On update le joueur dans notre liste
	 */
	@Override
	public void updatePosition(Player recu) throws RemoteException {
		if(listeJoueurs.contains(recu)){
			listeJoueurs.set(listeJoueurs.indexOf(recu), recu);
		}
	}

	@Override
	public boolean inviterJoueur(Player leader, Player invite) throws RemoteException{
		for(Player p : listeJoueurs){
			if(invite.equals(p)){
				ReceiverInterface ri = getReferenceCorrespondante(p);
				if(!ri.getPlayer().containsInvitation(leader.getGroupe())){
					ri.addInvitation(leader.getGroupe());
					return true;
				}
			}
		}
		return false;
	}

	private ReceiverInterface getReferenceCorrespondante(Player p){
		return listeRefJoueurs.get(listeJoueurs.indexOf(p));
	}

	@Override
	public void accepterInvitation(Player leader, Player invite) throws RemoteException {
		for(Player p : listeJoueurs){
			if(leader.equals(p)){
				ReceiverInterface ri = getReferenceCorrespondante(p);
				ri.invitationAcceptee(invite);
			}
		}
	}

	@Override
	public void disbandGroup(UID groupID) throws RemoteException {
		for(Player p : listeJoueurs){
			if(p.getGroupe()!=null && p.getGroupe().getID().equals(groupID)){
				getReferenceCorrespondante(p).disbandGroup();
			}
			else if(p.getGroupe()==null && p.containsInvitation(groupID))
				getReferenceCorrespondante(p).removeInvitation(groupID);
		}
	}

	public synchronized void retirerReferencesNeRepondantPas(){
		int i=-1;
		for (Iterator<ReceiverInterface> iterator = listeRefJoueurs.iterator(); iterator.hasNext();) {
			ReceiverInterface ri = (ReceiverInterface) iterator.next();
			i++;
			try {
				ri.getPlayer();
			} catch (RemoteException e) {
				try{
					listeJoueurs.remove(i);
					i--;
					iterator.remove();
					System.out.println("Un joueur ne répondant pas a été supprimé.");
				} catch(ConcurrentModificationException cme){
					System.out.println("humhum");
					new SupprimerOfflineThread(this).start();
				}
			}
		}
	}

	@Override
	public void entreEnModeCombat(Player leader, ArrayList<Player> listeJoueurs, ArrayList<Monstre> listeMonstre) throws RemoteException {
		// on récupère les joueurs du groupe du leader
		for(Player p : listeJoueurs){
			if(!p.equals(leader))
				//			if(p.getGroupe()!=null && p.getGroupe().equals(leader.getGroupe())){
				getReferenceCorrespondante(p).entrerEnCombat(listeJoueurs, listeMonstre);
			//			}
		}
	}

	@Override
	public void attaquer(Player emetteur, Combattant cible, int degats) throws RemoteException {
		// on récupère les joueurs du groupe de l'emetteur
		System.out.println("Methode attaquer détectée");
		for(Player p : emetteur.getListeJoueursCombatEnCours()){
			System.out.println(p.getId());
			if(!p.equals(emetteur)){
				//			if(!p.equals(emetteur) && p.getGroupe()!=null && p.getGroupe().equals(emetteur.getGroupe())){
				getReferenceCorrespondante(p).attaquer(cible, degats);
				//			}
			}
		}
	}

	@Override
	public void seSoigner(Player emetteur, int soin) throws RemoteException {
		// on récupère les joueurs du groupe de l'emetteur
		for(Player p : listeJoueurs){ //TODO emetteur.getListeJoueursCombatEnCours()
			if(!p.equals(emetteur) && p.getGroupe()!=null && p.getGroupe().equals(emetteur.getGroupe())){
				getReferenceCorrespondante(p).seSoigner(emetteur, soin);
			}
		}
	}

	@Override
	public boolean isConnected(Player p) throws RemoteException {
		return listeJoueurs.contains(p);
	}

	@Override
	public void refuserInvitation(UID groupID, Player refus) throws RemoteException {
		for(Player p : listeJoueurs){
			if(p.getGroupe()!=null && p.getGroupe().getID().equals(groupID) && p.getGroupe().isLeader(p)){
				ReceiverInterface ri = getReferenceCorrespondante(p);
				ri.invitationRefusee(refus);
			}
		}
	}

}
