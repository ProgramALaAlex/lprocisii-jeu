package exploration;

import java.rmi.RemoteException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;
import game.MainGame;


public class Exploration extends BasicGameState{
	private int stateID;
	private static Map currMap;
	
	public Exploration(int id){
		this.stateID = id;
	}

	public Exploration(){
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		container.setVSync(true);
		setCurrMap(new Map("01", true));
		MainGame.initialisationJoueur();
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		int playerX = (int) MainGame.getPlayer().getX();
		int playerY = (int) MainGame.getPlayer().getY();
		int resolutionWidth = container.getWidth();
		int resolutionHeight = container.getHeight();
		
		//scroll
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, getCurrMap(), 1 );
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, getCurrMap(), 2 );
		Scrolling.scrollPlayer(g, playerX, playerY, resolutionWidth, resolutionHeight, MainGame.getPlayer(), getCurrMap());
		
		//afficher les autres joueurs en ligne
		if (Constantes.MODE_ONLINE){
			if (!MainGame.getListePaquetJoueurs().isEmpty())
				for(Player p : MainGame.getListePaquetJoueurs()){
					Scrolling.scrollOtherPlayers(g, p, (int)MainGame.getPlayer().getX(), (int)MainGame.getPlayer().getY(), resolutionWidth, resolutionHeight, getCurrMap());
				}
		}
		
		Scrolling.scrollLayer(playerX, playerY, resolutionWidth, resolutionHeight, getCurrMap(), 3);

		
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
		
		//DEBUG
		//		Affiche la hitbox du joueur
//				g.draw(player.getCollision());
		//		Afficher les collisions du terrain
//				for(Rectangle p : currMap.getCollision())
//					g.draw(p);
		//	Afficher les TP
//		for(Teleporter tp : currMap.getListeTeleporter())
//			g.draw(tp);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		MainGame.getPlayer().update(container, game, delta);
		
		//TODO faire un vrai dispatcher : rappeler le serveur toutes les deux secondes c'est inutile en fait
		if (Constantes.MODE_ONLINE){
			try {
					MainGame.updateListeJoueur();
					MainGame.getRemoteReference().updatePosition(MainGame.getPlayer());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for(Teleporter tp : getCurrMap().getListeTeleporter()){
			if (tp.contains(MainGame.getPlayer().getX()+16, MainGame.getPlayer().getY()+16)){
				setCurrMap(new Map(tp.getIdMapDestination(), tp.isSafe()));
				MainGame.getPlayer().setX(tp.getDestinationX());
				MainGame.getPlayer().setY(tp.getDestinationY());
				MainGame.getPlayer().setMapId(tp.getIdMapDestination());
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
	}
	
}
