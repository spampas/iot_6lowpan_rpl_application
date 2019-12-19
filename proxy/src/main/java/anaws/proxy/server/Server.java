package anaws.proxy.server;

import org.eclipse.californium.core.CoapServer;

import anaws.utilities.parameters.Parameters;

public class Server extends CoapServer {
	
	int size;
	
	public static void main(String args[]) {
		
		String[] array = new String[Parameters.MOTE_NUMBER];
		for(int i = 0; i < Parameters.MOTE_NUMBER; i++)
			array[i] = "a" + i;
		
		new Server(array, Parameters.MOTE_NUMBER);
		
	}
	
	public Server(String[] array, int size) {
		
		this.size = size;
		add(new Resource("database",array,size));
		start();
		
		System.out.println("[INFO] Server started...");
		
	}
	
	
}
