package inventaire;

public class Arme extends Objet {

	public Arme(int id) {
		super(id);
		switch(id){
			case 1 :
				nom = "épée 1";
				valeur = 15;
				break;
			case 2 :
				nom = "épée 2";
				valeur = 20;
				break;
		}
	}

}
