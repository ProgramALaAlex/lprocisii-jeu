package exploration;


import org.newdawn.slick.Graphics;


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

	public static void scrollPlayer(Graphics g, int playerX, int playerY, int resolutionWidth, int resolutionHeight, Player player, Map map){
		int mapHeight = map.getTmap().getHeight()*map.getTmap().getTileHeight();
		int mapWidth = map.getTmap().getWidth()*map.getTmap().getTileWidth();
		boolean gauche = playerX-resolutionWidth/2<0;
		boolean droite = playerX+resolutionWidth/2>mapWidth;
		boolean haut = playerY-resolutionHeight/2<0;
		boolean bas = playerY+resolutionHeight/2>mapHeight;

		if (gauche && haut){
			player.render(g, playerX,playerY);
		}
		else if (gauche && bas){
			player.render(g, playerX, playerY-mapHeight+resolutionHeight);
		}
		else if (haut && droite){
			player.render(g, resolutionWidth-mapWidth+playerX, playerY);
		}
		else if (bas && droite){
			player.render(g, resolutionWidth-mapWidth+playerX, playerY-mapHeight+resolutionHeight);
		}
		else if(gauche){
			player.render(g, playerX, resolutionHeight/2);
		}
		else if (droite){
			player.render(g, resolutionWidth-mapWidth+playerX, resolutionHeight/2);
		}
		else if(haut){
			player.render(g, resolutionWidth/2, playerY);
		}
		else if(bas){
			player.render(g, resolutionWidth/2, resolutionHeight-mapHeight+playerY);
		}
		else {
			// haut gauche
			player.render(g, resolutionWidth/2, resolutionHeight/2);
		}
	}

	
	public static void scrollOtherPlayers(Graphics g, Player p, int positionLocaleX, int positionLocaleY, int resolutionWidth, int resolutionHeight, Map map){
		int mapHeight = map.getTmap().getHeight()*map.getTmap().getTileHeight();
		int mapWidth = map.getTmap().getWidth()*map.getTmap().getTileWidth();
		boolean gauche = positionLocaleX-resolutionWidth/2<0;
		boolean droite = positionLocaleX+resolutionWidth/2>mapWidth;
		boolean haut = positionLocaleY-resolutionHeight/2<0;
		boolean bas = positionLocaleY+resolutionHeight/2>mapHeight;
		int playerX = (int) p.getPosition().getX();
		int playerY = (int) p.getPosition().getY();
		Player player = p;

		if (gauche && haut){
			player.render(g, playerX, playerY);
		}
		else if (gauche && bas){
			player.render(g, playerX, playerY-mapHeight+resolutionHeight);
		}
		else if (haut && droite){
			player.render(g, playerX+resolutionWidth-mapWidth, playerY);
		}
		else if (bas && droite){
			player.render(g, playerX+resolutionWidth-mapWidth, playerY-mapHeight+resolutionHeight);
		}
		else if(gauche){
			player.render(g, playerX, playerY-positionLocaleY+resolutionHeight/2);
		}
		else if (droite){
			player.render(g, playerX+resolutionWidth-mapWidth, playerY-positionLocaleY+resolutionHeight/2);
		}
		else if(haut){
			player.render(g, playerX-positionLocaleX+resolutionWidth/2, playerY);
		}
		else if(bas){
			player.render(g, playerX-positionLocaleX+resolutionWidth/2, playerY+resolutionHeight-mapHeight);
		}
		else {
			// haut gauche
			player.render(g, playerX-positionLocaleX+resolutionWidth/2, playerY-positionLocaleY+resolutionHeight/2);
		}
	}

}
