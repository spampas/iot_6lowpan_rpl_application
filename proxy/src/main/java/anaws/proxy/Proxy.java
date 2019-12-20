package anaws.proxy;

import anaws.proxy.server.Server;
import anaws.proxy.client.Client;

import anaws.utilities.parameters.Parameters;

public class Proxy {
	
	Server CoAPServer;
	Client[] CoAPClients;
	
	String[] array;	// Cache

	public static void main(String args[]) {
		
		if(args.length == 1) {
			if(args[0].equals("z1")) {
				new Proxy(Parameters.MOTE_NUMBER, 0);
				System.out.println("Proxy started...");
			}
			else if(args[0].contentEquals("cooja")) {
				new Proxy(Parameters.MOTE_NUMBER, 1);
				System.out.println("Proxy started...");
			}
			else {
				System.err.println("proxy requires 1 mandatory args: the type of mote used (z1 or cooja)");
				return;
			}
		}
		else if(args.length == 2){
			if(args[0].equals("z1")) {
				new Proxy(Integer.parseInt(args[1]), 0);
				System.out.println("Proxy started...");
			}
			else if(args[0].contentEquals("cooja")) {
				new Proxy(Integer.parseInt(args[1]), 1);
				System.out.println("Proxy started...");
			}
			else {
				System.err.println("proxy requires 1 mandatory args: the type of mote used (z1 or cooja)");
				return;
			}	
		}
		System.err.println("proxy requires 1 mandatory args: the type of mote used (z1 or cooja)");
	}
	
	Proxy(int n, int mote){
		
		array = new String[n];
		CoAPServer = new Server(array,n);
		Client[] CoAPClients = new Client[n];
		
		int address;
		String addr;
		for (int i = 0; i < n; i++) {
			
			address =  i + Parameters.START_INTERFACE_ID;
			addr = Integer.toHexString(address);
			if(mote == 0) {
				CoAPClients[i] = new Client(i, Parameters.PREFIX_Z1 + addr, array);
			}
			else {
				if(address < 16) {
					CoAPClients[i] = new Client(i, Parameters.PREFIX_COOJA + "0" + addr + ":" + addr + ":" + addr + ":" + addr, array);
				}
				else {
					CoAPClients[i] = new Client(i, Parameters.PREFIX_COOJA + addr + ":" + addr + ":" + addr + ":" + addr, array);
				}
			}
			
		}
		
	}
	
}