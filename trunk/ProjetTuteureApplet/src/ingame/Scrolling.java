package ingame;

import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;



public class Scrolling {

	public static void scrollTopLayer(int playerX, int playerY, int resolutionWidth, int resolutionHeight, Map currMap){
		int mapHeight = currMap.getTmap().getHeight()*currMap.getTmap().getTileHeight();
		int mapWidth = currMap.getTmap().getWidth()*currMap.getTmap().getTileWidth();
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = playerY-resolutionHeight/2<0;
		boolean bas = playerY+resolutionHeight/2>mapHeight;

		if (gauche && haut){
			currMap.getTmap().render(0,0,3);
		}
		else if (gauche && bas){
			currMap.getTmap().render(0, -mapHeight+resolutionHeight,3);
		}
		else if (haut && droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, 0,3);
		}
		else if (bas && droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, -mapHeight+resolutionHeight,3);
		}
		else if(gauche){
			currMap.getTmap().render(0, -playerY+resolutionHeight/2, 3);
		}
		else if (droite){
			currMap.getTmap().render(resolutionWidth-mapWidth, -playerY+resolutionHeight/2, 3);
		}
		else if(haut){
			currMap.getTmap().render(-playerX+resolutionWidth/2, 0, 3);
		}
		else if(bas){
			currMap.getTmap().render(-playerX+resolutionWidth/2, resolutionHeight-mapHeight, 3);
		}
		else {
			// haut gauche
			currMap.getTmap().render(-playerX+resolutionWidth/2, -playerY+resolutionHeight/2, 3);
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

	public static void scrollBottomLayer(int playerX, int playerY, int resolutionWidth, int resolutionHeight, Map map){
		int mapHeight = map.getTmap().getHeight()*map.getTmap().getTileHeight();
		int mapWidth = map.getTmap().getWidth()*map.getTmap().getTileWidth();
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = (int)playerY-resolutionHeight/2<0;
		boolean bas = (int)playerY+resolutionHeight/2>mapHeight;

		if (gauche && haut){
			map.getTmap().render(0,0);
		}
		else if (gauche && bas){
			map.getTmap().render(0, -mapHeight+resolutionHeight);
		}
		else if (haut && droite){
			map.getTmap().render(resolutionWidth-mapWidth, 0);
		}
		else if (bas && droite){
			map.getTmap().render(resolutionWidth-mapWidth, -mapHeight+resolutionHeight);
		}
		else if(gauche){
			map.getTmap().render(0, -playerY+resolutionHeight/2);
		}
		else if (droite){
			map.getTmap().render(resolutionWidth-mapWidth, -playerY+resolutionHeight/2);
		}
		else if(haut){
			map.getTmap().render(-playerX+resolutionWidth/2, 0);
		}
		else if(bas){
			map.getTmap().render(-playerX+resolutionWidth/2, resolutionHeight-mapHeight);
		}
		else {
			// haut gauche
			map.getTmap().render(-playerX+resolutionWidth/2, -playerY+resolutionHeight/2);
		}
	}

}
