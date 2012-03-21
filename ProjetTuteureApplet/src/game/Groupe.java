package game;


import java.io.Serializable;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import constantes.Constantes;

import exploration.Exploration;

// pas top en fait cette classe :x
public class Groupe extends ArrayList<UID> implements Serializable{
	private String nom;
	private UID groupeId;
	
	public Groupe(Player leader) {
		super(4);
		nom = "Groupe de "+leader.getNom();
		this.add(leader.getId());
		groupeId = new UID();
		System.out.println("\""+nom+"\" créé!");
	}
	
	public Groupe(Player leader, String nomGroupe) {
		super(4);
		nom = nomGroupe.replaceAll("\\<.*?\\>", "");;
		this.add(leader.getId());
		groupeId = new UID();
		System.out.println("\""+nom+"\" créé!");
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public UID getID() {
		return groupeId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Groupe other = (Groupe) obj;
		if (groupeId == null) {
			if (other.groupeId != null)
				return false;
		} else if (!groupeId.equals(other.groupeId))
			return false;
		return true;
	}
	
	/**
	 * @return le joueur normal si le leader n'est pas trouvé
	 */
	public Player getLeader(){
		if(Constantes.ONLINE){
			for(Player p : Exploration.getListeJoueurLoc())
				if(p.getId().equals(this.get(0)))
					return p;
			return MainGame.getPlayer();
		}
		return MainGame.getPlayer();
	}
	
	public int getPosition(Player p){
		return this.indexOf(p.getId());
	}
	
	public boolean isLeader(Player p){
		return(p.getId().equals(this.get(0)));
	}

	
}
