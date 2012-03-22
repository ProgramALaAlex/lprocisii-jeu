package inventaire;

public class Armure extends Objet{
	public Armure(int id) {
		super(id);
		switch(id){
			case 1 :
				nom = "armure légère";
				valeur = 55;
				break;
			case 2 :
				nom = "armure lourde";
				valeur = 150;
				break;
		}
	}
}
