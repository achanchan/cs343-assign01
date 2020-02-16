import java.net.MalformedURLException;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Scanner;

public class ChatServer extends UnicastRemoteObject implements ChatInterface{
  private Vector<String> users;
  private String name;

  //Constructor
  public ChatServer(String user) throws RemoteException{
	super();
	name = user;
    users = new Vector<String>();
  }

  public static void main(String args[]) {

        try {
            Scanner s=new Scanner(System.in);
            System.out.println("Enter your username:");
	    	String name=s.nextLine().trim();
			ChatServer chat = new ChatServer(name);

			ChatInterface stub = (ChatInterface) UnicastRemoteObject.exportObject(chat, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");

			// while(true){
			//
			//
			// }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /*
        helloTo(String name) Server says hello to client and tells them what
        functions they can use
    */
    public String helloTo(String name) throws RemoteException{

        System.err.println(name + " is trying to contact!");

        String availableFunctions =
        "\n * register <username>: to join the system \n" +
        " * list: view active IM clients \n" +
        " * send <destination username> <message>: send an IM to a specific user \n" +
        " * quit: close connection to server \n";

        return "Server says hello to " + name + availableFunctions;
    }

}
