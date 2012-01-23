package rmi.paquetJoueur;

import java.awt.Point;
import java.io.Serializable;
import java.rmi.server.UID;

public class PaquetJoueur implements Serializable{
	private UID id;
	private Point position;
	private int direction;
	private String spriteSheetName, mapName;
	
	public PaquetJoueur(UID id, Point position, int direction, String spriteSheetName, String mapName) {
		this.id = id;
		this.position = position;
		this.direction = direction;
		this.spriteSheetName = spriteSheetName;
		this.mapName = mapName;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public UID getId() {
		return id;
	}
	public int getDirection() {
		return direction;
	}
	public String getSpriteSheetName() {
		return spriteSheetName;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	
	
	
	
	
	
}
