import java.io.File;
import java.io.IOException;
import java.rmi.*;

public interface MyRemote extends Remote{
	
	public File[] list() throws RemoteException;
	
	public String quit() throws RemoteException;
	
	public Object size(String s) throws RemoteException;
	
	public String cd(String s) throws RemoteException;
	
	public String lcd(String s) throws RemoteException;
	
	// public String get(String s) throws RemoteException;
	
	// public String put(String s) throws RemoteException;
}
