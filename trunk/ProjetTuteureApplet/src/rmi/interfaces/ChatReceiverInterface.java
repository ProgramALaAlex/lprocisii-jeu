package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatReceiverInterface extends Remote {
	  public void afficheMsg(String message, String user) throws RemoteException;
}
