package ingame;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.jws.Oneway;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import rmi.paquetJoueur.PaquetJoueur;

import constantes.Constantes;
import constantes.TPList;

import game.MainGame;
import gui.Menu;


public class Exploration extends BasicGameState{
	private int stateID;
	private static Map currMap;
	private static Player player;
	private float x = 250f, y = 330f;
	
	//TEST ONLINE
	private ArrayList<PaquetJoueur> listePaquetJoueurs;
	
	
	public Exploration(int id){
		this.stateID = id;
	}

	public Exploration(){
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		container.setVSync(true);
		this.currMap = new Map("01", true);
		player = new Player("Ark", "BlackGuard.png", x, y, currMap, 133, 133, 133, 134);
		listePaquetJoueurs = new ArrayList<PaquetJoueur>();
		if (Constantes.MODE_ONLINE){
			try {
				MainGame.getRemoteReference().inscription(player);
				listePaquetJoueurs = MainGame.getRemoteReference().updateListe(player.getUserId(), player.getMapId());
			} catch (RemoteException e) {
				e.printStackTrace();
				System.out.println("Erreur : le serveur du jeu ne répond pas (probablement car pas executé) mais un RMI semble être allumé lawl. \n Passage en mode Hors Ligne.");
				Constantes.MODE_ONLINE=false;
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		int playerX = (int) player.getX();
		int playerY = (int) player.getY();
		int resolutionWidth = container.getWidth();
		int resolutionHeight = container.getHeight();
		
		//scroll
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, currMap, 1 );
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, currMap, 2 );
		Scrolling.scrollPlayer(playerX, playerY, resolutionWidth, resolutionHeight, player, currMap);
		
		//afficher les autres joueurs en ligne
		if (Constantes.MODE_ONLINE){
			if (!this.listePaquetJoueurs.isEmpty())
				for(PaquetJoueur p : this.listePaquetJoueurs){
					Scrolling.scrollOtherPlayers(g, p, (int)player.getX(), (int)player.getY(), resolutionWidth, resolutionHeight, currMap);
				}
		}
		
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, currMap, 3);

		
		//HUD
		if(currMap.isSafe()){
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
		
		//DEBUG
		//		Affiche la hitbox du joueur
//				g.draw(player.getCollision());
		//		Afficher les collisions du terrain
//				for(Block p : currMap.getEntities())
//				p.draw(g);
		//	Afficher les TP
//		for(Teleporter tp : currMap.getListeTeleporter())
//			g.draw(tp);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		player.update(container, game, delta);
		
		if (Constantes.MODE_ONLINE){
			try {
					listePaquetJoueurs = MainGame.getRemoteReference().updateListe(player.getUserId(), player.getMapId());
					MainGame.getRemoteReference().updatePosition(new PaquetJoueur(player.getUserId(), new Point((int)player.getX(), (int)player.getY()), player.getDirection(), player.getSpriteSheetName(), player.getMapId()));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for(Teleporter tp : currMap.getListeTeleporter()){
			if (tp.contains(player.getX()+16, player.getY()+16)){
				currMap = new Map(tp.getIdMapDestination(), tp.isSafe());
				player.setX(tp.getDestinationX());
				player.setY(tp.getDestinationY());
				System.out.println("PUTAIN C "+tp.getIdMapDestination());
				player.setMapId(tp.getIdMapDestination());
				
				if(Constantes.MODE_ONLINE){
					//TODO
				}
			}
		}
		
		
		// menu de pause (inutile mais pour tester les gamestates)
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			game.enterState(Constantes.MENU_MAP_STATE);
//			a.enterState(Constantes.MENU_MAP_STATE);
//			System.out.println("SALUT");
		}
		
		//utilisé pour le debug
		if(input.isKeyPressed(Input.KEY_L)){
			System.out.println("x:"+input.getAbsoluteMouseX()+" y:"+input.getAbsoluteMouseY());
			System.out.println("x:"+input.getMouseX()+" y:"+input.getMouseY());
		}

		if(input.isKeyPressed(Input.KEY_I)){
			System.out.println(player.getInventaire());
		}
		
	}

	@Override
	public int getID() {
		return 0;
	}

	public static Player getPlayer(){
		return player;
	}
	
	public static void setBlockMap(Map blockMap){
		currMap = blockMap;
	}
	
}
