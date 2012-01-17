package constantes;

import ingame.Teleporter;

import java.util.ArrayList;

public final class Constantes {
	public static final int GAMEPLAY_MAP_STATE = 0;
	public static final int MENU_MAP_STATE = 1;
	
	public static final String CHAR_LOCATION = "res/graphics/char/";
	public static final String OBJECT_LOCATION = "res/graphics/objects/";
	public static final String MAP_LOCATION = "res/map/";
	public static final int EVENT_ANIM_DEFAUT_DURATION = 200;
	public static final int BLOCK_SIZE = 32;
	
	//téléporteurs, car codés en dur
	public static final TPList LISTE_TP = new TPList();
}
