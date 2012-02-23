package exploration;

import game.MainGame;
import game.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;

import constantes.Constantes;

public class OnlineUpdateThread extends Thread {
	private ArrayList<Player> listeJoueurLoc;

	public OnlineUpdateThread(ArrayList<Player> listeJoueurLoc){
		this.listeJoueurLoc = listeJoueurLoc;
	}

	public void run(){
		while(true){
				try {
					MainGame.updateListeJoueur();
					MainGame.getRemoteReference().updatePosition(MainGame.getPlayer());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				try {
					OnlineUpdateThread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
	
	public ArrayList<Player> getListeJoueurLoc(){
		return listeJoueurLoc;
	}

}
