package game;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ingame.Combat;
import ingame.Exploration;
import gui.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import rmi.interfaces.DispatcherInterface;

import constantes.Constantes;



public class MainGame extends StateBasedGame{
	private static DispatcherInterface remoteReference;


	public MainGame() {
		super("Projet Tuteuré");
		try { 
			if (Constantes.MODE_ONLINE){
				System.out.println("A");
				System.setProperty("java.security.policy","applet.policy");
				System.out.println("B");
				System.setSecurityManager(new RMISecurityManager());
				System.out.println("C");
				Registry registry = LocateRegistry.getRegistry("localhost");
				System.out.println(registry);
				remoteReference = (DispatcherInterface) registry.lookup(DispatcherInterface.REGISTRY_NAME);
				System.out.println("Connexion établie, normalement.");
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Erreur de connexion, sûrement. Passage en mode Hors Ligne.");
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
			container.setDisplayMode(640,480,false); 
			container.start(); 


		} catch (SlickException e) { 
			e.printStackTrace(); 
		}
	}

	public static DispatcherInterface getRemoteReference() {
		return remoteReference;
	} 

}
