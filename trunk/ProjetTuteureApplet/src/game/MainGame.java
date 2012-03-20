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
import java.util.Collections;
import java.util.Comparator;
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
			if (Constantes.ONLINE){
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
			Constantes.ONLINE=false;
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
		//		player.setPvCourant(10);
		listePaquetJoueurs = new ArrayList<Player>();
		if (Constantes.ONLINE){
			try {
				Callbacker espoir = new Callbacker(player);
				UnicastRemoteObject.exportObject(espoir, 0);
				remoteReference.inscription(player, espoir); 
				listePaquetJoueurs = remoteReference.updateListe(player.getId(), player.getMapId());
			} catch (RemoteException e) {
				e.printStackTrace();
				System.out.println("Erreur : le serveur du jeu ne répond pas (probablement car pas executé ou que l'objet est sur une adresse inaccessible) mais un RMI répond lawl. \nPassage en mode Hors Ligne.");
				Constantes.ONLINE=false;
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

	/**
	 * MVC
	 */
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

	public static void inviterAuGroupeByID(final String id){
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							int intid = Integer.parseInt(id);
							inviterAuGroupe(Exploration.getListeJoueurLoc().get(intid));
							return null;
						}
					});
		}
		catch(Exception ex){
			ex.printStackTrace();
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
			if(p.getGroupe() != null && player.getGroupe() != null && p.getGroupe().getID().equals(player.getGroupe().getID()))
				res.add(p);
		return res;
	}

	public static String afficherListeJoueurMapHTML(){
		String res = "";

		if(player.getGroupe()!=null){
			res += player.getGroupe().getNom()+"<br/>";
			if(!player.getGroupe().getLeader().equals(player))
				res+="Leader : <span class=\"joueurGroupe\">"+player.getGroupe().getLeader().getNom()+"</span>";
		}
		else res+= "Vous n'appartenez à aucun groupe";

		if(Exploration.getListeJoueurLoc().size()>1){
			res+="<br/>Autres joueurs présents dans la map : <ul>";

			for(int i=0; i<Exploration.getListeJoueurLoc().size(); i++){
				Player p = Exploration.getListeJoueurLoc().get(i);
				res+="<li>";
				if(!p.equals(player)){
					if(p.getGroupe()!=null)
						if(p.getGroupe().equals(player.getGroupe()))
							res+="<span class=\"joueurGroupe\">"+p.getNom()+"</span>";
						else
							res+="<span class=\"joueurAutreGroupe\">"+p.getNom()+"</span>";
					else res+=p.getNom();
				}
				if(player.getGroupe() != null && player.getGroupe().getLeader().equals(player) && p.getGroupe()==null && !p.estInvitePar(player.getGroupe()))
					res+=" <a href='#' onclick='inviter("+i+"); return false;'>[Inviter]</a>";
				else if(player.getGroupe()!=null && p.getGroupe()== null && p.estInvitePar(player.getGroupe()))
					res+=" [invité]";
				res+="</li>";
			}
			res+="</ul>";
		}
		return res;
	}

}
