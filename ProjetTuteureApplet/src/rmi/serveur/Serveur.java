package rmi.serveur;

import game.Player;

import inventaire.Inventaire;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

import rmi.interfaces.ChatRemoteInterface;
import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;
import rmi.serveur.beans.ClefDB;
import rmi.serveur.beans.JoueurBean;
import rmi.serveur.beans.JoueurDB;

import combats.Combattant;
import combats.Monstre;

import constantes.Constantes;

/**
 * LE SERVEUR RMI
 * NE PAS IMPORTER DANS LE JEU
 */
public class Serveur implements DispatcherInterface {
	public static void main(String[] args){
		System.out.println("Dispatcher");
		System.setSecurityManager (null);
		try {
			// System.setProperty("java.rmi.server.hostname", Constantes.IP_SERVEUR);
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
	private Vector<ReceiverInterface> listeRefJoueurs;

	private Vector<Player> listeJoueurs;
	
	private SupprimerOfflineThread threadDeconnexion;

	public Serveur(){
		listeJoueurs = new Vector<Player>();
		listeRefJoueurs = new Vector<ReceiverInterface>();
		threadDeconnexion = new SupprimerOfflineThread(this);
		threadDeconnexion.start();
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
	public void attaquer(Player emetteur, Combattant cible, int degats) throws RemoteException {
		// on récupère les joueurs du groupe de l'emetteur
		for(Player p : emetteur.getListeJoueursCombatEnCours()){
			System.out.println(p.getId());
			if(!p.equals(emetteur) && p.getGroupe()!=null && p.getGroupe().equals(emetteur.getGroupe())){
				getReferenceCorrespondante(p).attaquer(cible, degats);
			}
		}
	}

	@Override
	public int convertirClefEnID(String clef) throws RemoteException {
		ClefDB db = new ClefDB();
		return db.getId(clef);
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

	@Override
	public void entreEnModeCombat(Player leader, ArrayList<Player> listeJoueurs, ArrayList<Monstre> listeMonstre) throws RemoteException {
		// on récupère les joueurs du groupe du leader
		for(Player p : listeJoueurs){
			if(!p.equals(leader))
				getReferenceCorrespondante(p).entrerEnCombat(listeJoueurs, listeMonstre);
		}
	}

	@Override
	public void equiperArmure(Player emetteur, int armure) throws RemoteException {
		for(Player p : listeJoueurs)
			if(!p.equals(emetteur) && p.getMapId().equals(emetteur.getMapId()))
				getReferenceCorrespondante(p).equiperArmure(emetteur, armure);
	}

	@Override
	public JoueurBean getJoueurByID(String id) throws RemoteException {
		for (Iterator<Player> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Player p = (Player) iterator.next();
			if(p.getBDD_ID()==Integer.parseInt(id)){
				JoueurDB db = new JoueurDB();
				JoueurBean jb = db.getById(Integer.toString(p.getBDD_ID()));
				jb.setDernierX((int) p.getX());
				jb.setDernierY((int) p.getY());
				jb.setIdMap(Integer.parseInt(p.getMapId()));
				jb.setPvActuels(p.getPvCourant());
				try {
					jb.setInventaire(writeInventaire(p.getInventaire()));
				} catch (Exception e) {
					System.err.println("Erreur lors du chargement de l'inventaire");
				}
				listeRefJoueurs.remove(getReferenceCorrespondante(p));
				iterator.remove();
				return jb;
			}
		}
				
		JoueurDB db = new JoueurDB();
		return db.getById(id);
	}

	@Override
	public String getJoueurMapByID(int id) throws RemoteException {
		JoueurDB db = new JoueurDB();
		return db.getSprite(id);
	}

	private ReceiverInterface getReferenceCorrespondante(Player p){
		return listeRefJoueurs.get(listeJoueurs.indexOf(p));
	}

	@Override
	public void incrementerCombat(int BDD_ID) throws RemoteException {
		for(Player p : listeJoueurs)
			if(p.getBDD_ID()==BDD_ID){
				JoueurDB db = new JoueurDB();
				db.incrementerCombat(db.getById(Integer.toString(BDD_ID)));
			}
	}

	@Override
	public void incrementerMonstreTues(int BDD_ID) throws RemoteException {
		for(Player p : listeJoueurs)
			if(p.getBDD_ID()==BDD_ID){
				JoueurDB db = new JoueurDB();
				db.incrementerMonstreTues(db.getById(Integer.toString(BDD_ID)));
			}
	}

	@Override
	public void inscription(Player p, ReceiverInterface client) throws RemoteException {
		this.listeRefJoueurs.add(client);
		this.listeJoueurs.add(p);
		System.out.println(p.getNom()+" s'est connecté.");
	}

	@Override
	public boolean inviterJoueur(Player leader, Player invite) throws RemoteException{
		for(Player p : listeJoueurs){
			if(invite.equals(p)){
				if(!p.containsInvitation(leader.getGroupe())){
					ReceiverInterface ri = getReferenceCorrespondante(p);
					ri.addInvitation(leader.getGroupe());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isConnected(Player p) throws RemoteException {
		return listeJoueurs.contains(p);
	}

	@Override
	public void kick(String id) throws RemoteException {
		Player del = null;
		for(Player p : listeJoueurs)
			if(p.getBDD_ID()==Integer.parseInt(id)){
				del = p;
				break;
			}
		if(del!=null){
			getReferenceCorrespondante(del).kicked();
			listeRefJoueurs.remove(getReferenceCorrespondante(del));
			listeJoueurs.remove(del);
		}
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

	public synchronized void retirerReferencesNeRepondantPas(){
		int i=0;
		synchronized (listeRefJoueurs) {
			for (Iterator<ReceiverInterface> iterator = listeRefJoueurs.iterator(); iterator.hasNext();) {
				ReceiverInterface ri = iterator.next();
				try {
					ri.getPlayer();
				} catch (RemoteException e) {
					try{
						Player p = listeJoueurs.get(i);
						try {
							JoueurDB db = new JoueurDB();
							JoueurBean jb = db.getById(Integer.toString(p.getBDD_ID()));
							jb.setDernierX((int) p.getX());
							jb.setDernierY((int) p.getY());
							jb.setIdMap(Integer.parseInt(p.getMapId()));
							jb.setPvActuels(p.getPvCourant());
							jb.setInventaire(writeInventaire(p.getInventaire()));
							db.majJoueur(jb);
							System.out.println(p.getNom()+" modifié dans la base de données.");
						} catch (Exception e1) {
							System.err.println("La modification du joueur n'a pas pu s'effectuer");
						}
						listeJoueurs.remove(i);
						i--;
						iterator.remove();
						System.out.println("Un joueur ne répondant pas a été supprimé. ("+p.getNom()+")");
					} catch(ConcurrentModificationException cme){
						System.err.println("Désynchronisation des listes - redémarrer le serveur");
						threadDeconnexion = new SupprimerOfflineThread(this);
						threadDeconnexion.start();
					}
				}
				i++;
			}
		}
	}

	@Override
	public void seSoigner(Player emetteur, int soin) throws RemoteException {
		// on récupère les joueurs du groupe de l'emetteur
		for(Player p : emetteur.getListeJoueursCombatEnCours()){
			if(!p.equals(emetteur) && p.getGroupe()!=null && p.getGroupe().equals(emetteur.getGroupe())){
				getReferenceCorrespondante(p).seSoigner(emetteur, soin);
			}
		}
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
	
	/**
	 * Convertir inventaire en String pour la BDD
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private String writeInventaire(Inventaire obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baos);
		oout.writeObject(obj);
		oout.close();
		return new String(baos.toByteArray());
	}	

}
