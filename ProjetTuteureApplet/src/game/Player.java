package game;

import inventaire.Armure;
import inventaire.Inventaire;
import inventaire.Potion;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Mover;

import combats.Combattant;

import rmi.interfaces.ReceiverInterface;

import constantes.Constantes;
import exploration.Exploration;
import exploration.Map;

// exploration étant le mode "principal", je met player là dedans.
public class Player extends Combattant implements Mover {
	private String mapName, spriteSheetName;
	private int pasAvantProchainCombat, directionHistorique;
	private float x, y, vx, vy;
	private transient Polygon collision;
	private transient SpriteSheet spriteSheet;
	private transient Animation up, down, left, right;
	private Inventaire inventaire;
	//ONLINE
	private UID userId; //pour le online : id unique
	private Groupe groupe;
	private LinkedList<Groupe> listeInvitation = new LinkedList<Groupe>();

	//TEST TODO
	private boolean deplacementAuto = false;
	private transient StateBasedGame game; //MOCHE

	public Player(String nom, String spriteSheetName, float x, float y, int pvMax, int pvCourant, int attaque, int vitesse){
		super(nom, pvMax, pvCourant, attaque, vitesse);
		this.spriteSheetName = spriteSheetName;
		this.x = x;
		this.y = y;
		this.mapName = Exploration.getCurrMap().getIDMap();
		this.pvMax = pvMax;
		this.pvCourant = pvCourant;
		this.attaque = attaque;

		collision = new Polygon(new float[]{
				x,y,
				x+21,y,
				x+21,y+22,
				x,y+22
		});
		collision.setLocation(x+5, y+5);
		initAnimation();
		directionHistorique = Constantes.DROITE;
		pasAvantProchainCombat = (int) (Math.random()*500);
		inventaire = new Inventaire();
		inventaire.addObjet(new Potion());
		inventaire.addObjet(new Armure(1));
		inventaire.equiperArmure(new Armure(1));
		userId = new UID();
	}

	@Override
	public void initAnimation(){
		try {
			spriteSheet = new SpriteSheet(Constantes.CHAR_LOCATION+spriteSheetName, 31, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}	
		Image [] lookDown = {spriteSheet.getSprite(0, 0), spriteSheet.getSprite(1, 0), spriteSheet.getSprite(2, 0), spriteSheet.getSprite(1, 0)};
		Image [] lookLeft = {spriteSheet.getSprite(0, 1), spriteSheet.getSprite(1, 1), spriteSheet.getSprite(2, 1), spriteSheet.getSprite(1, 1)};
		Image [] lookRight = {spriteSheet.getSprite(0, 2), spriteSheet.getSprite(1, 2), spriteSheet.getSprite(2, 2), spriteSheet.getSprite(1, 2)};
		Image [] lookUp = {spriteSheet.getSprite(0, 3), spriteSheet.getSprite(1, 3), spriteSheet.getSprite(2, 3), spriteSheet.getSprite(1, 3)};

		up = new Animation(lookUp, Constantes.EVENT_ANIM_DEFAUT_DURATION, false);
		down = new Animation(lookDown, Constantes.EVENT_ANIM_DEFAUT_DURATION, false);
		left = new Animation(lookLeft, Constantes.EVENT_ANIM_DEFAUT_DURATION, false);
		right = new Animation(lookRight, Constantes.EVENT_ANIM_DEFAUT_DURATION, false);

		sprite = right;
	}
	

	public void demarrerCombat(){
		game.enterState(Constantes.COMBAT_STATE);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		this.game = game; //MOCHE
		
		Input input = container.getInput();
		//si on est pas dans un groupe ou si on est leader
		if(this.groupe==null || (groupe.getLeader().equals(this))){

			// Gestion des combats.
			if(!Exploration.getCurrMap().isSafe() && pasAvantProchainCombat==0){
				game.enterState(Constantes.COMBAT_STATE);
				pasAvantProchainCombat = (int) (Math.random()*500);
			}	


			collision.setLocation(x+5, y+5);
			finCombat();
			if(!deplacementAuto){
				if (input.isKeyDown(Input.KEY_UP)){
					allerEnHaut(delta);
				}
				if (input.isKeyDown(Input.KEY_DOWN)){
					allerEnBas(delta);
				}
				if (input.isKeyDown(Input.KEY_LEFT)){
					allerAGauche(delta);
				}
				if (input.isKeyDown(Input.KEY_RIGHT)){
					allerADroite(delta);
				}

				// si on appuit sur rien et qu'on bouge pas : on met l'animation 1
				if (!((input.isKeyDown(Input.KEY_UP))
						|| (input.isKeyDown(Input.KEY_DOWN))
						|| (input.isKeyDown(Input.KEY_LEFT))
						|| (input.isKeyDown(Input.KEY_RIGHT))
						|| !deplacementAuto))
					sprite.setCurrentFrame(1);

				// debug : création d'un groupe 
				if (input.isKeyPressed(Input.KEY_G)){
					if(groupe==null){
						listeInvitation.clear();
						groupe = new Groupe(this);
					}
					else {
						MainGame.disbandGroup(groupe.getID());
					}
				}

				//accepter invitation (rejoindre groupe)
				if (input.isKeyPressed(Input.KEY_O)){
					if(groupe==null && !listeInvitation.isEmpty()){
						accepterInvitation();
					}
				}

				//décliner une invitation
				if (input.isKeyPressed(Input.KEY_N)){
					if(groupe==null && !listeInvitation.isEmpty()){
						listeInvitation.pop();
					}
				}
				
				//si le leader a changé de map, on retire l'invitation
				for (Iterator iterator = listeInvitation.iterator(); iterator
						.hasNext();) {
					Groupe g = (Groupe) iterator.next();
					if(g.getLeader().equals(this)){
						iterator.remove();
						System.out.println("le leader est dans une map différente : invitation de "+g.getNom()+ " retirée");
					}
				}

				// debug : générer combat
				if (input.isKeyPressed(Input.KEY_P)){
					game.enterState(Constantes.COMBAT_STATE);
					pasAvantProchainCombat = (int) (Math.random()*500);
				}
			}
		}
		//si on est membre d'un groupe, on suit le membre devant nous (leader si on est 2eme, 2eme si on est 3eme..)
		else {
			for(Player p : Exploration.getListeJoueurLoc())
				if(p.getId().equals(groupe.getLeader().getId())){
					if(p.getMapId().equals(this.getMapId()))
						allerVers(false, (float)p.getPosDerriere(this).getX(), (float)p.getPosDerriere(this).getY(), delta);
					//si le leader a changé de map, on se téléporte sur lui
					else if (!p.getMapId().equals(this.getMapId())){
						Exploration.setCurrMap(new Map(p.getMapId()));
						this.setX(p.getX());
						this.setY(p.getY());
						this.setMapId(p.getMapId());
					}
				}
			if (input.isKeyPressed(Input.KEY_G)){
				groupe=null;
			}
		}
	}

	/**
	 * pour qu'un joueur suive un perso
	 * @param le joueur qui va se placer derrière
	 */
	public Point getPosDerriere(Player p){
		int position=1;
		if(this.groupe!=null)
			position = this.groupe.getPosition(p);
		Point res = new Point();
		switch (getDirection()){
		case Constantes.GAUCHE:
			res.setLocation(getX()+Constantes.TAILLE_CARRE_COLLISION*position, getY());
			break;
		case Constantes.DROITE:
			res.setLocation(getX()-Constantes.TAILLE_CARRE_COLLISION*position, getY());
			break;
		case Constantes.HAUT:
			res.setLocation(getX(), getY()+Constantes.TAILLE_CARRE_COLLISION*position);
			break;
		case Constantes.BAS:
			res.setLocation(getX(), getY()-Constantes.TAILLE_CARRE_COLLISION*position);
			break;
		}
		return res;
	}

	private void allerADroite(int delta) {
		directionHistorique = Constantes.DROITE;
		sprite = right;
		if (!isBlocked(delta * Constantes.VITESSE_DEPLACEMENT, 0)){
			sprite.update(delta);
			x += delta * Constantes.VITESSE_DEPLACEMENT;
			decrementerPas(delta);
		}

	}

	private void allerAGauche(int delta) {
		directionHistorique = Constantes.GAUCHE;
		sprite = left;
		if (!isBlocked(- delta * Constantes.VITESSE_DEPLACEMENT, 0)){
			sprite.update(delta);
			x -= delta * Constantes.VITESSE_DEPLACEMENT;
			decrementerPas(delta);
		}
	}

	private void allerEnBas(int delta) {
		directionHistorique = Constantes.BAS;
		sprite = down;
		if (!isBlocked(0, delta * Constantes.VITESSE_DEPLACEMENT)){
			sprite.update(delta);
			y += delta * Constantes.VITESSE_DEPLACEMENT;
			decrementerPas(delta);
		}
	}

	private void allerEnHaut(int delta) {
		sprite = up;
		directionHistorique = Constantes.HAUT;
		if (!isBlocked(0, - delta * Constantes.VITESSE_DEPLACEMENT)){
			sprite.update(delta);
			y -= delta * 0.1f;
			decrementerPas(delta);
		}
	}

	/**
	 * Va vers un point donné en ligne droite (avec respect des collisions)
	 * @return true si arrivé
	 */
	public boolean allerVers(boolean clic, float destX, float destY, int delta){
		//si on clic et qu'on est pas dans un groupe ou si on est leader
		if(!clic || this.groupe==null || (groupe.getLeader().equals(this))){
			deplacementAuto = true;
			int posX = Math.round(this.getX());
			int posY = Math.round(this.getY());

			boolean horizontal = posX > destX-5 && posX < destX+5;
			boolean vertical = posY > destY-5 && posY < destY+5;
			if(horizontal && vertical){
				deplacementAuto = false;
				return true;
			}
			// si pas aligné horizontalement
			if(!horizontal){
				if(posX  > destX){
					allerAGauche(delta);
				}
				if(posX < destX){
					allerADroite(delta);
				}
			}
			if(!vertical){
				if(posY > destY){
					allerEnHaut(delta);
				}
				if(posY < destY){
					allerEnBas(delta);
				}
			}
		}
		return false;

	}

	public void render(Graphics g, int x, int y){
		String nom = getNom();
		if(groupe!=null && groupe.getLeader().equals(this))
			nom+="(L)";
		int decalage = g.getFont().getWidth(nom)/2-Constantes.TAILLE_CARRE_COLLISION/2;
		sprite.draw(x, y, 32, 32);
		g.setColor(new Color(33, 33, 33, 70));

		for(int i=-1; i<2; i++){
			g.drawString(nom, x-decalage, y+30+i);
			g.drawString(nom, x+i-decalage, y+31);
		}
		// joueur sans groupe
		if(groupe==null)
			g.setColor(Color.white);
		// si les joueurs sont du même groupe
		else if(MainGame.getPlayer().getGroupe()!=null &&  this.getGroupe().getID().equals(MainGame.getPlayer().getGroupe().getID())) 
			g.setColor(new Color(100,255,100));
		// si les joueurs appartiennent à un autre groupe
		else 
			g.setColor(Color.yellow);
		g.drawString(nom, x-decalage, y+30);
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
		if(collision!=null){
			for (Rectangle r : Exploration.getCurrMap().getCollision()) {
				Polygon tmp = collision.copy();
				tmp.setX(tmp.getX()+x);
				tmp.setY(tmp.getY()+y);
				if (tmp.intersects(r)) {
					return true;
				}       
			}
		}
		return false;
	}

	/**
	 * Appelé lorsque le joueur se déplace, pour rafraichir les collisions
	 * et décrémenter le temps pour le prochain combat.
	 */
	public void decrementerPas(int delta){
		if(!Exploration.getCurrMap().isSafe() && pasAvantProchainCombat>0)
			pasAvantProchainCombat--;
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

	public int utiliserPotion(){
		int soin = this.inventaire.retirerPotion();
		if(soin==0) System.out.println("Pas de potion!");
		else System.out.println(getNom()+" utilise une potion!");
		if(getPvCourant()+soin <= getPvMax()){
			setPvCourant(getPvCourant()+soin);
			return soin;
		}
		else {
			int pvSoigne = getPvMax() - getPvCourant();
			setPvCourant(getPvMax());
			return  pvSoigne;
		}
	}
	
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

	public UID getId() {
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

	public Point getPosition() {
		return new Point((int)this.getX(), (int)this.getY());
	}

	public void setPosition(Point position) {
		this.setX((float) position.getX());
		this.setY((float) position.getY());
	}

	public void setDirection(int direction) {
		this.directionHistorique = direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	/**
	 * Lorsqu'un joueur reçoit une invitation
	 * La vérification se fait côté serveur
	 */
	public void addInvitation(Groupe g) throws RemoteException {
		System.out.println("invitation pour rejoindre le groupe \""+g.getNom()+"\" reçue !");
		System.out.println("O pour accepter, N pour décliner.");
		listeInvitation.add(g);
	}

	public boolean containsInvitation(Groupe g) {
		return listeInvitation.contains(g);
	}

	private void accepterInvitation(){
		groupe = listeInvitation.getFirst();
		groupe.add(userId);
		listeInvitation.clear();
		MainGame.accepterInvitation();
		System.out.println("Vous avez accepté l'invitation");
	}

	/**
	 * Mettre à false lors du changement de map
	 */
	public void setDeplacementAuto(boolean deplacementAuto) {
		this.deplacementAuto = deplacementAuto;
	}
	
	public boolean estLeaderDUnGroupeNonVide(){
		return (this.groupe!=null && this.groupe.getLeader().equals(this) && this.groupe.size()>1);
	}

	public boolean possedeUnGroupeNonVide(){
		return(this.groupe!=null && this.groupe.size()>1);
	}
	
	public void synchroniserStats(Player p){
		this.groupe = p.getGroupe();
		this.mapName = p.getMapId();
		this.pvCourant = p.getPvCourant();
	}

}
