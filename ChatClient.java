import java.net.MalformedURLException;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Scanner;

public class ChatClient {



  public static void main(String[] args)
    throws MalformedURLException, RemoteException{

      String host = (args.length < 1) ? null : args[0];

          try {
              Registry registry = LocateRegistry.getRegistry(host);
              ChatInterface stub = (ChatInterface) registry.lookup("Hello");

              Scanner s = new Scanner(System.in);
              System.out.println("Enter your name:");
  	    	  String name =s.nextLine().trim();

              String response = stub.helloTo(name);
              System.out.println("response: " + response);

              while (true) {
                  String msg=s.nextLine().trim();
                  String[] split_msg = msg.split("\\s+");

                  if (split_msg[0].equals("register")){
                      stub.addClient(split_msg[1]);
                  }

                  else if (split_msg[0].equals("list")){
                      stub.list();
                  }

              }

          } catch (Exception e) {
              System.err.println("Client exception: " + e.toString());
              e.printStackTrace();
          }

    }




}
