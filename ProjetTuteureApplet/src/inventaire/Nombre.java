package inventaire;

import java.io.Serializable;

public class Nombre implements Serializable{
	private static final long serialVersionUID = -3736967118943713072L;
	private int nombre;
	private boolean equipe = false;

	public Nombre(){
	}

	public Nombre(int nombre){
		this.nombre = nombre;
	}

	public void decrementer(){
		if(nombre>0)
			nombre--;
	}
	
	public int getNombre() {
		return nombre;
	}
	
	public void incrementer(){
		this.nombre++;
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
	
	
	
}
