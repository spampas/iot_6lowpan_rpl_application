package proxy.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;


import utilities.*;

public class Client extends CoapClient {
	
	String[] resource;
	int id;
	int connection_attempts;
	
	CoapClient client;
	CoapHandler handler;
	CoapObserveRelation relation;
	
	public Client(int cid, String address, String[] array){
		
		resource = array;		// Accedo alla sola risorsa del client
		id = new Integer(cid);
		client = new CoapClient("coap://[" + address + "]/" + Parameters.PATH);
		System.out.println("[INFO] ClientID=" + id + " bind with " + address + " address");
		client.setTimeout(5000);
		
		handler = new CoapHandler() {
			
			public void onLoad(CoapResponse response) {
				
					System.out.println("[INFO] ClientID=" + id + " observes from MoteID=" + Integer.toString(id + 1));
					
					connection_attempts = 0;
					
					if(response.getCode() == ResponseCode.NOT_ACCEPTABLE)
						
						Utilities.fromSenMLToText(Integer.toString(id+ 1),response.getResponseText());
					
					else {
						
						resource[id] = response.getResponseText();
						if(Parameters.DEBUG)
							System.out.println("[DEBUG] MoteID gave value:\n" + resource[id] );
						} 
				
				}
			
			public void onError() {
				
				if(connection_attempts <= Parameters.ATTEMPTS) {
					System.err.println("[INFO] MoteID=" + Integer.toString(id + 1) + " unreachable. ClientID=" + id + " tries again to observe from MoteID=" + Integer.toString(id + 1));
					relation = client.observe(handler);
					connection_attempts++;
				} else {
					System.err.println("[ERR] ClientID=" + id + " stopped observing node with MoteID=" + Integer.toString(id + 1));
				}
			
			}
			
		};
		
		connection_attempts = 0;
		
		getObserve();
		
	}
	
	void deleteObserve() {
		
		relation.proactiveCancel();
		
	}
	
	void getObserve() {
			
		relation = client.observe(handler);
	
	}
	
}