package ingame;

import java.awt.Point;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import constantes.Constantes;


public class Player extends Combattant{
	private String nom, spriteSheetName;
	private int pvCourant, pvMax, attaque, vitesse;
	private float x, y, vx, vy;
	private Polygon collision;
	private SpriteSheet spriteSheet;
	private Animation up, down, left, right;
	private Map map;
	private int pasAvantProchainCombat;
	private int directionHistorique;

	

	public Player(String nom, String spriteSheetName, float x, float y, Map map, int pvMax, int pvCourant, int attaque, int vitesse){
		super(nom, pvMax, pvCourant, attaque, vitesse);
		this.spriteSheetName = spriteSheetName;
		this.x = x;
		this.y = y;
		this.map = map;
		this.pvMax = pvMax;
		this.pvCourant = pvCourant;
		this.attaque = attaque;
		try {
			spriteSheet = new SpriteSheet(Constantes.CHAR_LOCATION+spriteSheetName, 31, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}	
		collision = new Polygon(new float[]{
				x,y,
				x+21,y,
				x+21,y+22,
				x,y+22
		});
		collision.setLocation(x+5, y+5);
		initAnimation(false);
		sprite = right; 
		pasAvantProchainCombat = (int) (Math.random()*500);

	}

	public void initAnimation(boolean autoUpdate){
		Image [] lookDown = {spriteSheet.getSprite(0, 0), spriteSheet.getSprite(1, 0), spriteSheet.getSprite(2, 0), spriteSheet.getSprite(1, 0)};
		Image [] lookLeft = {spriteSheet.getSprite(0, 1), spriteSheet.getSprite(1, 1), spriteSheet.getSprite(2, 1), spriteSheet.getSprite(1, 1)};
		Image [] lookRight = {spriteSheet.getSprite(0, 2), spriteSheet.getSprite(1, 2), spriteSheet.getSprite(2, 2), spriteSheet.getSprite(1, 2)};
		Image [] lookUp = {spriteSheet.getSprite(0, 3), spriteSheet.getSprite(1, 3), spriteSheet.getSprite(2, 3), spriteSheet.getSprite(1, 3)};

		up = new Animation(lookUp, Constantes.EVENT_ANIM_DEFAUT_DURATION, autoUpdate);
		down = new Animation(lookDown, Constantes.EVENT_ANIM_DEFAUT_DURATION, autoUpdate);
		left = new Animation(lookLeft, Constantes.EVENT_ANIM_DEFAUT_DURATION, autoUpdate);
		right = new Animation(lookRight, Constantes.EVENT_ANIM_DEFAUT_DURATION, autoUpdate); 
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		
		// Gestion des combats.
		if(!map.isSafe() && pasAvantProchainCombat==0){
			game.enterState(Constantes.COMBAT_STATE);
			pasAvantProchainCombat = (int) (Math.random()*500);
		}	

		Input input = container.getInput();
		
		// pas trouvé d'autres moyen pour garder l'historique, hyper crade
		finCombat();
		
		if (input.isKeyDown(Input.KEY_UP)){
			sprite = up;
			directionHistorique = Constantes.HAUT;
			if (!isBlocked(0, - delta * 0.1f)){
				sprite.update(delta);
				y -= delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN)){
			directionHistorique = Constantes.BAS;
			sprite = down;
			if (!isBlocked(0, delta * 0.1f)){
				sprite.update(delta);
				y += delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_LEFT)){
			directionHistorique = Constantes.GAUCHE;
			sprite = left;
			if (!isBlocked(- delta * 0.1f, 0)){
				sprite.update(delta);
				x -= delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT)){
			directionHistorique = Constantes.DROITE;
			sprite = right;
			if (!isBlocked(delta * 0.1f, 0)){
				sprite.update(delta);
				x += delta * 0.1f;
				decrementerPas(delta);
			}
		}
		
		
		// DEBUG
		if (input.isKeyDown(Input.KEY_P)){
			game.enterState(Constantes.COMBAT_STATE);
			pasAvantProchainCombat = (int) (Math.random()*500);
		}
		
		// si on appuit sur rien : on met l'animation 1
		if (!((input.isKeyDown(Input.KEY_UP))
				|| (input.isKeyDown(Input.KEY_DOWN))
				|| (input.isKeyDown(Input.KEY_LEFT))
				|| (input.isKeyDown(Input.KEY_RIGHT))))
			sprite.setCurrentFrame(1);
		else{
			// sinon, on rafraichit les collisions
			collision.setLocation(x+5, y+5);
		}
		
	}
	
	/**
	 * Methode d'update des combats
	 */
	public void updateCombat(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		sprite=left;
		sprite.update(delta);
		
	}

	/**
	 * Pour qu'à la fin du combat, le joueur regarde dans l'ancienne direction
	 * On est obligé d'utiliser directHistorique, car sprite n'est pas Clonable
	 */
	public void finCombat(){
		switch(directionHistorique){
		case Constantes.BAS : sprite = down; break;
		case Constantes.DROITE : sprite = right; break;
		case Constantes.HAUT : sprite = up; break;
		case Constantes.GAUCHE : sprite = left; break;
		}
	}

	private boolean isBlocked(float x, float y){
		for (Block b : map.getEntities()) {
			Polygon tmp = collision.copy();
			tmp.setX(tmp.getX()+x);
			tmp.setY(tmp.getY()+y);
			if (tmp.intersects(b.poly)) {
				return true;
			}       
		}
		return false;
	}

	/**
	 * Appelé lorsque le joueur se déplace, pour rafraichir les collisions
	 * et décrémenter le temps pour le prochain combat.
	 */
	public void decrementerPas(int delta){
		if(!map.isSafe() && pasAvantProchainCombat>0)
			pasAvantProchainCombat--;
	}
	
	public Animation getSprite(){
		return sprite;
	}
	
	public Polygon getCollision(){
		return collision;
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}


}
