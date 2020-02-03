package client;

import java.util.Scanner;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;

import utilities.*;

public class Client extends CoapClient {
	
	
	public static void main(String args[]) {
		
		CoapClient client = new CoapClient("coap://127.0.0.1/database");
		client.setTimeout(3000);
		
		System.out.println("[INFO] Node ID have an ID between 1 and " + Parameters.MOTE_NUMBER);
		System.out.println("Type a node ID to get its value or 'bye' to close the application.");		

		while(true){
			
            System.out.print(">> ");
			Scanner input = new Scanner(System.in);
			
            String value = input.nextLine();
            
            if(value.compareTo("bye") == 0) {
            	System.out.println("Client closed");
            	input.close();
            	System.exit(0);
            }
			
            try {
            	
				Request req = new Request(Code.GET);
				req.getOptions().addUriQuery("mote=" + value);
				req.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
				CoapResponse response = client.advanced(req);
				
				if(response != null)
					Utilities.fromSenMLToText(value, response.getResponseText());
				else
					System.out.println("[WARN] Server is not responding...");
           
            } catch(NumberFormatException nfe) {
        		System.out.println("[WARN] You have to insert a mote ID between 1 and " + Parameters.MOTE_NUMBER);
        	}
		}
		
	}	
}