package exploration;


import game.MainGame;
import game.Player;

import java.awt.Point;

import org.newdawn.slick.Graphics;


public class Scrolling {

	public static Point getDecalage(int resolutionWidth, int resolutionHeight) {
		int mapHeight = Exploration.getCurrMap().getTmap().getHeight()*Exploration.getCurrMap().getTmap().getTileHeight();
		int mapWidth = Exploration.getCurrMap().getTmap().getWidth()*Exploration.getCurrMap().getTmap().getTileWidth();
		
		float playerX = MainGame.getPlayer().getX();
		float playerY = MainGame.getPlayer().getY();
		
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = playerY-resolutionHeight/2<0;
		boolean bas = playerY+resolutionHeight/2>mapHeight;
	
		
		float x, y;
		if (gauche && haut){
			x=0;
			y=0;
		}
		else if (gauche && bas){
			x=0;
			y=-mapHeight+resolutionHeight;
		}
		else if (haut && droite){
			x=resolutionWidth-mapWidth;
			y=0;
		}
		else if (bas && droite){
			x = resolutionWidth-mapWidth;
			y = -mapHeight+resolutionHeight;
		}
		else if(gauche){
			x=0;
			y=-playerY+resolutionHeight/2;
		}
		else if (droite){
			x=resolutionWidth-mapWidth;
			y=-playerY+resolutionHeight/2;
		}
		else if(haut){
			x=-playerX+resolutionWidth/2;
			y=0;
		}
		else if(bas){
			x=-playerX+resolutionWidth/2;
			y=resolutionHeight-mapHeight;
		}
		else {
			// haut gauche
			x=-playerX+resolutionWidth/2;
			y=-playerY+resolutionHeight/2;
		}
		return new Point((int)x, (int)y);
	}

	public static void scrollLayer(int resolutionWidth, int resolutionHeight, int layer){
		int x = (int) getDecalage(resolutionWidth, resolutionHeight).getX();
		int y = (int) getDecalage(resolutionWidth, resolutionHeight).getY();
		
		Exploration.getCurrMap().getTmap().render(x, y, layer);
	}

	
	public static void scrollOtherPlayers(Graphics g, Player p, int resolutionWidth, int resolutionHeight){
		int playerX = (int) p.getPosition().getX();
		int playerY = (int) p.getPosition().getY();
		
		int x = (int) getDecalage(resolutionWidth, resolutionHeight).getX();
		int y = (int) getDecalage(resolutionWidth, resolutionHeight).getY();
		
		p.render(g, x+playerX, y+playerY);
	}

	public static void scrollPlayer(Graphics g, int resolutionWidth, int resolutionHeight){
		float playerX = MainGame.getPlayer().getX();
		float playerY = MainGame.getPlayer().getY();
		
		int x = (int) getDecalage(resolutionWidth, resolutionHeight).getX();
		int y = (int) getDecalage(resolutionWidth, resolutionHeight).getY();
		
		MainGame.getPlayer().render(g, x+(int)playerX, y+(int)playerY);
	}

}
