package inventaire;

/**
 * olbigé d'utiliser ça car pas de set() dans Integer pour l'hashmap.
 * impossible de definir une classe interne à inventaire car c'est une hashmap qui l'utilise
 * en fait utiliser une hashmap c'est surement pas le mieux
 *
 */
public class Nombre{
	private int nombre;
	private boolean equipe = false;

	public Nombre(){
	}

	public Nombre(int nombre){
		this.nombre = nombre;
	}

	public void incrementer(){
		this.nombre++;
	}
	
	public void decrementer(){
		if(nombre>0)
			nombre--;
	}
	
	public boolean isEmpty(){
		return nombre==0;
	}

	public boolean isEquipe() {
		return equipe;
	}

	public void setEquipe(boolean equipe) {
		this.equipe = equipe;
	}

	// pour le debug, a virer apres;
	public int getNombre() {
		return nombre;
	}
	
	
	
}
