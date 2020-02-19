
import java.net.MalformedURLException;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Scanner;

public class ChatClient implements ClientInterface{
  public void messageFromServer(String message) throws RemoteException{
        System.out.println(message);
    }

  public static void main(String[] args)
    throws MalformedURLException, RemoteException{

      String host = (args.length < 1) ? null : args[0];
      String username = "";

          try {
              Registry registry = LocateRegistry.getRegistry(host);
              ChatInterface stub = (ChatInterface) registry.lookup("Hello");

              String response = stub.helloTo();
              System.out.println(response);

              Scanner s = new Scanner(System.in);
              while (true) {
                  String msg=s.nextLine().trim();
                  String[] split_msg = msg.split("\\s+");

                  if (split_msg.length == 1) {
                      if (split_msg[0].equals("list")){
                          System.out.println(stub.list());
                      }

                      else if (split_msg[0].equals("quit")){
                          stub.quit(username);
                          System.exit(0);
                      }
                  }

                 else if (split_msg.length == 2 && split_msg[0].equals("register")){
                     username = split_msg[1];
                     ChatClient client = new ChatClient();
                     ClientInterface clientStub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);

                     System.out.println(stub.addClient(username, clientStub));
                 }


                  else if (split_msg[0].equals("send")){
                      String message = "";
                      for(int i = 2; i<split_msg.length-1; i++){
                          message+= split_msg[i] + " ";

                      }
                      message+= split_msg[split_msg.length-1];

                      stub.sendMessage(username, split_msg[1], message);
                  }

                  else {
                      System.out.println("You have entered an incorrect command."
                      + " Please try again! Here is a list of available commands"
                      + " for you to try:" +
                      "\n * register <username>: to join the system \n" +
                      " * list: view active IM clients \n" +
                      " * send <destination username> <message>: send an IM to a specific user \n" +
                      " * quit: close connection to server \n" +
                      ":-)");
                  }
              }

          } catch (Exception e) {
              System.err.println("Client exception: " + e.toString());
              e.printStackTrace();
          }

    }




}
