package exploration;

import game.AppletGameContainer;
import game.MainGame;
import game.Player;

import java.applet.Applet;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import rmi.serveur.beans.JoueurBean;
import constantes.Constantes;


public class Exploration extends BasicGameState{
	public static Map getCurrMap() {
		return currMap;
	}
	public static Vector<Player> getListeJoueurLoc() {
		return listeJoueurLoc;
	}
	//bricolage mais pas le choix
	public static void newSkin(Player p){
		updateur = p;
	}
	public static void setBlockMap(Map blockMap){
		setCurrMap(blockMap);
	}

	public static void setCurrMap(Map currMap) {
		Exploration.currMap = currMap;
		finder = new AStarPathFinder(getCurrMap(), 500, false);
	}
	@SuppressWarnings("unused")
	private int stateID;
	private static Map currMap;
	private static Vector<Player> listeJoueurLoc;
	private OnlineUpdateThread onlineUpdateThread;
	
	private static PathFinder finder;

	private Path path;

	private int compteurChemin=1;


	private float clicX, clicY;

	private static Player updateur;


	public Exploration(){
	}

	public Exploration(int id){
		this.stateID = id;
	}
	
	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		container.setVSync(true);
		//connexion via BDD
		if(container instanceof AppletGameContainer.Container && Constantes.ONLINE){
			Applet applet = ((AppletGameContainer.Container) container).getApplet();
			try {
				System.out.println("Clef : "+applet.getParameter("clef"));
				int BDD_ID = MainGame.getRemoteReference().convertirClefEnID(applet.getParameter("clef"));
				System.out.println("Clef convertie. ID = "+BDD_ID);
				JoueurBean jb = MainGame.getRemoteReference().getJoueurByID(Integer.toString(BDD_ID));
				System.out.println("ID DE LA MAP : "+jb.getIdMap());
				setCurrMap(new Map("0"+jb.getIdMap()));
				String pseudo = jb.getPseudo();
				String sprite = MainGame.getRemoteReference().getJoueurMapByID(jb.getIdApparence());
				int x = jb.getDernierX();
				int y = jb.getDernierY();
				int hpMax = jb.getPvMax();
				int hpCourant = jb.getPvActuels();
				int attaque = (int) jb.getAttaque();
				int vitesse = (int) jb.getVitesse();
				MainGame.initialisationJoueur(BDD_ID, pseudo, sprite, x, y, hpMax, hpCourant, attaque, vitesse);
			} catch (RemoteException e) {
				e.printStackTrace();
			} 
		} else {
			setCurrMap(new Map("01"));
			MainGame.initialisationJoueur();
		}
		finder = new AStarPathFinder(getCurrMap(), 500, false);
		listeJoueurLoc = new Vector<Player>();
		
		if (Constantes.ONLINE){
			try {
				MainGame.updateListeJoueur();
				MainGame.getRemoteReference().updatePosition(MainGame.getPlayer());
				//on copie la liste des joueurs en local pour pour interpoler leurs mouvements apres
				listeJoueurLoc = new Vector<Player>(MainGame.getListePaquetJoueurs());
				if(!listeJoueurLoc.isEmpty())
					//On recréé les spritesheet en local
					for(Player p : listeJoueurLoc){
						p.initAnimation();
					}
				onlineUpdateThread = new OnlineUpdateThread(listeJoueurLoc);
				onlineUpdateThread.start();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		listeJoueurLoc.add(MainGame.getPlayer());
		MainGame.updateListHTML(container);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		int resolutionWidth = container.getWidth();
		int resolutionHeight = container.getHeight();

		//scroll
		Scrolling.scrollLayer(resolutionWidth, resolutionHeight, 1);
		Scrolling.scrollLayer(resolutionWidth, resolutionHeight, 2);

		if(!Constantes.ONLINE)
			Scrolling.scrollPlayer(g, resolutionWidth, resolutionHeight);
		else {
			if (listeJoueurLoc!=null && !listeJoueurLoc.isEmpty())
				for(Player p : listeJoueurLoc)
					if(p.getMapId().equals(MainGame.getPlayer().getMapId()))
						Scrolling.scrollOtherPlayers(g, p, resolutionWidth, resolutionHeight);
		}

		Scrolling.scrollLayer(resolutionWidth, resolutionHeight, 3);


		//HUD
		if(getCurrMap().isSafe()){
			g.setColor(new Color(0, 65, 0));
			g.drawString("Zone non dangereuse", 10, 9);
			g.drawString("Zone non dangereuse", 10, 8);
			g.setColor(Color.white);
			g.drawString("Zone non dangereuse", 10, 8);
		}
		else {
			g.setColor(Color.black);
			g.drawString("Zone dangereuse", 10, 9);
			g.drawString("Zone dangereuse", 10, 8);
			g.setColor(Color.red);
			g.drawString("Zone dangereuse", 10, 8);
		}

		g.setColor(Color.black);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput(); 
		MainGame.getPlayer().update(container, game, delta);

		if(Constantes.ONLINE){
			listeJoueurLoc = onlineUpdateThread.getListeJoueurLoc();

			//on trie la liste de joueur de façon à afficher par la suite d'abord les plus hauts
			Collections.sort(listeJoueurLoc, new Comparator<Player>() {
				@Override
				public int compare(Player p1, Player p2) {
					return (Float.compare(p1.getY(), p2.getY()));
				}
			});
			
			boolean change=false;
			for(Player p : MainGame.getListePaquetJoueurs()){
				if(listeJoueurLoc.contains(p) && (!p.equals(MainGame.getPlayer()))){
					Player local = listeJoueurLoc.get(listeJoueurLoc.indexOf(p));
					// changement de map
					if(!local.getMapId().equals(p.getMapId())){
						local.setX(p.getX());
						local.setY(p.getY());
					}

					//si la position a changé, on déplace le joueur vers cette nouvelle position
					if(local.getX() != p.getX() || local.getY() != p.getY())
							local.allerVers(p.getX(), p.getY(), delta);

					//synchronisation..
					local.synchroniserStats(p, container);

				}
				else {
					listeJoueurLoc.add(p);
					listeJoueurLoc.get(listeJoueurLoc.indexOf(p)).initAnimation(); //obligé de faire ça à cause du transient
					change=true;
				}
			}
			// si la liste locale contient un joueur qui n'est pas dans la liste updaté = il est soit déco, soit dans une autre map
			for (Iterator<Player> iterator = listeJoueurLoc.iterator(); iterator
					.hasNext();) {
				Player p = iterator.next();
				if(!p.equals(MainGame.getPlayer()) && !MainGame.getListePaquetJoueurs().contains(p)){
					if(p.getGroupe()!=null && p.getGroupe().getLeader().equals(p))
						for(Player p2 : listeJoueurLoc)
							if(!p2.equals(p) && p2.getGroupe()!=null && p2.getGroupe().getLeader().equals(p))
								p2.getGroupe().remove(0);
					iterator.remove();
					change=true;
				}
			}
			if(change)
				MainGame.updateListHTML(container);
			
			if(updateur!=null){
				updateur.initAnimation();
			}
		}


		// changement de map si sur teleporteur
		for(Teleporter tp : getCurrMap().getListeTeleporter()){
			if (tp.contains(MainGame.getPlayer().getX()+16, MainGame.getPlayer().getY()+16)){
				setCurrMap(new Map(tp.getIdMapDestination()));
				MainGame.getPlayer().setX(tp.getDestinationX());
				MainGame.getPlayer().setY(tp.getDestinationY());
				MainGame.getPlayer().setMapId(tp.getIdMapDestination());
				path=null;
				MainGame.getPlayer().setDeplacementAuto(false);
			}
		}


		// menu de pause (inutile mais pour tester les gamestates)
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			game.enterState(Constantes.MENU_MAP_STATE);
		}

		//utilisé pour le debug
		if(input.isKeyPressed(Input.KEY_L)){
			System.out.println("x:"+input.getAbsoluteMouseX()+" y:"+input.getAbsoluteMouseY());
			System.out.println("x:"+input.getMouseX()+" y:"+input.getMouseY());
		}

		if(input.isKeyPressed(Input.KEY_I)){
			System.out.println(MainGame.getPlayer().getInventaire());
		}

		//déplacement automatique en cas de clic
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			clicX = (int) (input.getMouseX()-Scrolling.getDecalage(container.getWidth(), container.getHeight()).getX());
			clicY = (int) (input.getMouseY()-Scrolling.getDecalage(container.getWidth(), container.getHeight()).getY());
			System.out.println("x:"+clicX+", y:"+clicY);
			path = finder.findPath(MainGame.getPlayer(), (int)MainGame.getPlayer().getX()/Constantes.BLOCK_SIZE, (int)MainGame.getPlayer().getY()/Constantes.BLOCK_SIZE, (int)clicX/Constantes.BLOCK_SIZE, (int)clicY/Constantes.BLOCK_SIZE);
		}

		if(path!=null && clicX!=0 && clicY!=0){
			int pathSize = path.getLength();
			if(compteurChemin<pathSize-1){
				if (MainGame.getPlayer().allerVers(path.getX(compteurChemin)*Constantes.BLOCK_SIZE, path.getY(compteurChemin)*Constantes.BLOCK_SIZE, delta))
					compteurChemin+=2;
			}
			else if(compteurChemin<pathSize)
				if (MainGame.getPlayer().allerVers(path.getX(compteurChemin)*Constantes.BLOCK_SIZE, path.getY(compteurChemin)*Constantes.BLOCK_SIZE, delta))
					compteurChemin++;

			if(compteurChemin==pathSize){
				compteurChemin=1;
				path=null;
			}
		}

		// invitation à rejoindre un groupe via clique droit - temporaire
		if(Constantes.ONLINE){
			//si le joueur est leader d'un groupe
			if(MainGame.getPlayer().getGroupe()!=null && MainGame.getPlayer().getGroupe().getLeader().equals(MainGame.getPlayer())){
				if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
					Player selectionne = null;
					clicX = (int) (input.getMouseX()-Scrolling.getDecalage(container.getWidth(), container.getHeight()).getX());
					clicY = (int) (input.getMouseY()-Scrolling.getDecalage(container.getWidth(), container.getHeight()).getY());
					for (Player p : listeJoueurLoc){
						if(!p.equals(MainGame.getPlayer())){
							Rectangle r = new Rectangle(p.getX(), p.getY(), Constantes.BLOCK_SIZE, Constantes.BLOCK_SIZE);
							if(r.contains(clicX, clicY)){
								selectionne = p;
								break;
							}
						}
					}
					if(selectionne!=null)
						MainGame.inviterAuGroupe(selectionne);
				}
			}
		}
	}

}
