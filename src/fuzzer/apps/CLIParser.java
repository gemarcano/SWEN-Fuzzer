package fuzzer.apps;

import java.util.HashMap;
import java.util.Map;

public class CLIParser {
	
	private Map<String,String> mCLIParams;
	
	public CLIParser(String[] aCommandLine) {
		mCLIParams = parseCommandLine(aCommandLine);
		
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
		
		if (aCommandLine != null) {
		
		}
		return result;
	}
}
