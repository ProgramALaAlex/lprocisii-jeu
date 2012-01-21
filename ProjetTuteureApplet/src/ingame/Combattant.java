package ingame;

import org.newdawn.slick.Animation;

public class Combattant {
	protected String nom;
	protected int pvMax, pvCourant, attaque, vitesse;
	protected Animation sprite;
	
	
	public Combattant(){}
	
	public Combattant(String nom, int pvMax, int pvCourant, int attaque,
			int vitesse) {
		super();
		this.nom = nom;
		this.pvMax = pvMax;
		this.pvCourant = pvCourant;
		this.attaque = attaque;
		this.vitesse = vitesse;
	}

	/**
	 * @param Combattant
	 * @return true si l'ennemi est KO
	 */
	public boolean attaquer(Combattant c){
		System.out.println("\n"+ nom +" attaque "+c.getNom()+" !");
		int degatInflige = (int) (getAttaque() + (getAttaque() * (Math.random() -0.5) * 0.30));
		int pvFinaux = c.getPvCourant()-degatInflige;
		System.out.println(c.getNom()+" perd "+degatInflige+" pv!");
		c.setPvCourant(pvFinaux);
		
		if(c.getPvCourant() <= 0){
			System.out.println("Il est KO! OH MON DIEU!");
			return true;
		}
		else{
			System.out.println("Il ne lui reste plus que "+pvFinaux+" pv.");
			return false;
		}
	}

	public int getPvCourant() {
		return pvCourant;
	}


	public void setPvCourant(int pvCourant) {
		this.pvCourant = pvCourant;
	}


	public String getNom() {
		return nom;
	}


	public int getPvMax() {
		return pvMax;
	}


	public int getAttaque() {
		return attaque;
	}


	public int getVitesse() {
		return vitesse;
	}

	public Animation getSprite() {
		return sprite;
	}

	
	
}
