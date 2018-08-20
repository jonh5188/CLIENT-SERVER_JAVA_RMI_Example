import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote{
	
	File currentDir = new File(System.getProperty("user.dir"));
	
	// SEND LIST OF FILES IN CURRENT DIRECTORY OF THE SERVER
	public File[] list() {
		File[] paths = currentDir.listFiles();
		return paths;
	}
	
	// CHANGE CURRENT DIRECTORY ON THE SERVER
	public String cd(String s) {
		String str;
		String[] dir = s.split(" ", 2);
		String command = dir[0];
		String directory = dir[1];
		File tmp = new File(directory);
		if (tmp.exists()) {
			currentDir = new File(directory);
			str = directory;
		} else {
			str = "Directory does not exist on the server";
		}
		return str;
	}
	
	// LET THE CLIENT KNOW ITS DIRECTORY HAS CHANGED
	public String lcd(String s) {
		return s;
	}
	
	// SEND FILESIZE OF THE SPECIFIED FILENAME
	public Object size(String s) {
		File f = null;
		Object tmp;
		boolean bool = false;
		String[] fs = s.split(" ", 2);
		String command = fs[0];
		String filename = fs[1];
		String file = filename;
		f = new File(currentDir + "/" + file);
		bool = f.exists();
	    if(bool) {
	    	long len = f.length();
	        tmp = len;
	    } else {
	    	int none = -1;
	        tmp = none;
	    }	
	    return tmp;
	}
	
	// LET THE CLIENT KNOW THE CONNECTION HAS ENDED
	public String quit() {
		return "The connection with the Server has ended";
	}
	
	public MyRemoteImpl() throws RemoteException { }
	
	public static void main (String[] args) {
		try {
			MyRemote service = new MyRemoteImpl();
			Naming.rebind("RemoteHello", service);
			System.out.println("The Server is running and waiting for Client requests...");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
