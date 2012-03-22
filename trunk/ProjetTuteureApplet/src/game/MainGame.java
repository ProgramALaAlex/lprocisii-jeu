package game;

import inventaire.Arme;
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

import netscape.javascript.JSObject;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import rmi.interfaces.ChatReceiverInterface;
import rmi.interfaces.ChatRemoteInterface;
import rmi.interfaces.DispatcherInterface;

import combats.Combat;

import constantes.Constantes;
import exploration.Exploration;
import gui.Menu;



public class MainGame extends StateBasedGame implements Observer, ChatReceiverInterface{
	private static DispatcherInterface remoteReference;
	private static ChatRemoteInterface remoteReferenceChat;
	private static ArrayList<Player> listePaquetJoueurs;
	private static Player player;
	private static float x = 250f;
	private static float y = 330f;

	public static void accepterInvitation(){
		try {
			remoteReference.accepterInvitation(player.getGroupe().getLeader(), MainGame.getPlayer());
		} catch (RemoteException e) {
			System.out.println("Erreur invitation");
			e.printStackTrace();
		}
	}

	public static String afficherListeInvitationHTML(){
		return player.listeInvitationHTML();
	}

	public static String afficherListeJoueurMapHTML(){
		String res = "";

		if(player.getGroupe()!=null){
			res += "Groupe : <strong>"+player.getGroupe().getNom()+"</strong><br/>";
			if(!player.getGroupe().getLeader().equals(player)){
				res+="Leader : <span class=\"joueurGroupe\">"+player.getGroupe().getLeader().getNom()+"</span> <a href='#' onclick='creerGroupe()' return false;'>[Partir du groupe]</a>";
			} else res+="Vous êtes le leader. <br/> <a href='#' onclick='creerGroupe()' return false;'>[Dissoudre]</a>" ;
		}
		else { 
			res+= "Vous n'appartenez à aucun groupe";
			res+= "<input id='nomGroupe' name='groupe' type='text' size='15' maxlength='20' placeholder='Nom du groupe' /><input name='creer' type='button' onclick='creerGroupe(document.getElementById(\"nomGroupe\").value)' value='Créer groupe'/>";
		}

		if(Exploration.getListeJoueurLoc().size()>1){
			res+="<br/>Autres joueurs présents dans la map : <ul>";

			for(int i=0; i<Exploration.getListeJoueurLoc().size(); i++){
				Player p = Exploration.getListeJoueurLoc().get(i);
				if(!p.equals(player)){
				res+="<li>";
					if(p.getGroupe()!=null && !p.equals(player)){
						if(p.getGroupe().equals(player.getGroupe()))
							res+="<span class=\"joueurGroupe\">"+p.getNom()+"</span>";
						else
							res+="<span class=\"joueurAutreGroupe\">"+p.getNom()+"</span>";
					}
					else res+=p.getNom();
				if(player.getGroupe() != null && player.getGroupe().getLeader().equals(player) && p.getGroupe()==null && !p.estInvitePar(player.getGroupe()))
					res+=" <a href='#' onclick='inviter("+i+"); return false;'>[Inviter]</a>";
				else if(player.getGroupe()!=null && p.getGroupe()== null && p.estInvitePar(player.getGroupe()))
					res+=" [invité]";
				res+="</li>";
				}
			}
			res+="</ul>";
		}
		return res;
	}

	public static void disbandGroup(UID groupeID){
		if(player.getGroupe()!=null && player.equals(player.getGroupe().getLeader()))
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
	
	public static ArrayList<Player> getListePaquetJoueurs() {
		return listePaquetJoueurs;
	}


	public static Player getPlayer() {
		return player;
	}


	public static DispatcherInterface getRemoteReference() {
		return remoteReference;
	} 

	public static void initialisationJoueur(){
		player = new Player(0, "Joueur", "homme", x, y, 133, 133, 133, 134);
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

	public static void initialisationJoueur(int BDD_ID, String nomJoueur, String sprite, int x, int y, int hpMax, int hpCourant, int attaque, int vitesse){
		player = new Player(BDD_ID, nomJoueur, sprite, x, y, hpMax, hpCourant, attaque, vitesse);
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

	public static void inviterAuGroupe(final Player invite){
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							try {
								if(remoteReference.inviterJoueur(player, invite)){
									System.out.println("Invitation envoyée à "+invite.getNom()+" !");
									invite.addInvitation(player.getGroupe());
									updateListeJoueur();
								}
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
							return null;
						}
					});
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	
	public static void inviterAuGroupeByID(final String id){
		final int intid = Integer.parseInt(id);
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							inviterAuGroupe(Exploration.getListeJoueurLoc().get(intid));
							return null;
						}
					});
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public static void main(String[] argv) { 

		try{
			AppGameContainer container = new AppGameContainer(new MainGame()); 
			container.setVSync(true);
			container.setAlwaysRender(true);
			container.setDisplayMode(640,480,false); 
			container.setTargetFrameRate(60);
			container.setShowFPS(false);
			container.start(); 
		} catch (SlickException e) { 
			e.printStackTrace(); 
		}
	}

	public static void setListePaquetJoueurs(ArrayList<Player> listePaquetJoueurs) {
		MainGame.listePaquetJoueurs = listePaquetJoueurs;
	}

	public static void updateListeJoueur() throws RemoteException{
		listePaquetJoueurs = remoteReference.updateListe(player.getId(), player.getMapId());
	}

	/**
	 * Appelle la fonciton JS qui rafraichit la liste des joueurs
	 * @param container
	 */
	public static void updateListHTML(GameContainer container) {
		if(container instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) container).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("voirListeJoueurs", null);
		}
	}


	// CHAT

	public static void updateListInvitHTML(GameContainer container) {
		if(container instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) container).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("voirListeInvitations", null);
		}
	}

	public MainGame() {
		super("Projet Tuteuré");
		initialisationRMI();
	}

	private void initialisationRMI() {
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
	public void afficheMsg(String message, String user){
		if(this.getContainer() instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) this.getContainer()).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("ajoutMsg", new String[] { user, message, "general"});
		}
	}

	public void creerGroupe(final String nomGroupe){
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							player.toggleCreationGroupe(nomGroupe);
							return null;
						}
					});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void desequiperArme(){
		player.getInventaire().desequiperArme();
	}

	public void desequiperArmure(){
		player.getInventaire().desequiperArmure();
		try {
			MainGame.getRemoteReference().equiperArmure(player, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
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

	public void equiperArme(String id){
		int intid = Integer.parseInt(id);
		player.getInventaire().equiperArme(new Arme(intid));
	}

	public void equiperArmure(String id){
		int intid = Integer.parseInt(id);
		player.getInventaire().equiperArmure(new Armure(intid));
		player.initAnimation();
		try {
			MainGame.getRemoteReference().equiperArmure(player, intid);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void goMsg(String m){
		final String g = m;
		final ChatReceiverInterface client = this;
		try{
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	@Override
	public void initStatesList(GameContainer container) {
		addState(new Exploration(Constantes.GAMEPLAY_MAP_STATE));
		addState(new Menu(Constantes.MENU_MAP_STATE));
		addState(new Combat(Constantes.COMBAT_STATE));
	}
	
	public void refuserGroupe(final int id){
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							UID gid = player.refuserInvitation(id);
							try {
								remoteReference.refuserInvitation(gid, player);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
							return null;
						}
					});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void rejoindreGroupe(final int id){
		try{
			java.security.AccessController.doPrivileged(
					new java.security.PrivilegedAction<Object>() {
						@Override
						public Object run() {
							player.accepterInvitation(id);
							return null;
						}
					});
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
	
	public String voirInventaire(){
		if(player.getInventaire().countObservers()==0){
			player.getInventaire().addObserver(this);
			System.out.println("Inventaire observé");
		}
		return player.getInventaire().toStringHTML();
	}

}
