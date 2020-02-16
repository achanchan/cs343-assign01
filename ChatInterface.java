import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote{
  String helloTo(String name) throws RemoteException;
}
