package game;

import inventaire.Arme;
import inventaire.Armure;

import java.applet.Applet;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

import rmi.interfaces.DispatcherInterface;

import constantes.Constantes;



public class MainGame extends StateBasedGame implements Observer{
	private static DispatcherInterface remoteReference;


	public MainGame() {
		super("Projet Tuteur�");
		try { 
			if (Constantes.MODE_ONLINE){
				System.out.println("Connexion en cours...");
				System.setProperty("java.rmi.server.hostname", Constantes.IP_SERVEUR);
				Registry registry = LocateRegistry.getRegistry(Constantes.IP_SERVEUR, Constantes.REGISTRY_PORT);
				System.out.println(registry);
				remoteReference = (DispatcherInterface) Naming.lookup("rmi://"+Constantes.IP_SERVEUR+":"+Constantes.REGISTRY_PORT+"/"+Constantes.REGISTRY_NAME);
//				remoteReference = (DispatcherInterface) registry.lookup(Constantes.REGISTRY_NAME);
				System.out.println(remoteReference);
				System.out.println("Connexion �tablie, normalement.");
				
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Erreur de connexion au serveur RMI. Passage en mode Hors Ligne.");
			Constantes.MODE_ONLINE=false;
		}
	}

	public void initStatesList(GameContainer container) {
		addState(new Exploration(Constantes.GAMEPLAY_MAP_STATE));
		addState(new Menu(Constantes.MENU_MAP_STATE));
		addState(new Combat(Constantes.COMBAT_STATE));
	}

	public static void main(String[] argv) { 

		try{
			AppGameContainer container = new AppGameContainer(new MainGame()); 
			container.setVSync(true);
			container.setAlwaysRender(true);
			container.setDisplayMode(640,480,false); 
			
			
			container.start(); 

		} catch (SlickException e) { 
			e.printStackTrace(); 
		}
	}

	
	
	public static DispatcherInterface getRemoteReference() {
		return remoteReference;
	} 
	
	public void testJavaScript(){
		try{
		java.security.AccessController.doPrivileged(
			new java.security.PrivilegedAction<Object>() {
				public Object run() {
					try{
						System.out.println("Salut ! On m'a appel� via du javascript !");
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
		Exploration.getPlayer().getInventaire().equiperArmure(new Armure(intid));
	}
	
	public void desequiperArmure(){
		Exploration.getPlayer().getInventaire().desequiperArmure();
	}
	
	public String voirInventaire(){
		if(Exploration.getPlayer().getInventaire().countObservers()==0){
			Exploration.getPlayer().getInventaire().addObserver(this);
			System.out.println("Inventaire observ�");
		}
		return Exploration.getPlayer().getInventaire().toStringHTML();
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

}
