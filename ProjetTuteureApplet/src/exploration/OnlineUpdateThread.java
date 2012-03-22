package exploration;

import game.MainGame;
import game.Player;

import java.rmi.RemoteException;
import java.util.Vector;

public class OnlineUpdateThread extends Thread {
	private Vector<Player> listeJoueurLoc;

	public OnlineUpdateThread(Vector<Player> listeJoueurLoc){
		this.listeJoueurLoc = listeJoueurLoc;
	}

	public Vector<Player> getListeJoueurLoc(){
		return listeJoueurLoc;
	}
	
	@Override
	public void run(){
		while(true){
				try {
					MainGame.updateListeJoueur();
					MainGame.getRemoteReference().updatePosition(MainGame.getPlayer());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

}
