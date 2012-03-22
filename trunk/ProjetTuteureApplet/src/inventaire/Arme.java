package inventaire;

public class Arme extends Objet {

	public Arme(int id) {
		super(id);
		switch(id){
			case 1 :
				nom = "épée légère";
				valeur = 25;
				break;
			case 2 :
				nom = "épée sacrée";
				valeur = 130;
				break;
		}
	}

}
