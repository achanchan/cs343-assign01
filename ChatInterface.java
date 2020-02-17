import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote{
  public String helloTo(String name) throws RemoteException;
  public String addClient(String username) throws RemoteException;
}
