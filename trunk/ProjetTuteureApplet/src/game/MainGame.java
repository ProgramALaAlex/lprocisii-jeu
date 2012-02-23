package game;

import inventaire.Armure;

import java.applet.Applet;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import exploration.Exploration;
import gui.Menu;

import netscape.javascript.JSObject;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import combats.Combat;

import rmi.interfaces.ChatReceiverInterface;
import rmi.interfaces.ChatRemoteInterface;
import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;

import constantes.Constantes;



public class MainGame extends StateBasedGame implements Observer, ChatReceiverInterface{
	private static DispatcherInterface remoteReference;
	private static ChatRemoteInterface remoteReferenceChat;
	private static ArrayList<Player> listePaquetJoueurs;
	private static Player player;
	private static float x = 250f;
	private static float y = 330f;

	public MainGame() {
		super("Projet Tuteuré");
		try { 
			if (Constantes.MODE_ONLINE){
				System.out.println("Connexion en cours...");
//				System.setProperty("java.rmi.server.hostname", Constantes.IP_SERVEUR);
				Registry registry = LocateRegistry.getRegistry(Constantes.IP_SERVEUR, Constantes.REGISTRY_PORT);
				System.out.println(registry);
				remoteReference = (DispatcherInterface) Naming.lookup("rmi://"+Constantes.IP_SERVEUR+":"+Constantes.REGISTRY_PORT+"/"+Constantes.REGISTRY_NAME);
				remoteReferenceChat = (ChatRemoteInterface) Naming.lookup("rmi://"+Constantes.IP_SERVEUR+":"+Constantes.REGISTRY_PORT+"/"+Constantes.REGISTRY_NAME_CHAT);
				enregistrerClient();
				
//				remoteReference = (DispatcherInterface) registry.lookup(Constantes.REGISTRY_NAME);
				System.out.println(remoteReference);
				System.out.println("Connexion établie, normalement.");
				
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Erreur de connexion au serveur RMI. Passage en mode Hors Ligne.");
			Constantes.MODE_ONLINE=false;
		}
	}

	@Override
	public void initStatesList(GameContainer container) {
		addState(new Exploration(Constantes.GAMEPLAY_MAP_STATE));
		addState(new Menu(Constantes.MENU_MAP_STATE));
		addState(new Combat(Constantes.COMBAT_STATE));
	}
	
	public static void updateListeJoueur() throws RemoteException{
		listePaquetJoueurs = remoteReference.updateListe(player.getId(), player.getMapId());
	}

	public static void main(String[] argv) { 

		try{
			AppGameContainer container = new AppGameContainer(new MainGame()); 
			container.setVSync(true);
			container.setAlwaysRender(true);
			container.setDisplayMode(640,480,false); 
			container.setTargetFrameRate(60);
			container.start(); 

		} catch (SlickException e) { 
			e.printStackTrace(); 
		}
	}
	
	
	public static void initialisationJoueur(){
		player = new Player("Joueur", "BlackGuard.png", x, y, 133, 133, 133, 134);
		listePaquetJoueurs = new ArrayList<Player>();
		if (Constantes.MODE_ONLINE){
			try {
				Callbacker espoir = new Callbacker(player);
				UnicastRemoteObject.exportObject(espoir, 0);
				remoteReference.inscription(player, espoir); //impossible d'unicast player?! WTF§§
				//je veux que player agisse normalement côté serveur
				//et qu'une methode se fasse côté client donc j'l'exporte 2 fois.. 
				listePaquetJoueurs = remoteReference.updateListe(player.getId(), player.getMapId());
			} catch (RemoteException e) {
				e.printStackTrace();
				System.out.println("Erreur : le serveur du jeu ne répond pas (probablement car pas executé ou que l'objet est sur une adresse inaccessible) mais un RMI répond lawl. \nPassage en mode Hors Ligne.");
				Constantes.MODE_ONLINE=false;
			}
		}
	}


	public static Player getPlayer() {
		return player;
	}

	
	public static DispatcherInterface getRemoteReference() {
		return remoteReference;
	} 
	
	public static ArrayList<Player> getListePaquetJoueurs() {
		return listePaquetJoueurs;
	}

	public static void setListePaquetJoueurs(ArrayList<Player> listePaquetJoueurs) {
		MainGame.listePaquetJoueurs = listePaquetJoueurs;
	}

	// JS 
	public void testJavaScript(){
		try{
		java.security.AccessController.doPrivileged(
			new java.security.PrivilegedAction<Object>() {
				@Override
				public Object run() {
					try{
						System.out.println("Salut ! On m'a appelé via du javascript !");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					return null;

				};

			}
		);

		} catch (Exception ex) {
		ex.printStackTrace();
		}
	}
	
	public void equiperArmure(String id){
		int intid = Integer.parseInt(id);
		player.getInventaire().equiperArmure(new Armure(intid));
	}
	
	public void desequiperArmure(){
		player.getInventaire().desequiperArmure();
	}
	
	public String voirInventaire(){
		if(player.getInventaire().countObservers()==0){
			player.getInventaire().addObserver(this);
			System.out.println("Inventaire observé");
		}
		return player.getInventaire().toStringHTML();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// si on est bien dans une applet
		if(this.getContainer() instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) this.getContainer()).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("voirInventaire", null);
		}
	}

	
	// CHAT
	
	@Override
	public void afficheMsg(String message, String user){
		if(this.getContainer() instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) this.getContainer()).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("ajoutMsg", new String[] { user, message, "general"});
		}
	}
	
	private void enregistrerClient() {
		try {
			UnicastRemoteObject.exportObject(this, 0);
			remoteReferenceChat.addClient(this);
		}
		catch (RemoteException e) {
			System.out.println("Remote exception: " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("General exception: " +
			e.getClass().getName() + ": " + e.getMessage());
			System.exit(1);
		}
	}
	
	public void goMsg(String m){
		final String g = m;
		final ChatReceiverInterface client = this;
		java.security.AccessController.doPrivileged(
				new java.security.PrivilegedAction<Object>() {
			@Override
			public Object run() {
				try {
					System.out.println("goMsg appelé : "+g);
					remoteReferenceChat.getMessage(g, client);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}
	
	public static void inviterAuGroupe(Player invite){
		try {
			if(remoteReference.inviterJoueur(player, invite))
				System.out.println("Invitation envoyée à "+invite.getNom()+" !");
			else {
				String erreur = "L'invitation n'a pas été envoyée : ";
				if(invite.getGroupe()!=null)
					erreur+="Le joueur possède déjà un groupe";
				else if(invite.containsInvitation(invite.getGroupe()))
					erreur+="vous avez déjà invité ce joueur";
				System.out.println(erreur);
			}
		} catch (RemoteException e) {
			System.out.println("Erreur invitation");
			e.printStackTrace();
		}
	}
	
	public static void accepterInvitation(){
		try {
			remoteReference.accepterInvitation(player.getGroupe().getLeader(), MainGame.getPlayer());
		} catch (RemoteException e) {
			System.out.println("Erreur invitation");
			e.printStackTrace();
		}
	}
	
	public static void disbandGroup(UID groupeID){
		if(player.equals(player.getGroupe().getLeader()))
			try {
				remoteReference.disbandGroup(groupeID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}
	
	public static ArrayList<Player> getJoueursDuGroupe(){
		ArrayList<Player> res = new ArrayList<Player>();
		for (Player p : Exploration.getListeJoueurLoc())
			if(p.getGroupe().getID().equals(player.getGroupe().getID()))
				res.add(p);
		return res;
	}
	
}
