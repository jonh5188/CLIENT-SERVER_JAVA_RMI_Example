import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.*;
import java.util.Scanner;

public class MyRemoteClient {
	
	public static void main(String[] args) {
		new MyRemoteClient().go();
	}
	public void go() {
		Scanner in = new Scanner(System.in);
    	System.out.print("Hostname: ");
    	String hostname = in.next();
		try {
			MyRemote service = (MyRemote) Naming.lookup("rmi://" + hostname + "/RemoteHello");
			System.out.println("Enter a command");
			File currentDir = new File(System.getProperty("user.dir"));
			while (true) {
		    	String cmd = in.nextLine();
		    	
		    	// LIST ALL FILES IN THE CURRENT DIRECTORY ON THE SERVER
		    	if (cmd.equals("LIST")) {
		    		File request = new File(cmd);
		    		File[] f = new File[1];
		    		f[0] = request;
		    		f = service.list();
		    		String file;
		    		if (f.length == 0 || f == null) {
	    				System.out.println("No files exist in current directory");
	    			}
		    		for (File path:f) {
		    			if (path.isFile()) {
		    				file = path.getName();
		    				System.out.println(file);
		    			}
		    		} System.out.println();
		    	}
		    	
		    	// CHANGE THE SERVER DIRECTORY
		    	if (cmd.startsWith("CD")) {
		      		String c = cmd;
		      		c = service.cd(cmd);
		    		System.out.println("Current Directory: " + c);
		    		System.out.println();
		      	}
		    	
		    	// CHANGE THE CLIENT DIRECTORY
		    	if (cmd.startsWith("LCD")) {
		    		String[] dir = cmd.split(" ", 2);
					String command = dir[0];
					String directory = dir[1];
					File tmp = new File(directory);
					if (tmp.exists()) {
					currentDir = new File(directory);
					String cur = directory;
					cur = service.lcd(directory);
					System.out.println("Current Directory: " + cur);
					System.out.println();
					} else {
						System.out.println("Current Directory: Directory does not exist");
						System.out.println();
					}
		    	}
		    	
		    	// RECEIVE THE FILE SIZE
		    	if (cmd.startsWith("SIZE")) {
		    		Object s = cmd;
		    		s = service.size(cmd);
		    		System.out.println("File Size: "+s+" bytes");
		    		System.out.println();
		    	}
		    	
		    	// CLOSE THE CLIENT CONNECTION
				if (cmd.equals("QUIT")) {
		    		String q = cmd;
		    		q = service.quit();
		    		break;
				}
			}
			System.out.println("The connection to the Server has ended");
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		in.close();
	}
}
