package combats;

import java.io.Serializable;

import org.newdawn.slick.Animation;

public abstract class Combattant implements Serializable {
	protected String nom;
	protected int pvMax, pvCourant, attaque, vitesse;
	protected int xCombat, yCombat;
	protected boolean enAttaque;
	protected transient Animation sprite;
	
	
	public Combattant(){
		this.enAttaque = true;
	}
	
	public Combattant(String nom, int pvMax, int pvCourant, int attaque,
			int vitesse) {
		this.nom = nom;
		this.pvMax = pvMax;
		this.pvCourant = pvCourant;
		this.attaque = attaque;
		this.vitesse = vitesse;
		this.enAttaque = true;
	}

	/**
	 * @param Combattant
	 * @return le nombre de dégats infligés
	 */
	public int attaquer(Combattant c){
		System.out.println("\n"+ nom +" attaque "+c.getNom()+" !");
		int degatInflige = (int) (getAttaque() + (getAttaque() * (Math.random() -0.5) * 0.30));
		int pvFinaux = c.getPvCourant()-degatInflige;
		System.out.println(c.getNom()+" perd "+degatInflige+" pv!");
		c.setPvCourant(pvFinaux);
		
		if(c.getPvCourant() <= 0){
			System.out.println("Il est KO! OH MON DIEU!");
			return degatInflige+c.getPvCourant();
		}
		else{
			System.out.println("Il ne lui reste plus que "+pvFinaux+" pv.");
			return degatInflige;
		}
	}
	
	/**
	 * Attaquer avec des dégats prédéfinies (online)
	 */
	public int attaquer(Combattant c, int degatsInflige){
		System.out.println("\n"+ nom +" attaque "+c.getNom()+" !");
		int pvFinaux = c.getPvCourant()-degatsInflige;
		System.out.println(c.getNom()+" perd "+degatsInflige+" pv!");
		c.setPvCourant(pvFinaux);
		
		if(c.getPvCourant() <= 0){
			System.out.println("Il est KO! OH MON DIEU!");
			return degatsInflige+c.getPvCourant();
		}
		else{
			System.out.println("Il ne lui reste plus que "+pvFinaux+" pv.");
			return degatsInflige;
		}
	}
	
	public boolean estEnVie(){
		return this.getPvCourant() > 0;
	}
	
	/**
	 * Déplace un combattant lorsqu'il attaque
	 * @return true quand le combattant a terminé son déplacement.
	 */
	public abstract boolean deplacementAttaque(int delta, int departX, int destinationX);

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

	public int getXCombat() {
		return xCombat;
	}

	public void setXCombat(int xCombat) {
		this.xCombat = xCombat;
	}

	public int getYCombat() {
		return yCombat;
	}

	public void setYCombat(int yCombat) {
		this.yCombat = yCombat;
	}

	/**
	 * Nécessaire de mettre ça dans une méthode à part pour
	 * recréer localement les animations.
	 */
	public abstract void initAnimation();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combattant other = (Combattant) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
	public boolean isKO(){
		return pvCourant<=0;
	}
	
}
