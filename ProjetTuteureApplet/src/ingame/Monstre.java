package ingame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import constantes.Constantes;

public class Monstre extends Combattant{

	/**
	 * Pas de paramètres : généré aléatoirement entre deux modèles.
	 */
	public Monstre() {
		sprite = new Animation();
		if(Math.random() < 0.5){
			nom = "Slime bleue";
			pvMax = 500;
			pvCourant = 500;
			attaque = 10;
			vitesse = 120;
			try {
				sprite.addFrame(new Image(Constantes.MONSTER_LOCATION+"SlimeB.png"), Constantes.EVENT_ANIM_DEFAUT_DURATION);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
		}
		else {
			nom = "Slime verte";
			pvMax = 300;
			pvCourant = 300;
			attaque = 15;
			vitesse = 115;
			try {
				sprite.addFrame(new Image(Constantes.MONSTER_LOCATION+"SlimeV.png"), Constantes.EVENT_ANIM_DEFAUT_DURATION);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
			
			
	}

}
