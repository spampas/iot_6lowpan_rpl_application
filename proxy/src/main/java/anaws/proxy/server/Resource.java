package anaws.proxy.server;

import java.io.IOException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;

import anaws.utilities.Utilities;
import anaws.utilities.parameters.Parameters;

public class Resource extends CoapResource {
	
	String[] resource;
	int N;
	
	public static void main(String args[]) {
		
		String[] array = new String[Parameters.MOTE_NUMBER];
		Resource res = new Resource("database",array,Parameters.MOTE_NUMBER);
		
		res.delete();
		System.out.println("[INFO] Resource deleted");
		
	}
	
	public Resource(String name, String[] array, int size) {
		
		super(name);
		resource = array;
		N = size;
		getAttributes().setTitle("Database Resource");
		setObservable(false);
		
		System.out.println("[INFO] Resource created");
		
	}
	
	public void handleGET(CoapExchange exchange) {
		
		Response response = null;
		
		try {
			if(exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {
	
					String value = exchange.getQueryParameter("mote");
				
					int id_mote = Integer.parseInt(value);
					
					if(id_mote > 0 && id_mote <= N) {
						
						response = new Response(ResponseCode.CONTENT);
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						response.setPayload(resource[id_mote - 1]);
						System.out.println("[INFO] Resource sent to the client");
						
					}else{
						response = new Response(ResponseCode.NOT_ACCEPTABLE);
						response.setPayload(Utilities.setErrorSenML("Node ID not valid"));
						System.out.println("[ERR] Client sent wrong Node ID (" + value + ")");
					}
				
			}else{
				response = new Response(ResponseCode.NOT_ACCEPTABLE);
				response.setPayload(Utilities.setErrorSenML("Supporting content-types application json"));
				System.out.println("[ERR] Client sent wrong type (" + exchange.getRequestOptions().getAccept() + ")");
			}
				
			exchange.respond(response);
		
		}catch(IOException ioe){
			
			Response error = new Response(ResponseCode.NOT_ACCEPTABLE);
			error.setPayload("{\"Error\":\"Input not valid\"}");
			System.out.println("[EXC] Client sent wrong input");
			exchange.respond(error);
			
		}
		
	}
	
}