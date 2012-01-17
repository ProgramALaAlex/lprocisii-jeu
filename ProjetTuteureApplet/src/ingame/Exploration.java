package ingame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;
import constantes.TPList;

import gui.Menu;


public class Exploration extends BasicGameState{
	private int stateID;
	private static Map currMap;
	private static Player player;
	private float x = 250f, y = 330f;
	
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
		player = new Player("Ark", "BlackGuard.png", x, y, currMap);
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		int playerX = (int) player.getX();
		int playerY = (int) player.getY();
		int resolutionWidth = container.getWidth();
		int resolutionHeight = container.getHeight();
		
		//scroll
		Scrolling.scrollBottomLayer(playerX, playerY, resolutionWidth, resolutionHeight, currMap);
		Scrolling.scrollPlayer(playerX, playerY, resolutionWidth, resolutionHeight, player, currMap);
		Scrolling.scrollTopLayer(playerX, playerY, resolutionWidth, resolutionHeight, currMap);

		
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
		
		
		// juste pour tester la téléportation
		for(Teleporter tp : currMap.getListeTeleporter()){
			if (tp.contains(player.getX()+16, player.getY()+16)){
				System.out.println("ok, on change de map là.");
				currMap = new Map(tp.getIdMapDestination(), tp.isSafe());
				player.setX(tp.getDestinationX());
				player.setY(tp.getDestinationY());
			}
		}
		
		
		// menu de pause (inutile mais pour tester les gamestates)
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			game.enterState(Constantes.MENU_MAP_STATE);
//			a.enterState(Constantes.MENU_MAP_STATE);
//			System.out.println("SALUT");
		}
		
		//utilisé pour le debug
		if(input.isKeyPressed(Input.KEY_I)){
			System.out.println("x:"+input.getAbsoluteMouseX()+" y:"+input.getAbsoluteMouseY());
			System.out.println("x:"+input.getMouseX()+" y:"+input.getMouseY());
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
