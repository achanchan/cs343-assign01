import java.net.MalformedURLException;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ChatClient {

  public static void main(String[] args)
    throws MalformedURLException, RemoteException{

      String host = (args.length < 1) ? null : args[0];

          try {
              Registry registry = LocateRegistry.getRegistry(host);
              ChatInterface stub = (ChatInterface) registry.lookup("Hello");
              // String response = stub.helloTo();
              // System.out.println("response: " + response);
          } catch (Exception e) {
              System.err.println("Client exception: " + e.toString());
              e.printStackTrace();
          }

    }

}
