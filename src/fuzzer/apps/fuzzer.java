package fuzzer.apps;
/**
 * 
 * @authors Connor <csh6900>, Gabriel <gem5597>
 *
 */

import java.util.HashMap;
import java.util.Map;

public class fuzzer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> commandMap = parseCommandLine(args);
	}

	/***
	 * Command line map "String" -> "String"
	 * Keys -> Values:
	 * 	mode -> "discover" or "test", empty if there is none (this should be an error)
	 * 	cauth -> "username::password" or empty if there is none
	 * 	cwords -> "filename" or empty if there is none
	 */
	static Map<String, String> parseCommandLine(String[] aCommandLine) {
		Map<String, String> result = new HashMap<>();
		result.put("mode", "");
		result.put("cauth", "");
		result.put("cwords", "");
		
		if (aCommandLine != null) {
		
		}
		return result;
	}
}
