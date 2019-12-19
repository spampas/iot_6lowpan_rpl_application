package anaws.utilities.parameters;

public class Parameters {
	
	public static final int MOTE_NUMBER = 30;	// Number of CoAP motes in the net
	public static final int START_INTERFACE_ID = 2;	// Address of the first CoAP server mote
	
	//public static String PREFIX = "fd00::c30c:0:0:"; 	// Address prefix Z1 mote
	public static String PREFIX = "fd00::2"; 			// Address prefix Cooja mote	fd00:02X:X:X:X
	public static String PATH = "mote/value";			// Resource path on mote

}
