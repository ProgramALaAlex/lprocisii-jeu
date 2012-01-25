package exploration;


import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;


import rmi.paquetJoueur.PaquetJoueur;


/**
 * C4EST MOCHE
 */
public class Scrolling {

	public static void scrollLayer(int playerX, int playerY, int resolutionWidth, int resolutionHeight, Map currMap, int layer){
		int mapHeight = currMap.getTmap().getHeight()*currMap.getTmap().getTileHeight();
		int mapWidth = currMap.getTmap().getWidth()*currMap.getTmap().getTileWidth();
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = playerY-resolutionHeight/2<0;
		boolean bas = playerY+resolutionHeight/2>mapHeight;

		if (gauche && haut){
			currMap.getTmap().render(0,0,layer);
		}
		else if (gauche && bas){
			currMap.getTmap().render(0, -mapHeight+resolutionHeight,layer);
		}
		else if (haut && droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, 0,layer);
		}
		else if (bas && droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, -mapHeight+resolutionHeight,layer);
		}
		else if(gauche){
			currMap.getTmap().render(0, -playerY+resolutionHeight/2, layer);
		}
		else if (droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, -playerY+resolutionHeight/2, layer);
		}
		else if(haut){
			currMap.getTmap().render(-playerX+resolutionWidth/2, 0, layer);
		}
		else if(bas){
			currMap.getTmap().render(-playerX+resolutionWidth/2, resolutionHeight-mapHeight, layer);
		}
		else {
			// haut gauche
			currMap.getTmap().render(-playerX+resolutionWidth/2, -playerY+resolutionHeight/2, layer);
		}
	}

	public static void scrollPlayer(int playerX, int playerY, int resolutionWidth, int resolutionHeight, Player player, Map map){
		int mapHeight = map.getTmap().getHeight()*map.getTmap().getTileHeight();
		int mapWidth = map.getTmap().getWidth()*map.getTmap().getTileWidth();
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = (int)playerY-resolutionHeight/2<0;
		boolean bas = (int)playerY+resolutionHeight/2>mapHeight;

		if (gauche && haut){
			player.getSprite().draw(playerX,playerY);
		}
		else if (gauche && bas){
			player.getSprite().draw(playerX, playerY-mapHeight+resolutionHeight);
		}
		else if (haut && droite){
			player.getSprite().draw(resolutionWidth-mapWidth+playerX, playerY);
		}
		else if (bas && droite){
			player.getSprite().draw(resolutionWidth-mapWidth+playerX, playerY-mapHeight+resolutionHeight);
		}
		else if(gauche){
			player.getSprite().draw(playerX, resolutionHeight/2);
		}
		else if (droite){
			player.getSprite().draw(resolutionWidth-mapWidth+playerX, resolutionHeight/2);
		}
		else if(haut){
			player.getSprite().draw(resolutionWidth/2, playerY);
		}
		else if(bas){
			player.getSprite().draw(resolutionWidth/2, resolutionHeight-mapHeight+playerY);
		}
		else {
			// haut gauche
			player.getSprite().draw(resolutionWidth/2, resolutionHeight/2);
		}
	}

	
	public static void scrollOtherPlayers(Graphics g, PaquetJoueur p, int positionLocaleX, int positionLocaleY, int resolutionWidth, int resolutionHeight, Map map){
		int mapHeight = map.getTmap().getHeight()*map.getTmap().getTileHeight();
		int mapWidth = map.getTmap().getWidth()*map.getTmap().getTileWidth();
		boolean gauche = positionLocaleX-resolutionWidth/2<0;
		boolean droite = positionLocaleX+resolutionWidth/2>mapWidth;
		boolean haut = positionLocaleY-resolutionHeight/2<0;
		boolean bas = positionLocaleY+resolutionHeight/2>mapHeight;
		int playerX = (int) p.getPosition().getX();
		int playerY = (int) p.getPosition().getY();
		Player player = new Player(p);

		if (gauche && haut){
			player.getSprite().draw(playerX,playerY, 32, 32);
		}
		else if (gauche && bas){
			player.getSprite().draw(playerX, playerY-mapHeight+resolutionHeight, 32, 32);
		}
		else if (haut && droite){
			player.getSprite().draw(playerX+resolutionWidth-mapWidth, playerY, 32, 32);
		}
		else if (bas && droite){
			player.getSprite().draw(playerX+resolutionWidth-mapWidth, playerY-mapHeight+resolutionHeight, 32, 32);
		}
		else if(gauche){
			player.getSprite().draw(playerX, playerY-positionLocaleY+resolutionHeight/2, 32, 32);
		}
		else if (droite){
			player.getSprite().draw(playerX+resolutionWidth-mapWidth, playerY-positionLocaleY+resolutionHeight/2, 32, 32);
		}
		else if(haut){
			player.getSprite().draw(playerX-positionLocaleX+resolutionWidth/2, playerY, 32, 32);
		}
		else if(bas){
			player.getSprite().draw(playerX-positionLocaleX+resolutionWidth/2, playerY+resolutionHeight-mapHeight, 32, 32);
		}
		else {
			// haut gauche
			player.getSprite().draw(playerX-positionLocaleX+resolutionWidth/2, playerY-positionLocaleY+resolutionHeight/2, 32, 32);
		}
	}

}
