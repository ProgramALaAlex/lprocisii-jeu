package exploration;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import constantes.Constantes;
 
public class Map implements TileBasedMap {
	private TiledMap tmap;
	private int mapWidth;
	private int mapHeight;
	private String IDMap;

	private static ArrayList<Rectangle> listeCollisions;
	private static boolean safe;
	private static ArrayList<Teleporter> listeTeleporter;
	
 
	public Map(String id) throws SlickException {
		listeCollisions = new ArrayList<Rectangle>();
		
		this.tmap = new TiledMap(Constantes.MAP_LOCATION+"map"+id+".tmx", "res/map");
		this.mapWidth = tmap.getWidth() * tmap.getTileWidth();
		this.mapHeight = tmap.getHeight() * tmap.getTileHeight();

		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				if (tileID == 68) {
					listeCollisions.add(new Rectangle(x*Constantes.TAILLE_CARRE_COLLISION, y*Constantes.TAILLE_CARRE_COLLISION, Constantes.TAILLE_CARRE_COLLISION, Constantes.TAILLE_CARRE_COLLISION));
				}
			}
		}
		this.IDMap = id;

		if(id.equals("01"))
			safe = true;
		else safe = false;
		
		//initialisation des TP (pour pas avoir à parcourir tous les TP du monde dans la méthode update())
		listeTeleporter = new ArrayList<Teleporter>();
		for(Teleporter tp : Constantes.LISTE_TP){
			if(tp.getIdMapDepart().equals(this.IDMap)){
				listeTeleporter.add(tp);
			}
		}
	}
	
	
	
	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		for (Rectangle r : listeCollisions) {
			if(r.contains(tx*Constantes.BLOCK_SIZE+Constantes.BLOCK_SIZE/2, ty*Constantes.BLOCK_SIZE+Constantes.BLOCK_SIZE/2))     
				return true;
		}
		return false;
	}



	public ArrayList<Rectangle> getCollision() {
		return listeCollisions;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1;
	}

	@Override
	public int getHeightInTiles() {
		return tmap.getHeight();
	}

	public String getIDMap(){
		return this.IDMap;
	}

	public ArrayList<Teleporter> getListeTeleporter() {
		return listeTeleporter;
	}

	public int getMapHeight() {
		return mapHeight;
	}



	public int getMapWidth() {
		return mapWidth;
	}



	public TiledMap getTmap() {
		return tmap;
	}



	@Override
	public int getWidthInTiles() {
		return tmap.getWidth();
	}



	public boolean isSafe() {
		return safe;
	}



	@Override
	public void pathFinderVisited(int x, int y) {
	}

	
	
}