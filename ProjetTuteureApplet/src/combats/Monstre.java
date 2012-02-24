package combats;

import inventaire.Arme;
import inventaire.Objet;
import inventaire.Potion;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import constantes.Constantes;

public class Monstre extends Combattant{
	private HashMap<Objet, Integer> dropList;

	/**
	 * Pas de paramètres : généré aléatoirement entre deux modèles.
	 */
	public Monstre() {
		super();
		dropList = new HashMap<Objet, Integer>();

		if(Math.random() < 0.5){
			nom = "Slime bleue";
			pvMax = 500;
			pvCourant = 500;
			attaque = 10;
			vitesse = 120;
			dropList.put(new Potion(), 100); // == 100% de chance de dropper des potions
			
		}
		else {
			nom = "Slime verte";
			pvMax = 300;
			pvCourant = 300;
			attaque = 15;
			vitesse = 115;
			dropList.put(new Arme(1), 50);
		}
		initAnimation();
	}
	
	public ArrayList<Objet> drop(){
		ArrayList<Objet> res = new ArrayList<Objet>();
		int random = (int) (Math.random()*100);
		for(Objet o : this.dropList.keySet())
			if(random <= this.dropList.get(o)){
				System.out.println("objet droppé ! C'est un(e)"+o.getNom());
				res.add(o);
			}
		if(res.isEmpty())
			System.out.println("pas d'objet droppé...");
		return res;
	}

	@Override
	public boolean deplacementAttaque(int delta, int departX, int destinationX) {
		if(enAttaque  && xCombat <= destinationX){
			xCombat+=0.5f*delta;
		}
		if(xCombat >= destinationX){
			enAttaque = false;
			xCombat-=0.3f*delta;
		}
		if(!enAttaque && xCombat <= destinationX){
			xCombat-=0.3f*delta;
		}
		if(!enAttaque && xCombat <= departX){
			enAttaque = true;
			return true;
		}
		return false;
	}

	public void initAnimation(){
		sprite = new Animation();
		if(nom.equals("Slime bleue"))
			try {
				sprite.addFrame(new Image(Constantes.MONSTER_LOCATION+"SlimeB.png"), Constantes.EVENT_ANIM_DEFAUT_DURATION);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		else try {
			sprite.addFrame(new Image(Constantes.MONSTER_LOCATION+"SlimeV.png"), Constantes.EVENT_ANIM_DEFAUT_DURATION);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
