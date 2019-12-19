package anaws.proxy;

import anaws.proxy.server.Server;
import anaws.proxy.client.Client;

import anaws.utilities.parameters.Parameters;

public class Proxy {
	
	Server CoAPServer;
	Client[] CoAPClients;
	
	String[] array;	// Cache

	public static void main(String args[]) {
			
		new Proxy(Parameters.MOTE_NUMBER);
		System.out.println("Proxy started...");
			
	}
	
	Proxy(int n){
		
		array = new String[n];
		CoAPServer = new Server(array,n);
		Client[] CoAPClients = new Client[n];
		
		int address;
		String addr;
		for (int i = 0; i < n; i++) {
			
			address =  i + Parameters.START_INTERFACE_ID;
			addr = Integer.toHexString(address);
			
			if(address < 16) 
				CoAPClients[i] = new Client(i, Parameters.PREFIX + "0" + addr + ":" + addr + ":" + addr + ":" + addr, array);
			else
				CoAPClients[i] = new Client(i, Parameters.PREFIX + addr + ":" + addr + ":" + addr + ":" + addr, array);
			
		}
		
	}
	
}