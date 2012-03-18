package combats;


import inventaire.Objet;

import java.applet.Applet;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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
	private static ArrayList<Player> listeJoueurs;
	private static ArrayList<Player> listeJoueursKO;

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


	/**
	 * Utilisé par le callbacker
	 */
	public static void setListes(ArrayList<Player> listeJoueurs, ArrayList<Monstre> listeMonstre) {
		Combat.listeJoueurs = listeJoueurs;
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
		MainGame.getPlayer().synchroniserStats(getPlayer());
		MainGame.getPlayer().setLeaderCombat(false);
		MainGame.getPlayer().getListeJoueursCombatEnCours().clear();
		if(MainGame.getPlayer().isKO())
			MainGame.getPlayer().setPvCourant(1);
		combatDeGroupe = false;
		// fin de l'affichage
		listeJoueursKO = null;
		degatsDisplay = null;
		dropDisplay = null;
		listeMonstre = null;
		listeJoueurs = null;
		
		MainGame.getPlayer().finCombat();
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
			listeMonstre = new ArrayList<Monstre>(4);
			for (int i=0; i<Math.random()*4; i++){
				Monstre m = new Monstre();
				m.addFinNom("("+i+")");
				listeMonstre.add(m);
			}
		}
		else {
			//sinon on les récupère, en générant les images correspondantes localement
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
		listeJoueursKO = new ArrayList<Player>();
		
		// on génère la liste des joueurs si on l'a pas reçu
		if(listeJoueurs == null) {
			listeJoueurs = MainGame.getPlayer().getJoueursDuGroupeProchesSansCombat();

			combatDeGroupe=!listeJoueurs.isEmpty();

			listeJoueurs.add(MainGame.getPlayer());
			
			if(Constantes.ONLINE){
				//si on est proche d'autres membres du groupe : on leur envoit le combat
				if(combatDeGroupe && MainGame.getPlayer().isLeaderCombat()){
					try {
						MainGame.getRemoteReference().entreEnModeCombat(MainGame.getPlayer(), listeJoueurs, listeMonstre);
					} catch (RemoteException e) {
						System.out.println("Erreur combat multi");
						e.printStackTrace();
					}
				}
			}
		}
		else {
			combatDeGroupe = true;
			for (Player p : listeJoueurs)
				p.initAnimation();
		}


		Collections.sort(listeJoueurs, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return p1.getId().toString().compareTo(p2.getId().toString());
			}
		});

		MainGame.getPlayer().setListeJoueursCombatEnCours(new ArrayList<Player>(listeJoueurs));

		// positionnement des combattants
		int i=container.getHeight()/2/listeJoueurs.size();
		for (Player p : listeJoueurs) {
			p.setXCombat(attaqueJoueurX);
			p.setYCombat(i);
			i+=70;
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
		for (Player p : listeJoueurs) {
			p.getSprite().draw(p.getXCombat(),p.getYCombat());
			g.setColor(Color.black);
			String pvJoueur = p.getPvCourant()+"/"+p.getPvMax();
			int decalage = g.getFont().getWidth(pvJoueur)/2-Constantes.TAILLE_CARRE_COLLISION/2;
			g.drawString(pvJoueur, p.getXCombat()-decalage, p.getYCombat()+35);
			//pv. En blanc si c'est les notres.
			if(p.equals(MainGame.getPlayer()))
				g.setColor(Color.white);
			else
				g.setColor(Color.lightGray);
			g.drawString(pvJoueur, p.getXCombat()-decalage, p.getYCombat()+34);
		}
		for(Player p : listeJoueursKO){
			p.getSprite().getImage(1).setRotation(90f);
			g.drawImage(p.getSprite().getImage(1), p.getXCombat(), p.getYCombat());
			p.getSprite().getImage(1).setRotation(0);
		}

		for(Monstre m : listeMonstre){
			m.getSprite().draw(m.getXCombat(), m.getYCombat());
			//afficher PV
			g.setColor(Color.black);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), m.getYCombat()+46);
			g.setColor(Color.lightGray);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), m.getYCombat()+45);
		}

		// Menu des combats
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
		
		// animation joueur(s)
		for(Player p : listeJoueurs)
			p.updateCombat(container, game, delta);

		gererDeconnexions();
		
		// drops eventuels
		if(dropDisplay != null && dropDisplay.isAffiche())
			dropDisplay.update(container, game, delta);

		if(!attendreReponse){
			
			//on attend la fin d'affichage des dégats pour continuer
			if(degatsDisplay != null && degatsDisplay.isAffiche())
				degatsDisplay.update(container, game, delta);
			else {
				//si tous les ennemis sont morts, on arrête le combat = retour en exploration
				if(listeMonstre.isEmpty()){
					MainGame.getPlayer().finCombat();
					game.enterState(Constantes.GAMEPLAY_MAP_STATE);
				}

				// si tous les joueurs sont ko, on stop aussi
				if(listeJoueurs.isEmpty()) {
					game.enterState(Constantes.GAMEPLAY_MAP_STATE);
				}

				// liste de tous les combattants
				ArrayList<Combattant> listeCombattant = new ArrayList<Combattant>();
				listeCombattant.addAll(listeJoueurs);
				listeCombattant.addAll(listeMonstre);
				
				// on défini qui doit commencer à jouer
				if(!tourJoueur && !tourEnnemi){
					if(tour>=listeCombattant.size())
						tour=0;
					combattantPlusRapide = getPlusRapide(listeCombattant, tour);
					//si c'est un joueur
					if(combattantPlusRapide instanceof Player){
						// si c'est "nous"
						if(((Player) combattantPlusRapide).equals(MainGame.getPlayer())){
							tourJoueur=true;
							tourEnnemi=false;
							choisirAction(input, delta);
						}
						else {
							attendreReponse=true;
						}
					}
					// si c'est un monstre
					else{
						tourJoueur=false;
						tourEnnemi=true;
						//si on attends de recevoir l'attaque
						if(combatDeGroupe && !MainGame.getPlayer().isLeaderCombat())
							attendreReponse=true;
					}

					//fin du tour
					if(!attendreReponse)
						tour++;
				}

				// c'est au tour de l'ennemi d'attaquer
				if(tourEnnemi){
					attaqueEnnemiEnvoyer(delta);
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
	 * permet de gérer les déconnections
	 * (moche)
	 */
	private void gererDeconnexions() {
		for (Iterator<Player> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Player p = (Player) iterator.next();
			try {
				if(!MainGame.getRemoteReference().isConnected(p)){
					reaffecterLeader(p);
					if(MainGame.getPlayer().isLeaderCombat())
						attendreReponse=false;
					iterator.remove();
					// liste de tous les combattants
					ArrayList<Combattant> listeCombattant = new ArrayList<Combattant>();
					listeCombattant.addAll(listeJoueurs);
					listeCombattant.addAll(listeMonstre);
					if(combattantPlusRapide != null && combattantPlusRapide.equals(p)){
						if(tour>=listeCombattant.size())
							tour=0;
						combattantPlusRapide = getPlusRapide(listeCombattant, tour);
					}
					ArrayList<Player> JoueurEncorePresent = new ArrayList<Player>();
					JoueurEncorePresent.addAll(listeJoueurs);
					JoueurEncorePresent.addAll(listeJoueursKO);
					if(JoueurEncorePresent.size()==1)
						combatDeGroupe=false;
					MainGame.getPlayer().setListeJoueursCombatEnCours(JoueurEncorePresent);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		for (Iterator<Player> iterator = listeJoueursKO.iterator(); iterator.hasNext();) {
			Player p = (Player) iterator.next();
			try {
				if(!MainGame.getRemoteReference().isConnected(p)){
					reaffecterLeader(p);
					if(MainGame.getPlayer().isLeaderCombat())
						attendreReponse=false;
					iterator.remove();
					// liste de tous les combattants
					ArrayList<Combattant> listeCombattant = new ArrayList<Combattant>();
					listeCombattant.addAll(listeJoueurs);
					listeCombattant.addAll(listeMonstre);
					if(combattantPlusRapide != null && combattantPlusRapide.equals(p)){
						if(tour>=listeCombattant.size())
							tour=0;
						combattantPlusRapide = getPlusRapide(listeCombattant, tour);
					}
					ArrayList<Player> JoueurEncorePresent = new ArrayList<Player>();
					JoueurEncorePresent.addAll(listeJoueurs);
					JoueurEncorePresent.addAll(listeJoueursKO);
					if(JoueurEncorePresent.size()==1)
						combatDeGroupe=false;
					p.setListeJoueursCombatEnCours(JoueurEncorePresent);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Attaque ennemi (envoyer)
	 * @param delta
	 */
	private void attaqueEnnemiEnvoyer(int delta) {
		if(!combatDeGroupe || MainGame.getPlayer().isLeaderCombat()){
			if(combattantPlusRapide.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE)){
				tourEnnemi = false;
				int degats;
				//on choisit un joueur au hasard si c'est un combat de groupe et si on est le leader
				if(combatDeGroupe && MainGame.getPlayer().isLeaderCombat()){
					int cible = (int) Math.round(Math.random()*(listeJoueurs.size()-1));
					degats = combattantPlusRapide.attaquer(listeJoueurs.get(cible));
					degatsDisplay = new GUIValeursCombats(listeJoueurs.get(cible).getXCombat(), listeJoueurs.get(cible).getYCombat(), Integer.toString(degats));
					try {
						MainGame.getRemoteReference().attaquer(MainGame.getPlayer(), listeJoueurs.get(cible), degats);
					} catch (RemoteException e) {
						System.out.println("Erreur attaque de l'ennemi");
						e.printStackTrace();
					}
					
					playerDown(listeJoueurs.get(cible));
				}
				else {
					degats = combattantPlusRapide.attaquer(getPlayer());
					degatsDisplay = new GUIValeursCombats(getPlayer().getXCombat(), getPlayer().getYCombat(), Integer.toString(degats));
					playerDown(getPlayer());
				}
			}
		}
	}

	private static void playerDown(Player cible) {
		if(cible.isKO()){
			reaffecterLeader(cible);
			listeJoueursKO.add(cible);
			listeJoueurs.remove(cible);
			//TODO
		}
	}

	private static void reaffecterLeader(Player JoueurQuiVaPartir) {
		if(JoueurQuiVaPartir.isLeaderCombat()){
			for(Player p : listeJoueurs)
				if(!p.isKO() && !p.isLeaderCombat()){
					p.setLeaderCombat(true);
					if(p.equals(MainGame.getPlayer()))
						MainGame.getPlayer().setLeaderCombat(true);
					break;
				}
			JoueurQuiVaPartir.setLeaderCombat(false);
		}
	}

	/**
	 * Lorsque l'ennemi attaque (recevoir)
	 * @param delta
	 * @param cible
	 * @param Degats
	 * @return true quand l'animation est terminée
	 */
	private static boolean attaqueEnnemiRecevoir(int delta, Combattant cible, int degats) {
		if(combattantPlusRapide.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE)){
			tourEnnemi = false;
			if(combatDeGroupe){
				degatsDisplay = new GUIValeursCombats(cible.getXCombat(), cible.getYCombat(), Integer.toString(degats));
			}
			playerDown((Player) cible);
			return true;
		}
		return false;
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
	 * Lorsque le joueur choisit une action dans le menu
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
						int soin = getPlayer().utiliserPotion();
						seSoignerEnvoyer(soin);
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
			attaqueJoueurEnvoyer(delta);
		}
	}

	/**
	 * Un autre joueur se soigne (recevoir)
	 * @param emetteur
	 * @param soin
	 */
	public static void seSoignerRecevoir(Player emetteur, int soin) {
		tourJoueur=false;
		degatsDisplay = new GUIValeursCombats(emetteur.getXCombat(), emetteur.getYCombat(), Integer.toString(soin), 2);

		for(Player p : listeJoueurs)
			if(p.equals(emetteur)){
				p.setPvCourant(p.getPvCourant()+soin);
				break;
			}

		attendreReponse = false;
		tour++;
	}

	/**
	 * On se soigne (envoyer) 
	 * @param emetteur
	 * @param soin
	 */
	public static void seSoignerEnvoyer(int soin) {
		tourJoueur=false;
		degatsDisplay = new GUIValeursCombats(getPlayer().getXCombat(), getPlayer().getYCombat(), Integer.toString(soin), 2);
		if(combatDeGroupe)
			try {
				MainGame.getRemoteReference().seSoigner(getPlayer(), soin);
			} catch (RemoteException e) {
				System.out.println("Erreur lors de l'attaque");
				e.printStackTrace();
			}
	}
	
	/**
	 * @return le joueur local
	 */
	private static Player getPlayer(){
//		if(!MainGame.getPlayer().isLeaderCombat()){
			if(listeJoueurs.contains(MainGame.getPlayer()))
				return listeJoueurs.get(listeJoueurs.indexOf(MainGame.getPlayer()));
			return listeJoueursKO.get(listeJoueursKO.indexOf(MainGame.getPlayer()));
//		} else return MainGame.getPlayer();
	}

	/**
	 * Attaque du joueur (envoyer)
	 * @param delta
	 */
	private void attaqueJoueurEnvoyer(int delta) {
		if (getPlayer().deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX)){
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
			drop(listeMonstre.get(definirCible));
			selectionCible=false;
			tourJoueur=false;
		}
	}

	/**
	 * Attaque du joueur (recevoir)
	 * @return true quand l'animation est terminée
	 */
	private static boolean attaqueJoueurRecevoir(int delta, Combattant cible, int degats) {
		if (combattantPlusRapide.deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX)){
			degatsDisplay = new GUIValeursCombats(cible.getXCombat(), cible.getYCombat(), Integer.toString(degats));
			drop((Monstre)cible);
			return true;
		}
		return false;
	}

	private static void drop(Monstre monstre) {
		if(!monstre.estEnVie()){
			// si l'ennemi est KO
			ArrayList<Objet> drops = monstre.drop();
			// si drop
			if(drops!=null && !drops.isEmpty()){
				String drop = "";
				if(drops.size()>1){
					for (Objet o : drops)
						drop+=o.getNom()+", ";
					drop+=" droppés!";
				}
				else drop+=drops.get(0).getNom()+" droppé!";
				dropDisplay = new GUIValeursCombats(monstre.getXCombat(), monstre.getYCombat(), drop, 3);
				MainGame.getPlayer().getInventaire().addObjets(drops);
			}
			listeMonstre.remove(monstre);
			definirCible=0;
		}
	}

	/**
	 * Lorsque le joueur reçoit une attaque, appelé en callback
	 * @param cibleRecu
	 * @param degats
	 * @return
	 */
	public static boolean attaqueOnline(Combattant cibleRecu, int degats){
		//txt+dgt
		if(!enTrainDeRepondre){
			Combattant cible = null;
			System.out.println(cibleRecu.getClass());
			if(cibleRecu instanceof Player){
				for(Player p : listeJoueurs)
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
		enTrainDeRepondre=true;
		//anim attaque
		if(combattantPlusRapide instanceof Player){
			if(attaqueJoueurRecevoir(delta, cibleRecuTemp, degats))
				return true;
		}
		else{
			if(attaqueEnnemiRecevoir(delta, cibleRecuTemp, degats))
				return true;
		}
		return false;
	}

}
