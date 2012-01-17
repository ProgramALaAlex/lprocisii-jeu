package game;

import ingame.Exploration;
import gui.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;



public class MainGame extends StateBasedGame{

	
	
	public MainGame() {
		super("Projet Tuteuré");
	}

	public void initStatesList(GameContainer container) {
		addState(new Exploration(Constantes.GAMEPLAY_MAP_STATE));
		addState(new Menu(Constantes.MENU_MAP_STATE));
	}

	public static void main(String[] argv) { 
		try { 
			AppGameContainer container = new AppGameContainer(new MainGame()); 
			container.setDisplayMode(640,480,false); 
			container.start(); 
		} catch (SlickException e) { 
			e.printStackTrace(); 
		} 
	} 

}
