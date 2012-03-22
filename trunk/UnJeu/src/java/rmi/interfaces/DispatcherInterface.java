package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.ArrayList;



/**
 * Les méthodes qu'on appelle du serveur.
 */
public interface DispatcherInterface extends Remote
{
    public void testSysout() throws RemoteException;
}
