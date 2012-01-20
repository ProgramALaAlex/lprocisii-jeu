package constantes;

import ingame.Teleporter;

import java.util.ArrayList;

public final class Constantes {
	public static final int GAMEPLAY_MAP_STATE = 0;
	public static final int MENU_MAP_STATE = 1;
	public static final int COMBAT_STATE = 2;
	
	public static final String CHAR_LOCATION = "res/graphics/char/";
	public static final String MONSTER_LOCATION = "res/graphics/monstres/";
	public static final String MAP_LOCATION = "res/map/";
	public static final int EVENT_ANIM_DEFAUT_DURATION = 200;
	public static final int BLOCK_SIZE = 32;
	
	public static final int HAUT=8;
	public static final int BAS=2;
	public static final int GAUCHE=4;
	public static final int DROITE=6;
	
	//téléporteurs, car codés en dur
	public static final TPList LISTE_TP = new TPList();
	
	public static final String SELECTION = "> ";
}
