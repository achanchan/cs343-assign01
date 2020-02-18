import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote{
  public String helloTo() throws RemoteException;
  public String addClient(String username, ClientInterface newClient) throws RemoteException;
  public String list() throws RemoteException;
  public void quit(String username) throws RemoteException;
}
