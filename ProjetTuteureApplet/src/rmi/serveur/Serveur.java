package rmi.serveur;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import constantes.Constantes;
import exploration.Player;

import rmi.interfaces.ChatRemoteInterface;
import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;

/**
 * LE SERVEUR RMI
 * NE PAS IMPORTER DANS LE JEU
 */
public class Serveur implements DispatcherInterface {
	private ArrayList<ReceiverInterface> listeRefJoueurs;
	private ArrayList<Player> listeJoueurs;
	
	public Serveur(){
		listeRefJoueurs = new ArrayList<ReceiverInterface>();
		listeJoueurs = new ArrayList<Player>();
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
	public void inscription(ReceiverInterface joueur) throws RemoteException {
		this.listeRefJoueurs.add(joueur);
		this.listeJoueurs.add((Player) joueur);
	}

	@Override
	public ArrayList<Player> updateListe(UID id, String idMap) throws RemoteException {
		ArrayList<Player> res = new ArrayList<Player>();
		for (Player p : listeJoueurs)
			if(!p.getUserId().equals(id) && p.getMapId().equals(idMap))
				res.add(p);
		return res;
	}

	@Override
	public void updatePosition(Player paquetJoueur) throws RemoteException {
		for(Player p : this.listeJoueurs)
			if(p.getUserId().equals(paquetJoueur.getUserId())){
				p.setPosition(paquetJoueur.getPosition());
				p.setDirection(paquetJoueur.getDirection());
				p.setMapId(paquetJoueur.getMapId());
			}
	}

}
