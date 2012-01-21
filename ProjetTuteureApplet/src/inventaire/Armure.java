package inventaire;

public class Armure extends Objet{
	public Armure(int id) {
		super(id);
		switch(id){
			case 1 :
				nom = "armure 1";
				valeur = 25;
				break;
			case 2 :
				nom = "armure 2";
				valeur = 50;
				break;
		}
	}
}
