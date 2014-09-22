package fuzzer.apps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CLIParser {
	
	private Map<String,String> mCLIParams;
	private List<String> mValidParameters;
	
	public CLIParser(String[] aCommandLine) {
		mCLIParams = parseCommandLine(aCommandLine);
		mCLIParams.put("mode", "");
		mCLIParams.put("cauth", "");
		mCLIParams.put("cwords", "");
		
	}
	
	/**
	 * Returns the data related to the parameter specified.
	 * 
	 * {@param aKey The key of the parameter to get. Specifically:
	 * 		mode -> "discover" or "test", empty if there is none (this should be an error)
	 *		cauth -> "username::password" or empty if there is none
	 *		cwords -> "filename" or empty if there is none
	 * }
	 * @return Command line map with the parsed results matched to specific keys.
	 */
	public String get(String aKey) {
		return mCLIParams.get(aKey);
	}
	
	/**
	 * Returns a map with all the parameters that were parsed from the command line.
	 * 
	 * {@param aCommandLine The command line arguments as given to the Java application.
	 *	The syntax for the command line parameter is: <pre>fuzz [discover | test] url OPTIONS</pre>
	 * }
	 * {@return Command line map with the parsed results matched to specific keys. Specifically:
	 * 	"String" -> "String"
	 *	Keys -> Values:
	 *	mode -> "discover" or "test", empty if there is none (this should be an error)
	 *	cauth -> "username::password" or empty if there is none
	 *	cwords -> "filename" or empty if there is none
	 * }
	 */
	private Map<String, String> parseCommandLine(String[] aCommandLine) {
		Map<String, String> result = new HashMap<>();
		result.put("mode", "");
		result.put("cauth", "");
		result.put("cwords", "");
		
		if (aCommandLine != null && aCommandLine.length > 0) {
			String command = aCommandLine[0];
			if (command.equals("discover") /*|| command.equals("test")*/) {
				mCLIParams.put("mode", command);
			}
			
			//For the rest of the parameters
			for (int i = 1; i < aCommandLine.length; i++) {
				String[] option = aCommandLine[i].split("=");
				if (option.length <= 2) {
					switch (option[0]) {
					case "--custom-auth":
						if (option.length == 2) {
							mCLIParams.put(option[0], parsePassword(option[1]));
						}
						break;
					case "--common-words":
						if (option.length == 2) {
							mCLIParams.put(option[0], option[1]);
						}
						break;
					default:
					} //What if get(0) is out of bounds?
				}
			}
		}
		return result;
	}
	
	private String parsePassword(String aUsernamePassword) {
		String[] up = aUsernamePassword.split(":");
		String result = "";
		if (up.length == 2) {
			result = up[0] + "::" + up[1];
		}
		return result;
	}
}
