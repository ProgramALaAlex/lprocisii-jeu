package combats;


import inventaire.Objet;

import java.applet.Applet;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import netscape.javascript.JSObject;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import constantes.Constantes;
import game.AppletGameContainer;
import game.MainGame;
import game.Player;
import gui.GUIValeursCombats;



public class Combat extends BasicGameState{
	private int stateID;
	private static ArrayList<Monstre> listeMonstre;

	private boolean attaquer;
	private static boolean selectionCible;
	private static boolean tourJoueur;
	private static boolean tourEnnemi;
	private static int definirCible;
	private static int tour;
	private static Combattant combattantPlusRapide;

	private static int attaqueJoueurX; // peut pas mettre dans constante car par rapport au bord..
	private static int destinationAttaqueJoueurX;
	private static boolean joueurAttaque;
	private Image background;

	private static GUIValeursCombats degatsDisplay;
	private static GUIValeursCombats dropDisplay;
	private static boolean combatDeGroupe;
	private int tailleDuGroupe;
	private static boolean attendreReponse, enTrainDeRepondre;
	private static int delta;

	private static Combattant cibleRecuTemp;
	private static int degatsRecuTemp;

	public Combat(int stateID){
		this.stateID = stateID;
	}

	public Combat(){
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image(Constantes.BATTLE_BACKGROUND_LOCATION+"battleBackground.jpg");
	}


	public static void setListeMonstre(ArrayList<Monstre> listeMonstre) {
		Combat.listeMonstre = listeMonstre;
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		// réactivaiton des boutons
		if(container instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) container).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("activerBoutons", null);
		}
		// fin de l'affichage
		degatsDisplay = null;
		dropDisplay = null;
		listeMonstre = null;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		//on désactive les boutons de la page
		if(container instanceof AppletGameContainer.Container){
			Applet applet = ((AppletGameContainer.Container) container).getApplet();
			JSObject jso = JSObject.getWindow(applet);
			jso.call("desactiverBoutons", null);
		}

		//si on a pas reçu de liste de monstre préalablement, on les génère
		if(listeMonstre==null){
			listeMonstre = new ArrayList<Monstre>();
			for (int i=0; i<Math.random()*4; i++){
				listeMonstre.add(new Monstre());
			}
		}
		else {
			//sinon on les récupère, en générant les images locales
			for (Monstre m : listeMonstre)
				m.initAnimation();
		}
		attaquer = true;
		selectionCible = false;
		tourJoueur=false;
		tourEnnemi=false;
		tour=0;
		definirCible=0;
		attendreReponse=false;
		attaqueJoueurX = container.getWidth()-80;
		destinationAttaqueJoueurX = container.getWidth()-80 - 30;
		joueurAttaque = false;
		enTrainDeRepondre=false;
		combatDeGroupe=false;

		if(Constantes.MODE_ONLINE){
			//si on est le leader : on avertit les autres et on leur donne les monstres du combat
			if(MainGame.getPlayer().estLeaderDUnGroupeNonVide()){
				try {
					MainGame.getRemoteReference().entreEnModeCombat(MainGame.getPlayer(), listeMonstre);
					combatDeGroupe=true;
				} catch (RemoteException e) {
					System.out.println("Erreur combat multi");
					e.printStackTrace();
				}
			}
			else if(MainGame.getPlayer().possedeUnGroupeNonVide())
				combatDeGroupe=true;
		}

		if(!combatDeGroupe){
			MainGame.getPlayer().setXCombat(attaqueJoueurX);
			MainGame.getPlayer().setYCombat(container.getHeight()/2);
		}
		else {
			int i=container.getHeight()/2/MainGame.getJoueursDuGroupe().size();
			for (Player p : MainGame.getJoueursDuGroupe()) {
				p.setXCombat(attaqueJoueurX);
				p.setYCombat(i+=60);
			}
		}

		int espace = 30;
		for(Monstre m : listeMonstre){
			m.setXCombat(Constantes.POSX_COMBAT_MONSTRE);
			m.setYCombat(espace+=60);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//image de fond
		background.draw(0, 0, container.getWidth(), container.getHeight());

		//affichage joueur
		if(!combatDeGroupe){
			MainGame.getPlayer().getSprite().draw(MainGame.getPlayer().getXCombat(),MainGame.getPlayer().getYCombat());
			//pv
			g.setColor(Color.black);
			String pvJoueur = MainGame.getPlayer().getPvCourant()+"/"+MainGame.getPlayer().getPvMax();
			int decalage = g.getFont().getWidth(pvJoueur)/2-Constantes.TAILLE_CARRE_COLLISION/2;
			g.drawString(pvJoueur, MainGame.getPlayer().getXCombat()-decalage, MainGame.getPlayer().getYCombat()+35);
			g.setColor(Color.lightGray);
			g.drawString(pvJoueur, MainGame.getPlayer().getXCombat()-decalage, MainGame.getPlayer().getYCombat()+34);
		}
		else
			for (Player p : MainGame.getJoueursDuGroupe()) {
				p.getSprite().draw(p.getXCombat(),p.getYCombat());
				//pv. En blanc si c'est les notres.
				g.setColor(Color.black);
				String pvJoueur = p.getPvCourant()+"/"+p.getPvMax();
				int decalage = g.getFont().getWidth(pvJoueur)/2-Constantes.TAILLE_CARRE_COLLISION/2;
				g.drawString(pvJoueur, p.getXCombat()-decalage, p.getYCombat()+35);
				if(p.equals(MainGame.getPlayer()))
					g.setColor(Color.white);
				else
					g.setColor(Color.lightGray);
				g.drawString(pvJoueur, p.getXCombat()-decalage, p.getYCombat()+34);
			}

		for(Monstre m : listeMonstre){
			m.getSprite().draw(m.getXCombat(), m.getYCombat());

			//afficher PV
			g.setColor(Color.black);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), m.getYCombat()+46);
			g.setColor(Color.lightGray);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), m.getYCombat()+45);
		}

		// Menu combat
		int positionH = container.getHeight()-Constantes.HAUTEUR_MENU_BAS_COMBAT;
		g.setColor(new Color(0f, 0f, 0f, 0.8f));
		g.fillRect(0, positionH, container.getWidth(), Constantes.HAUTEUR_MENU_BAS_COMBAT);
		g.drawLine(0, positionH, container.getWidth(), positionH);
		if (attaquer){
			if(!tourJoueur)
				g.setColor(Color.darkGray);
			else g.setColor(Color.white);
			g.drawString(Constantes.SELECTION+"Attaquer", 50, positionH+10);
			g.setColor(Color.gray);
			g.drawString("Utiliser potion", 50, positionH+30);
			if(selectionCible && !listeMonstre.isEmpty()){
				g.drawString(Constantes.SELECTION, listeMonstre.get(definirCible).getXCombat(), listeMonstre.get(definirCible).getYCombat()+20);
			}
		}
		else{
			g.setColor(Color.gray);
			g.drawString("Attaquer", 50, positionH+10);
			g.setColor(Color.white);
			if(!tourJoueur)
				g.setColor(Color.darkGray);
			g.drawString(Constantes.SELECTION+"Utiliser potion", 50, positionH+30);
			g.setColor(Color.gray);
		}

		//affichage des dégats

		if(degatsDisplay != null){
			degatsDisplay.render(container, game, g);
		}
		if(dropDisplay != null){
			dropDisplay.render(container, game, g);
		}


	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Combat.delta = delta;
		Input input = container.getInput();

		if(enTrainDeRepondre){
			if(attaqueOnline(cibleRecuTemp, degatsRecuTemp)){
				attendreReponse=false;
				enTrainDeRepondre=false;
				tour++;
			}
		}
		// animation joueur
		if(!combatDeGroupe)
			MainGame.getPlayer().updateCombat(container, game, delta);
		else
			for(Player p : MainGame.getJoueursDuGroupe())
				p.updateCombat(container, game, delta);

		// drops eventuels
		if(dropDisplay != null && dropDisplay.isAffiche())
			dropDisplay.update(container, game, delta);

		if(!attendreReponse){

			//on attend la fin d'affichage des dégats pour continuer
			if(degatsDisplay != null && degatsDisplay.isAffiche())
				degatsDisplay.update(container, game, delta);
			else {
				//si tous les ennemis sont morts, on arrête le combat
				if(listeMonstre.isEmpty()){
					MainGame.getPlayer().finCombat();
					game.enterState(Constantes.GAMEPLAY_MAP_STATE);
				}

				// si le joueur (seul pour l'instant) est ko, on stop aussi en fait
				if(MainGame.getPlayer().getPvCourant()<=0){
					MainGame.getPlayer().finCombat();
					MainGame.getPlayer().setPvCourant(MainGame.getPlayer().getPvMax());
					game.enterState(Constantes.GAMEPLAY_MAP_STATE);
				}


				//avant de naviguer dans le menu, on regarde si on est le plus rapide
				//-- liste de tous les combattants :
				ArrayList<Combattant> listeCombattant = new ArrayList<Combattant>();
				if(!combatDeGroupe)
					listeCombattant.add(MainGame.getPlayer());
				else {
					listeCombattant.addAll(MainGame.getJoueursDuGroupe());
				}

				listeCombattant.addAll(listeMonstre);

				// on défini qui doit commencer à jouer
				if(!tourJoueur && !tourEnnemi){
					if(tour>=listeCombattant.size())
						tour=0;
					combattantPlusRapide = getPlusRapide(listeCombattant, tour);
					//si c'est un joueur
					if(combattantPlusRapide instanceof Player){
						//solo
						if(!combatDeGroupe){
							tourJoueur(delta, input);
						}
						//multi
						else {
							// si c'est "nous"
							if(((Player) combattantPlusRapide).equals(MainGame.getPlayer()))
								tourJoueur(delta, input);
							else {
								attendreReponse=true;
							}
						}
					}
					else{
						tourJoueur=false;
						tourEnnemi=true;
						//si on gère pas l'attaque mais qu'on l'attend juste (pas leader)
						if(combatDeGroupe && !MainGame.getPlayer().estLeaderDUnGroupeNonVide())
							attendreReponse=true;
					}

					//fin du tour
					if(!attendreReponse)
						tour++;
				}

				// c'est au tour de l'ennemi d'attaquer
				if(tourEnnemi){
					attaqueEnnemi(delta);
				}
				if(tourJoueur){
					choisirAction(input, delta);
				}

			}
		}



		//DEBUG : sortir du combat
		if (input.isKeyPressed(Input.KEY_F1)){
			MainGame.getPlayer().finCombat();
			game.enterState(Constantes.GAMEPLAY_MAP_STATE);
		}
	}

	/**
	 * Attaque ennemi (côté local)
	 * @param delta
	 */
	private void attaqueEnnemi(int delta) {
		if(!combatDeGroupe || MainGame.getPlayer().estLeaderDUnGroupeNonVide()){
			if(combattantPlusRapide.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE)){
				tourEnnemi = false;
				int degats;
				//on choisit un joueur au hasard si c'est un combat de groupe et si on est le leader
				if(combatDeGroupe){
					if(MainGame.getPlayer().estLeaderDUnGroupeNonVide()){
						int cible = (int) Math.round(Math.random()*(MainGame.getJoueursDuGroupe().size()-1));
						degats = combattantPlusRapide.attaquer(MainGame.getJoueursDuGroupe().get(cible));
						degatsDisplay = new GUIValeursCombats(MainGame.getJoueursDuGroupe().get(cible).getXCombat(), MainGame.getJoueursDuGroupe().get(cible).getYCombat(), Integer.toString(degats));
						try {
							MainGame.getRemoteReference().attaquer(MainGame.getPlayer(), MainGame.getJoueursDuGroupe().get(cible), degats);
						} catch (RemoteException e) {
							System.out.println("Erreur attaque de l'ennemi");
							e.printStackTrace();
						}
					}
					// si on est pas leader, on passe par l'autre fonction qu'on reçoit
				}
				else{
					degats = combattantPlusRapide.attaquer(MainGame.getPlayer());
					degatsDisplay = new GUIValeursCombats(MainGame.getPlayer().getXCombat(), MainGame.getPlayer().getYCombat(), Integer.toString(degats));
				}
			}
		}
	}

	/**
	 * Utilisée pour le multi
	 * @param delta
	 * @param cible
	 * @param Degats
	 * @return true quand l'animation est terminée
	 */
	private static boolean attaqueEnnemi(int delta, Combattant cible, int degats) {
		if(combattantPlusRapide.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE)){
			tourEnnemi = false;
			if(combatDeGroupe){
				degatsDisplay = new GUIValeursCombats(cible.getXCombat(), cible.getYCombat(), Integer.toString(degats));
			}
			return true;
		}
		return false;
	}

	private void tourJoueur(int delta, Input input) {
		tourJoueur=true;
		tourEnnemi=false;
		choisirAction(input, delta);
	}


	/**
	 * @param listeCombattant, la liste de combattant
	 * @param numero
	 * @return le Nieme combattant le plus rapide de la liste
	 * Par exemple, getPlusRapide(liste, 0) retourne le plus rapide.
	 */
	private Combattant getPlusRapide(ArrayList<Combattant> listeCombattant,  int numero){
		Collections.sort(listeCombattant, new Comparator<Combattant>() {
			@Override
			public int compare(Combattant c1, Combattant c2) {
				return Float.compare(c2.getVitesse(), c1.getVitesse());
			}
		});

		System.out.println("Le plus rapide c'est "+listeCombattant.get(numero).getNom());
		if(listeCombattant.get(numero) instanceof Player)
			System.out.println(((Player) listeCombattant.get(numero)).getId());
		return listeCombattant.get(numero);
	}


	/**
	 * Autorise le joueur à choisir une action dans le menu
	 * (c'est donc à son tour de jouer)
	 */
	public void choisirAction(Input input, int delta){
		// naviguation dans le "menu"

		if(!joueurAttaque){
			if(!selectionCible){
				if(input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_UP))
					attaquer = !attaquer;

				if(input.isKeyPressed(Input.KEY_ENTER)){
					// s'il choisit d'attaquer
					if(attaquer && !selectionCible){
						selectionCible = true;
					}
					else {
						int soin = MainGame.getPlayer().utiliserPotion();
						seSoigner(soin);
					}
				}
			}

			//selection d'une cible
			else {
				if(input.isKeyPressed(Input.KEY_DOWN))
					if(definirCible < listeMonstre.size()-1)
						definirCible++;
					else definirCible=0;
				else if(input.isKeyPressed(Input.KEY_UP))
					if(definirCible>0)
						definirCible--;
					else definirCible=listeMonstre.size()-1;
				else if(input.isKeyPressed(Input.KEY_ENTER)){
					// on attaque
					joueurAttaque = true;
				}
				else if (input.isKeyPressed(Input.KEY_ESCAPE)){
					//on annule
					selectionCible = false;
				}
			}
		}
		else {
			attaqueJoueur(delta);
		}
	}

	/**
	 * Un autre joueur se soigne
	 * @param emetteur
	 * @param soin
	 */
	public static void seSoigner(Player emetteur, int soin) {
		tourJoueur=false;
		degatsDisplay = new GUIValeursCombats(emetteur.getXCombat(), emetteur.getYCombat(), Integer.toString(soin), 2);

		for(Player p : MainGame.getJoueursDuGroupe())
			if(p.equals(emetteur)){
				p.setPvCourant(p.getPvCourant()+soin);
				break;
			}
		
		attendreReponse = false;
		tour++;
	}
	
	/**
	 * On se soigne 
	 * @param emetteur
	 * @param soin
	 */
	public static void seSoigner(int soin) {
		tourJoueur=false;
		degatsDisplay = new GUIValeursCombats(MainGame.getPlayer().getXCombat(), MainGame.getPlayer().getYCombat(), Integer.toString(soin), 2);
		if(combatDeGroupe)
			try {
				MainGame.getRemoteReference().seSoigner(MainGame.getPlayer(), soin);
			} catch (RemoteException e) {
				System.out.println("Erreur lors de l'attaque");
				e.printStackTrace();
			}
	}

	/**
	 * Attaque du joueur local
	 * @param delta
	 */
	private void attaqueJoueur(int delta) {
		if (MainGame.getPlayer().deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX)){
			joueurAttaque = false;
			int degats = MainGame.getPlayer().attaquer(listeMonstre.get(definirCible));
			//si online, on envoit l'attaque aux autres joueurs
			if(combatDeGroupe)
				try {
					MainGame.getRemoteReference().attaquer(MainGame.getPlayer(), listeMonstre.get(definirCible), degats);
				} catch (RemoteException e) {
					System.out.println("Erreur lors de l'attaque");
					e.printStackTrace();
				}
			degatsDisplay = new GUIValeursCombats(listeMonstre.get(definirCible).getXCombat(), listeMonstre.get(definirCible).getYCombat(), Integer.toString(degats));
			if(!listeMonstre.get(definirCible).estEnVie()){
				// si l'ennemi est KO
				ArrayList<Objet> drops = listeMonstre.get(definirCible).drop();
				// si drop
				if(drops!=null && !drops.isEmpty()){
					String drop = "";
					if(drops.size()>1){
						for (Objet o : drops)
							drop+=o.getNom()+", ";
						drop+=" droppés!";
					}
					else drop+=drops.get(0).getNom()+" droppé!";
					dropDisplay = new GUIValeursCombats(listeMonstre.get(definirCible).getXCombat(), listeMonstre.get(definirCible).getYCombat(), drop, 3);
					MainGame.getPlayer().getInventaire().addObjets(drops);
				}
				listeMonstre.remove(definirCible);
				definirCible=0;
			}
			selectionCible=false;
			tourJoueur=false;
		}
	}

	/**
	 * Attaque du joueur, utilisée pour le multi
	 * @return true quand l'animation est terminée
	 */
	private static boolean attaqueJoueur(int delta, Combattant cible, int degats) {
		if (combattantPlusRapide.deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX)){
			joueurAttaque = false;
			degatsDisplay = new GUIValeursCombats(cible.getXCombat(), cible.getYCombat(), Integer.toString(degats));
			if(!cible.estEnVie()){
				// si l'ennemi est KO
				ArrayList<Objet> drops = ((Monstre) cible).drop();
				// si drop
				if(drops!=null && !drops.isEmpty()){
					String drop = "";
					if(drops.size()>1){
						for (Objet o : drops)
							drop+=o.getNom()+", ";
						drop+=" droppés!";
					}
					else drop+=drops.get(0).getNom()+" droppé!";
					dropDisplay = new GUIValeursCombats(cible.getXCombat(), cible.getYCombat(), drop, 3);
					MainGame.getPlayer().getInventaire().addObjets(drops);
				}
				listeMonstre.remove(cible);
				definirCible=0;
			}
			selectionCible=false;
			tourJoueur=false;
			return true;
		}
		return false;
	}

	public static boolean attaqueOnline(Combattant cibleRecu, int degats){
		//txt+dgt
		if(!enTrainDeRepondre){
			Combattant cible = null;
			System.out.println(cibleRecu.getClass());
			if(cibleRecu instanceof Player){
				for(Player p : MainGame.getJoueursDuGroupe())
					if(p.equals(cibleRecu)){
						cible = p;
						break;
					}
			} else if (cibleRecu instanceof Monstre){
				for(Monstre m : listeMonstre)
					if(m.equals(cibleRecu)){
						cible = m;
						break;
					}
			}
			cibleRecuTemp = cible;
			degatsRecuTemp = degats;
			combattantPlusRapide.attaquer(cible, degats);
		}
		//anim
		if(combattantPlusRapide instanceof Player){
			if(attaqueJoueur(delta, cibleRecuTemp, degats))
				return true;
		}
		else{
			if(attaqueEnnemi(delta, cibleRecuTemp, degats))
				return true;
		}
		enTrainDeRepondre=true;
		return false;

		//		if(combattantPlusRapide instanceof Player)
		//			combattantPlusRapide.deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX);
		//		else
		//			combattantPlusRapide.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE);

	}

}
