package gui;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;


/**
 * Juste une classe de test à la base.
 * Mais au final on pourrait utiliser ça pour les AFK donc je laisse pour le moment.
 *
 */
public class Menu extends BasicGameState{
	private int stateID;

	public Menu(){
	}

	public Menu(int stateID){
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
	throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//effet de pause
		game.getState(0).render(container, game, g);
		g.setColor(new Color(0f,0f,0f,0.5f));
		g.fillRect(0,0, container.getScreenWidth(), container.getScreenHeight()); 
		g.setColor(Color.white);
		
		g.drawString("Reprendre", 20, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			game.enterState(Constantes.GAMEPLAY_MAP_STATE);
		}

	}

}
