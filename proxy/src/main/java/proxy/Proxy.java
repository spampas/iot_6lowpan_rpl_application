package proxy;

import client.Client;
import server.Server;
import utilities.Parameters;

public class Proxy {
	
	Server CoAPServer;
	Client[] CoAPClients;
	
	String[] array;	// Cache

	public static void main(String args[]) {
		
		System.out.println("Proxy is starting...");
		
		start(args);

		System.out.println("Proxy started...");
		
	}
	
	Proxy(int mote){
		
		array = new String[Parameters.MOTE_NUMBER];
		CoAPServer = new Server(array);
		Client[] CoAPClients = new Client[Parameters.MOTE_NUMBER];
		
		int address;
		String addr;
		for (int i = 0; i < Parameters.MOTE_NUMBER; i++) {
			
			address =  i + Parameters.START_INTERFACE_ID;
			addr = Integer.toHexString(address);
			
			if(mote == 0) {	// Z1
				
				CoAPClients[i] = new Client(i, Parameters.PREFIX_Z1 + addr, array);
				
			}else {			// COOJA node
			
				if(address < 16) 
					CoAPClients[i] = new Client(i, Parameters.PREFIX_COOJA + "0" + addr + ":" + addr + ":" + addr + ":" + addr, array);
				else
					CoAPClients[i] = new Client(i, Parameters.PREFIX_COOJA + addr + ":" + addr + ":" + addr + ":" + addr, array);
				
			}
			
		}
		
	}
	
	static void start(String[] args) {
		
		switch(args.length) {
		
			case 1:
				if(args[0].equals("z1"))
					new Proxy( 0);
				else if(args[0].equals("cooja"))
					new Proxy(1);
				else {
					System.err.println("[ERR] Proxy requires 1 mandatory arg: the type of mote used (z1 or cooja)");
					System.exit(-1);
				}
				break;			
				
			case 2:
				Parameters.MOTE_NUMBER = Integer.parseInt(args[1]);
				if(args[0].equals("z1"))
					new Proxy(0);
				else if(args[0].equals("cooja"))
					new Proxy(1);
				else {
					System.err.println("[ERR] Proxy requires 2 mandatory args: the number of motes and the type of mote used (z1 or cooja)");
					System.exit(-1);
				}
				
				break;
				
			default:
				System.out.println("[ERR] Arguments not valid");
				System.exit(-1);
		}
		
	}
	
}