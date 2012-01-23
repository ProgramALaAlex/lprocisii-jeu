package rmi.serveur;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;
import rmi.paquetJoueur.PaquetJoueur;

/**
 * LE SERVEUR RMI
 * NE PAS IMPORTER DANS LE JEU
 */
public class Serveur implements DispatcherInterface {
	private ArrayList<ReceiverInterface> listeRefJoueurs;
	private ArrayList<PaquetJoueur> listeJoueurs;
	
	public Serveur(){
		listeRefJoueurs = new ArrayList<ReceiverInterface>();
		listeJoueurs = new ArrayList<PaquetJoueur>();
	}

	public static void main(String[] args){
		System.setSecurityManager (null);
		try {
			System.out.println("Dispatcher");
			
			/* création de l'objet serveur */
			DispatcherInterface server = new Serveur();
			/* création du proxy */
			DispatcherInterface proxy = (DispatcherInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(REGISTRY_NAME, proxy);
			System.out.println("dispatcher OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void inscription(ReceiverInterface joueur) throws RemoteException {
		this.listeRefJoueurs.add(joueur);
		this.listeJoueurs.add(new PaquetJoueur(joueur.getUserId(), new Point((int)joueur.getX(), (int)joueur.getY()), joueur.getDirection(), joueur.getSpriteSheetName(), joueur.getMapId()));
	}

	@Override
	public ArrayList<PaquetJoueur> updateListe(UID id, String idMap) throws RemoteException {
		ArrayList<PaquetJoueur> res = new ArrayList<PaquetJoueur>();
		for (PaquetJoueur p : listeJoueurs)
			if(!p.getId().equals(id) && p.getMapName().equals(idMap))
				res.add(p);
		return res;
	}

	@Override
	public void updatePosition(PaquetJoueur paquetJoueur) throws RemoteException {
		for(PaquetJoueur p : this.listeJoueurs)
			if(p.getId().equals(paquetJoueur.getId())){
				p.setPosition(paquetJoueur.getPosition());
				p.setDirection(paquetJoueur.getDirection());
				p.setMapName(paquetJoueur.getMapName());
			}
	}

}
