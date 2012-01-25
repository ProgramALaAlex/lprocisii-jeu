package constantes;

import ingame.Teleporter;

import java.util.ArrayList;

public final class Constantes {
	// NE PAS TOUCHER
	public static final int GAMEPLAY_MAP_STATE = 0;
	public static final int MENU_MAP_STATE = 1;
	public static final int COMBAT_STATE = 2;
	
	// DEBUG : FALSE POUR TESTER EN SOLO
	public static boolean MODE_ONLINE = true;
	
	// ressources
	public static final String CHAR_LOCATION = "res/graphics/char/";
	public static final String MONSTER_LOCATION = "res/graphics/monstres/";
	public static final String MAP_LOCATION = "res/map/";
	public static final String BATTLE_BACKGROUND_LOCATION = "res/graphics/battle/";
	
	// exploration - animation & direction
	public static final int EVENT_ANIM_DEFAUT_DURATION = 200;
	public static final int BLOCK_SIZE = 32;
	public static final int HAUT=8;
	public static final int BAS=2;
	public static final int GAUCHE=4;
	public static final int DROITE=6;
	public static final float VITESSE_DEPLACEMENT=0.1f;
	
	//téléporteurs (car codés en dur)
	public static final TPList LISTE_TP = new TPList();
	
	//combats
	public static final String SELECTION = "> ";
	public static final int POSX_COMBAT_MONSTRE = 100;
	public static final int POSX_ATTAQUE_MONSTRE = 150;
	public static final int HAUTEUR_MENU_BAS_COMBAT = 70;
	
	//online - changer les commentaires si non local
//	public static final String IP_SERVEUR = "78.230.68.159";
	public static final String IP_SERVEUR = "localhost";
	public static final String REGISTRY_NAME = "RMI_JEU";
	public static final int REGISTRY_PORT = 25565;
}
