package exploration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;
import org.newdawn.slick.util.pathfinding.PathFinder;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import constantes.Constantes;
import game.MainGame;
import game.Player;


public class Exploration extends BasicGameState{
	private int stateID;
	private static Map currMap;
	private static ArrayList<Player> listeJoueurLoc;
	private OnlineUpdateThread onlineUpdateThread;

	private static PathFinder finder;
	private Path path;
	private int compteurChemin=1;
	private float clicX, clicY;
	
	public Exploration(int id){
		this.stateID = id;
	}

	public Exploration(){
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		container.setVSync(true);
		setCurrMap(new Map("01"));
		MainGame.initialisationJoueur();
		finder = new AStarPathFinder(getCurrMap(), 500, false);
		listeJoueurLoc = new ArrayList<Player>();
		
		if (Constantes.ONLINE){
			try {
				MainGame.updateListeJoueur();
				MainGame.getRemoteReference().updatePosition(MainGame.getPlayer());
				//on copie la liste des joueurs en local pour pour interpoler leurs mouvements apres
				listeJoueurLoc = new ArrayList<Player>(MainGame.getListePaquetJoueurs());
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
			g.setColor(Color.black);
			g.drawString("Map non dangereuse", 10, 24);
			g.setColor(Color.green);
			g.drawString("Map non dangereuse", 10, 23);
		}
		else {
			g.setColor(Color.black);
			g.drawString("Map dangereuse", 10, 24);
			g.setColor(Color.red);
			g.drawString("Map dangereuse", 10, 23);

		}

		g.setColor(Color.black);

		int t=50;
		if(Constantes.ONLINE){
		for(Player p : listeJoueurLoc)
			g.drawString(p.toString()+":"+p.getMapId(), 10, t+=20);
		g.setColor(Color.white);
		t=49;
		for(Player p : listeJoueurLoc)
			g.drawString(p.toString()+":"+p.getMapId(), 10, t+=20);
		}
		//DEBUG
		//		Affiche la hitbox du joueur
		//						g.draw(MainGame.getPlayer().getCollision());
		//		Afficher les collisions du terrain
		//						for(Rectangle p : currMap.getCollision())
		//							g.draw(p);
		//	Afficher les TP
		//		for(Teleporter tp : currMap.getListeTeleporter())
		//			g.draw(tp);
		// affiche les différents poitns

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
					local.synchroniserStats(p);

				}
				else {
					listeJoueurLoc.add(p);
					listeJoueurLoc.get(listeJoueurLoc.indexOf(p)).initAnimation(); //obligé de faire ça à cause du transient
				}
			}
			// si la liste locale contient un joueur qui n'est pas dans la liste updaté = il est soit déco, soit dans une autre map
			for (Iterator<Player> iterator = listeJoueurLoc.iterator(); iterator
					.hasNext();) {
				Player p = (Player) iterator.next();
				if(!p.equals(MainGame.getPlayer()) && !MainGame.getListePaquetJoueurs().contains(p)){
					if(p.getGroupe()!=null && p.getGroupe().getLeader().equals(p))
						for(Player p2 : listeJoueurLoc)
							p2.getGroupe().remove(0);
					iterator.remove();
				}
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


	@Override
	public int getID() {
		return 0;
	}

	public static void setBlockMap(Map blockMap){
		setCurrMap(blockMap);
	}

	public static Map getCurrMap() {
		return currMap;
	}

	public static void setCurrMap(Map currMap) {
		Exploration.currMap = currMap;
		finder = new AStarPathFinder(getCurrMap(), 500, false);
	}

	public static ArrayList<Player> getListeJoueurLoc() {
		return listeJoueurLoc;
	}

}
