package anaws.proxy.client;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;

import anaws.utilities.Utilities;
import anaws.utilities.parameters.Parameters;

public class Client extends CoapClient {
	
	String[] resource;
	int id;
	
	CoapClient client;
	CoapObserveRelation relation;
	
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
		
		relation = client.observe( new CoapHandler() {
			
								public void onLoad(CoapResponse response) {
									System.out.println("[INFO] Client ID=" + id + " observes from Node ID="+ id + 1); // response.advanced().getSource() addr
									resource[id] = response.getResponseText();
												 
								}
			
								public void onError() {
									System.err.println("[ERR] Client ID=" + id + " stopped observing");
								}
			
								});
		
	}
	    
}
	
