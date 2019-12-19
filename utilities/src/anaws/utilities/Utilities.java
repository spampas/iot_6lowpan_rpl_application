package anaws.utilities;

import java.io.IOException;

import teamethernet.senmlapi.Label;
import teamethernet.senmlapi.SenMLAPI;

public class Utilities {
	
	public static void fromSenMLToText(String value, String json){
		
		try {
			SenMLAPI senMLAPI = SenMLAPI.initJson(json.getBytes());
			if(senMLAPI.getValue(Label.NAME, 0).equals("error")) {
				System.out.println("[ERR]" + senMLAPI.getValue(Label.STRING_VALUE, 0));
			}
			else {
	    	System.out.println("Mote " + value + " gave value: " + senMLAPI.getValue(Label.VALUE, 0).toString());
			}
		}
		catch(IOException ioe) {
	    	System.out.println("[ERR] Something went wrong extracting from SenML);");

		}
			
	}
	
	public static int fromSenMLToInt(String json) throws IOException{

		SenMLAPI senMLAPI = SenMLAPI.initJson(json.getBytes());
    	int result = (int)senMLAPI.getValue(Label.VALUE, 0);
    	return result;
	
	}
	
	public static String setErrorSenML(String error) throws IOException {
		
		SenMLAPI senMLAPI = SenMLAPI.initJson();
		senMLAPI.addRecord(Label.NAME.attachValue("error"), Label.STRING_VALUE.attachValue(error));
		return new String(senMLAPI.getSenML());
	}
	
	public static void main(String[] args) {
		try {
		SenMLAPI senMLAPI = SenMLAPI.initJson();
		senMLAPI.addRecord(Label.NAME.attachValue("value"), Label.UNIT.attachValue("A"), Label.VALUE.attachValue(1.2));
		System.out.println(new String(senMLAPI.getSenML()));
		byte[] json = senMLAPI.getSenML();
		
		SenMLAPI senMLAPI2 = SenMLAPI.initJson(json);
		double v = (double)senMLAPI2.getValue(Label.VALUE, 0);
		
		System.out.println(v);

		}catch(IOException e) {
			
		}

	}

}
