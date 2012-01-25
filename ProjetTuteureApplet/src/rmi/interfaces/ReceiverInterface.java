package rmi.interfaces;

import ingame.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;

import org.newdawn.slick.Animation;

/**
 * Les méthode que le serveur va appeler à dsitance.
 */
public interface ReceiverInterface extends Remote
{

    public float getX() throws RemoteException;
    public float getY() throws RemoteException;
    public int getDirection() throws RemoteException;
    public UID getUserId() throws RemoteException;
    public String getSpriteSheetName() throws RemoteException;
    public String getMapId() throws RemoteException;
    
    
}
