package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;

public class GUIValeursCombats {
	private int degatsX, transparence;
	private float degatsY; //float pour translation lente
	private String degats;
	private int temps;
	private boolean afficher;
	private int couleur;

	public GUIValeursCombats(int degatsX, int degatsY, String degats) {
		super();
		this.degatsX = degatsX;
		this.degatsY = degatsY;
		this.degats = degats;
		this.temps = 0;
		this.transparence = 255;
		this.afficher = true;
		this.couleur = 0;
	}
	
	/**
	 * @param mode : le mode
	 * 1 : rouge (dégâts normaux)
	 * 2 : vert (soins)
	 * 3 : gris (drops)
	 */
	public GUIValeursCombats(int degatsX, int degatsY, String degats, int mode) {
		super();
		this.couleur = mode;
		if(mode == 3){
			this.degatsY = degatsY-20;
			this.temps = -400;
		}
		else {
			this.temps = 0;
			this.degatsY = degatsY;
		}
		this.degatsX = degatsX;
		this.degats = degats;
		this.transparence=255;
		this.afficher = true;
	}
	
	public int getDegatsX() {
		return degatsX;
	}

	public float getDegatsY() {
		return degatsY;
	}

	public String getDegats() {
		return degats;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g){
		if(afficher){
			g.setColor(new Color(0, 0, 0, transparence));
			g.drawString(degats, degatsX, degatsY+1);
			//rouge si dégats, vert si soin
			if(couleur==2)
				g.setColor(new Color(0, 255, 0, transparence));
			else if(couleur==3)
				g.setColor(new Color(200, 200, 200, transparence));
			else g.setColor(new Color(255, 050, 0, transparence));
			g.drawString(degats, degatsX, degatsY);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(this.temps <= Constantes.TEMPS_AFFICHAGE_DEGATS){
			temps+=1*delta;
			degatsY-=0.03*delta;
			if(this.temps > Constantes.TEMPS_AFFICHAGE_DEGATS/2){
				this.transparence-=0.4*delta;
			}
		}
		else afficher = false;
	}

	public boolean isAffiche() {
		return afficher;
	}
	
	

}
