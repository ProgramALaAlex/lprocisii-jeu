package inventaire;

import java.io.Serializable;

public abstract class Objet implements Serializable {
	protected String nom;
	protected int id;
	protected int valeur; //la valeur (pv soigné, attaque ajouté...)
	
	public Objet(){}
	
	public Objet(int id){
		this.id = id;
	}
	
	public Objet(int id, String nom, int valeur) {
		super();
		this.nom = nom;
		this.valeur = valeur;
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Objet){
			return (((Objet)obj).getValeur()==this.valeur);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return valeur;
	}

	public String getNom() {
		return nom;
	}

	public int getValeur() {
		return valeur;
	}
	
	public int getId(){
		return id;
	}
	
}
