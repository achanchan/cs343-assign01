import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.util.Vector;
import java.util.Scanner;
import java.util.Arrays;

public class ChatServer implements ChatInterface{
  //private Vector<User> users;
  private Vector<String> users;
  //private String name;

  //Constructor
  public ChatServer() throws RemoteException{
	super();
	//users = new Vector<User>();
	users = new Vector<String>();
  }

  public static void main(String args[]) {

        try {
            // Scanner s=new Scanner(System.in);
            // System.out.println("Enter your username:");
	    	// String name=s.nextLine().trim();

			ChatServer chat = new ChatServer();
			ChatInterface stub = (ChatInterface) UnicastRemoteObject.exportObject(chat, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /*
        helloTo(String name) Server says hello to client and tells them what
        functions they can use
    */
    public String helloTo() throws RemoteException{

        String availableFunctions =
        "\n * register <username>: to join the system \n" +
        " * list: view active IM clients \n" +
        " * send <destination username> <message>: send an IM to a specific user \n" +
        " * quit: close connection to server \n";

        return "Welcome to the chat server! Here is a list of available functions for you to try: \n"
				+ availableFunctions;
    }

	/*
		addClient(String username) checks to see if username exists in
		client vector. If it doesn't, it adds the username to the vector.
		If it does, it returns an error message to the client.
	*/
	public String addClient(String username, ClientInterface newClient) throws RemoteException{

		try{
		if (users.contains(username)) {
			return ("Username already exists. Please retry!");
		}
		else {
			//User newUser = new User(username, newClient);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind(username, newClient);
			users.add(username);
			String[] names = Naming.list(username);
			System.out.println(Arrays.toString(names));
			return ("Ok!");
		}
	}
	catch(Exception e){
		System.err.println("Exception");
		return (e.toString());
	}
	}

	/*
		list() returns a list of all active users.
	*/
	public String list() throws RemoteException{
		return users.toString();
	}

	/*
		quit(String username) allows Client to leave the Server.
	*/
	public void quit(String username) throws RemoteException{
		if (users.contains(username)){
			users.remove(username);
			
			Registry registry = LocateRegistry.getRegistry();
			registry.unbind(username); 
			String[] names = Naming.list(username);
			System.out.println(Arrays.toString(names));
		}
	}

}
