package ingame;

import inventaire.Armure;
import inventaire.Inventaire;
import inventaire.Potion;

import java.awt.Point;
import java.io.Serializable;
import java.rmi.server.UID;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import rmi.interfaces.DispatcherInterface;
import rmi.interfaces.ReceiverInterface;
import rmi.paquetJoueur.PaquetJoueur;

import constantes.Constantes;


public class Player extends Combattant implements ReceiverInterface, Serializable {
	private String nom, mapName, spriteSheetName;
	private int pvCourant, pvMax, attaque, vitesse, pasAvantProchainCombat, directionHistorique;
	private float x, y, vx, vy;
	private transient Polygon collision;
	private transient SpriteSheet spriteSheet;
	private transient Animation up, down, left, right;
	private transient Map map;
	private transient Inventaire inventaire;
	private UID userId; //pour le online

	public Player(String nom, String spriteSheetName, float x, float y, Map map, int pvMax, int pvCourant, int attaque, int vitesse){
		super(nom, pvMax, pvCourant, attaque, vitesse);
		this.spriteSheetName = spriteSheetName;
		this.x = x;
		this.y = y;
		this.map = map;
		this.mapName = map.getIDMap();
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
		inventaire = new Inventaire();
		inventaire.addObjet(new Potion());
		inventaire.addObjet(new Armure(1));
		inventaire.equiperArmure(new Armure(1));
		System.out.println("Armure 1 équipée");
		this.userId = new UID();
	}
	
	/**
	 * Constructeur utilisé pour reconstruire les autres joueurs en ligne, donc pas complet pour éco bande passante
	 */
	public Player(PaquetJoueur p){
		this.spriteSheetName = p.getSpriteSheetName();
		this.x = (int)p.getPosition().getX();
		this.y = (int)p.getPosition().getY();
		this.directionHistorique = p.getDirection();
		try {
			spriteSheet = new SpriteSheet(Constantes.CHAR_LOCATION+spriteSheetName, 31, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}	
		initAnimation(false);
		sprite = right;
		finCombat();
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
			if (!isBlocked(0, - delta * Constantes.VITESSE_DEPLACEMENT)){
				sprite.update(delta);
				y -= delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_DOWN)){
			directionHistorique = Constantes.BAS;
			sprite = down;
			if (!isBlocked(0, delta * Constantes.VITESSE_DEPLACEMENT)){
				sprite.update(delta);
				y += delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_LEFT)){
			directionHistorique = Constantes.GAUCHE;
			sprite = left;
			if (!isBlocked(- delta * Constantes.VITESSE_DEPLACEMENT, 0)){
				sprite.update(delta);
				x -= delta * 0.1f;
				decrementerPas(delta);
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT)){
			directionHistorique = Constantes.DROITE;
			sprite = right;
			if (!isBlocked(delta * Constantes.VITESSE_DEPLACEMENT, 0)){
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

	public void utiliserPotion(){
		int soin = this.inventaire.retirerPotion();
		if(soin==0) System.out.println("Pas de potion!");
		else System.out.println(getNom()+" utilise une potion !");
		if(getPvCourant()+soin <= pvMax)
			setPvCourant(getPvCourant()+soin);
		else setPvCourant(pvMax);
	}

	// juste pour le debug, a virer plus tard
	public Inventaire getInventaire() {
		return inventaire;
	}

	@Override
	public int getPvMax() {
		return super.getPvMax() + this.inventaire.getPVBonus();
	}

	@Override
	public int getAttaque() {
		return super.getAttaque() + this.inventaire.getAttaqueBonus();
	}

	@Override
	public boolean deplacementAttaque(int delta, int departX, int destinationX) {
		if(enAttaque  && xCombat >= destinationX){
			xCombat-=0.5f*delta;
		}
		if(xCombat <= destinationX){
			enAttaque = false;
			xCombat+=0.3f*delta;
		}
		if(!enAttaque && xCombat >= destinationX){
			xCombat+=0.3f*delta;
		}
		if(!enAttaque && xCombat >= departX){
			enAttaque = true;
			return true;
		}
		return false;
	}

	public UID getUserId() {
		return userId;
	}

	// online
	public String getSpriteSheetName() {
		return spriteSheetName;
	}

	// online
	public int getDirection() {
		return directionHistorique;
	}

	public String getMapId() {
		return mapName;
	}

	public void setMapId(String mapId) {
		this.mapName = mapId;
	}
	
	
	

	
	
}
