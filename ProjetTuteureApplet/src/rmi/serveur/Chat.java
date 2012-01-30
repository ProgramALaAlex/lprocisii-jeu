package rmi.serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

import rmi.interfaces.ChatReceiverInterface;
import rmi.interfaces.ChatRemoteInterface;



public class Chat implements ChatRemoteInterface
{

	private ArrayList<ChatReceiverInterface> clients = new ArrayList<ChatReceiverInterface>();
	private ArrayList<String> name = new ArrayList<String>();

	private int idClient(ChatReceiverInterface client){
		for(int i = 0; i < clients.size(); i++){
			if(client.toString().compareTo(clients.get(i).toString()) == 0)
				return i;
		}
		return -1;
	}
	
	private String createUniKey(){
		return UUID.randomUUID().toString();
	}
	
    public String getMessage(String msg, ChatReceiverInterface client)
    {
    	System.out.println("onche");
		String debut = msg.substring(0,1);
		if(debut.compareTo("/") == 0){
			String[] cmd = msg.split(" ");
			String c = cmd[0].substring(1, cmd[0].length());
			
			if(c.compareTo("nick") == 0){
				if(cmd.length >= 2)
					changeName( cmd[1], client);
			}else if(c.compareTo("w") == 0){
				System.out.println("Message prive");
			}else{
				System.out.println("Cmd inconnue");
			}
			return "Cmd";
		}
		
        System.out.println( "MSG: "+msg);
		int idcl = idClient( client );
		if(idcl != -1){
			for(int i = 0; i < clients.size(); i++){
				try{
					clients.get(i).afficheMsg(msg, name.get(idcl));
				}catch (Exception e){
					System.out.println( "erreur client: "+i);
				}
			}
		}
        return "Message recu";
    }
	
	public void addClient(ChatReceiverInterface client) {
		String user = "user"+clients.size();
		System.out.println("Ajout d'un client nommé: "+user);
		System.out.println( createUniKey() );
		clients.add(client);
		name.add(user);
	}
	
	private String changeName( String newName, ChatReceiverInterface client){
		int id = idClient(client);
		if(id != -1){
			for(int i = 0; i < name.size(); i++){
				if( newName.compareTo(name.get(i)) == 0){
					System.out.println("Ce nom d'utilisateur est deja pris");
					return "Ce nom d'utilisateur est deja pris";
				}
			}
			System.out.println(name.get(id)+" se nomme maintenant "+newName);
			name.set(id, newName);
			return "Rename effectuee";
		}
		return "Erreur fonction change nom";
	}
	
}
