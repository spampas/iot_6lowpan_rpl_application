package server;

import org.eclipse.californium.core.CoapServer;

import utilities.Parameters;

public class Server extends CoapServer {
	
	public static void main(String args[]) {
		
		String[] array = new String[Parameters.MOTE_NUMBER];
		for(int i = 0; i < Parameters.MOTE_NUMBER; i++)
			array[i] = "a" + i;
		
		new Server(array);
		
	}
	
	public Server(String[] array) {
		
		add(new Resource("database",array));
		start();
		
		System.out.println("[INFO] Server started...");
		
	}	
	
}