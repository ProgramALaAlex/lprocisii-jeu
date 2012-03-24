package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRemoteInterface extends Remote {

    public String addClient(ChatReceiverInterface client) throws RemoteException;
	public String getMessage(String msg, ChatReceiverInterface client) throws RemoteException;
	public void init(String nouveauNom, String ancienNom) throws RemoteException;
	
}
