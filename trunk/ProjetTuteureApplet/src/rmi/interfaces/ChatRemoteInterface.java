package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRemoteInterface extends Remote {

    public String getMessage(String msg, ChatReceiverInterface client) throws RemoteException;
	public void addClient(ChatReceiverInterface client) throws RemoteException;
}
