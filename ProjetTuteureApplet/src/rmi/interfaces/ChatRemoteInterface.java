package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRemoteInterface extends Remote {

    public void addClient(ChatReceiverInterface client, String pseudo) throws RemoteException;
	public String getMessage(String msg, ChatReceiverInterface client) throws RemoteException;
}
