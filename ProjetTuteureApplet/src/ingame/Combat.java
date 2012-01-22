package ingame;


import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import constantes.Constantes;



public class Combat extends BasicGameState{
	private int stateID;
	private ArrayList<Monstre> listeMonstre;

	private boolean attaquer, selectionCible, tourJoueur, tourEnnemi;
	private int definirCible, tour;
	private Combattant enCours;

	private int attaqueJoueurX, destinationAttaqueJoueurX; // peut pas mettre dans constante car par rapport au bord..
	private boolean joueurAttaque; //moche

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
	}


	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		listeMonstre = new ArrayList<Monstre>();
		for (int i=0; i<Math.random()*4; i++){
			listeMonstre.add(new Monstre());
		}
		attaquer = true;
		selectionCible = false;
		tourJoueur=false;
		tourEnnemi=false;
		tour=0;
		definirCible=0;

		attaqueJoueurX = container.getWidth()-80;
		destinationAttaqueJoueurX = container.getWidth()-80 - 30;
		joueurAttaque = false;

		Exploration.getPlayer().setXCombat(attaqueJoueurX);
		for(Monstre m : listeMonstre){
			m.setXCombat(Constantes.POSX_COMBAT_MONSTRE);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Exploration.getPlayer().getSprite().draw(Exploration.getPlayer().getXCombat(),container.getHeight()/2);

		int espace = 100;
		for(Monstre m : listeMonstre){
			m.getSprite().draw(m.getXCombat(), (espace+=60));
			g.setColor(Color.black);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), espace+46);
			g.setColor(Color.gray);
			g.drawString(m.getPvCourant()+"/"+m.getPvMax(), m.getXCombat(), espace+45);
		}

		// Menu combat
		int positionH = container.getHeight()-70;
		g.drawLine(0, positionH, container.getWidth(), positionH);
		if (attaquer){
			g.setColor(Color.white);
			g.drawString(Constantes.SELECTION+"Attaquer", 50, positionH+10);
			g.setColor(Color.gray);
			g.drawString("Utiliser potion", 50, positionH+30);
			if(selectionCible){
				g.drawString(Constantes.SELECTION, 80, 170+definirCible*50);
			}
		}
		else{
			g.drawString("Attaquer", 50, positionH+10);
			g.setColor(Color.white);
			g.drawString(Constantes.SELECTION+"Utiliser potion", 50, positionH+30);
			g.setColor(Color.gray);
		}
		g.drawString(Exploration.getPlayer().getPvCourant()+"/"+Exploration.getPlayer().getPvMax(), Exploration.getPlayer().getXCombat()-20, container.getHeight()/2+35);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();

		Exploration.getPlayer().updateCombat(container, game, delta);


		//avant de naviguer dans le menu, on regarde si on est le plus rapide
		//-- liste de tous les combattants :
		ArrayList<Combattant> listeCombattant = new ArrayList<Combattant>();
		listeCombattant.add(Exploration.getPlayer());
		listeCombattant.addAll(this.listeMonstre);

		if(!tourJoueur && !tourEnnemi){
			if(tour>=listeCombattant.size())
				tour=0;
			enCours = getPlusRapide(listeCombattant, tour);
			if(enCours instanceof Player){
				tourJoueur=true;
				tourEnnemi=false;
				choisirAction(input, delta);
			}
			else{
				tourEnnemi=true;
				enCours.attaquer(Exploration.getPlayer());
			}

			//fin du tour
			tour++;
		}
		// c'est au tour de l'ennemi d'attaquer
		if(tourEnnemi){
			if(enCours.deplacementAttaque(delta, Constantes.POSX_COMBAT_MONSTRE, Constantes.POSX_ATTAQUE_MONSTRE))
				tourEnnemi = false;
		}

		if(tourJoueur){
			choisirAction(input, delta);
		}


		//si tous les ennemis sont mort, on arrête le combat
		if(listeMonstre.isEmpty()){
			Exploration.getPlayer().finCombat();
			game.enterState(Constantes.GAMEPLAY_MAP_STATE);
		}

		// si le joueur (seul pour l'instant) est ko, on stop aussi en fait
		if(Exploration.getPlayer().getPvCourant()<=0){
			Exploration.getPlayer().finCombat();
			Exploration.getPlayer().setPvCourant(Exploration.getPlayer().getPvMax());
			game.enterState(Constantes.GAMEPLAY_MAP_STATE);
		}


		//DEBUG : sortir du combat
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			Exploration.getPlayer().finCombat();
			game.enterState(Constantes.GAMEPLAY_MAP_STATE);
		}
	}


	/**
	 * @param listeCombattant, la liste de combattant
	 * @param numero
	 * @return le Nieme combattant le plus rapide de la liste
	 * Par exemple, getPlusRapide(liste, 0) retourne le plus rapide.
	 */
	private Combattant getPlusRapide(ArrayList<Combattant> listeCombattant,  int numero){
		ArrayList<Combattant> temp = (ArrayList<Combattant>) listeCombattant.clone();
		ArrayList<Combattant> sortedList = new ArrayList<Combattant>();
		Combattant rapide = null;

		for (int i=0; i<listeCombattant.size(); i++){
			int plusRapide=0;
			for (Combattant c : temp){
				if(c.getVitesse() > plusRapide){
					plusRapide = c.getVitesse();
					rapide = c;
				}
			}
			sortedList.add(rapide);
			temp.remove(rapide);
		}
		System.out.println("Le plus rapide c'est "+sortedList.get(numero).getNom());
		return sortedList.get(numero);
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
					// si pas encore en train de choisir cible, choisir attaquer (menu bas)
					if(attaquer && !selectionCible){
						selectionCible = true;
					}
					else {
						Exploration.getPlayer().utiliserPotion();
						tourJoueur=false;
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
			}
		}
		else {
			if (Exploration.getPlayer().deplacementAttaque(delta, attaqueJoueurX, destinationAttaqueJoueurX)){
				joueurAttaque = false;
				if(Exploration.getPlayer().attaquer(listeMonstre.get(definirCible))){
					// si l'ennemi est KO
					Exploration.getPlayer().getInventaire().addObjets(listeMonstre.get(definirCible).drop());
					listeMonstre.remove(definirCible);
					definirCible=0;
				}
				selectionCible=false;
				tourJoueur=false;
			}
		}
	}
}
