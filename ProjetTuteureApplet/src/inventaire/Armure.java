package inventaire;

public class Armure extends Objet{
	private static final long serialVersionUID = 7562172149069766571L;

	public Armure(int id) {
		super(id);
		switch(id){
			case 0 :
				nom = "Vêtements normaux";
				valeur = 0;
			break;
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
