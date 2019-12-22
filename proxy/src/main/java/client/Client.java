package client;

import java.util.Scanner;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.Code;

import utilities.*;

import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;

public class Client extends CoapClient {
	
	String[] resource;
	int id;
	
	CoapClient client;
	CoapObserveRelation relation;
	
	public static void main(String args[]) {
		
		int N = Parameters.MOTE_NUMBER;
		CoapClient client = new CoapClient("coap://127.0.0.1/database");
		client.setTimeout(3000);
		
		System.out.println("[INFO] Node ID have an ID between 1 and " + N);
		System.out.println("Are you looking for what a node is doing? Digit Node ID or 'bye' to close the client application.");		

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
        		System.out.println("[WARN] You have to insert a mote ID between 1 and " + Parameters.MOTE_NUMBER); /////
        	}
		}
		
	}
	
	public Client(int cid, String address, String[] array){
		
		resource = array;		// Accedo alla sola risorsa del client
		id = new Integer(cid);
		client = new CoapClient("coap://[" + address + "]/" + Parameters.PATH);
		System.out.println("[INFO] ClientID=" + id + " bind with " + address + " address");
		client.setTimeout(5000);
		
		getObserve();
		
	}
	
	void deleteObserve() {
		
		relation.proactiveCancel();
		
	}
	
	void getObserve() {
		
		relation = client.observe(new CoapHandler() {
			
			public void onLoad(CoapResponse response) {
				
				
				System.out.println("[INFO] ClientID=" + id + " observes from Node ID=" + Integer.toString(id + 1));
				resource[id] = response.getResponseText(); // ERRORE!!!!!: AGGIUNGERE CONTROLLO
				if(Parameters.DEBUG)
					System.out.println("[DEBUG] MoteID gave value=" + resource[id] );
				
				/*String content = response.getResponseText();
				System.out.println("[INFO] Client ID=" + id + " notified from Node ID="+ Integer.toString(id+1) +": "+ content); // response.advanced().getSource() addr
				fromJsonToInt(content);*/
			}
			
			public void onError() {
				System.err.println("[ERR] ClientID=" + id + " stopped observing");
			}
			
		});
		
	}
	
}