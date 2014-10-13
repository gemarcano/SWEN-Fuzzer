package fuzzer.apps;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.*;

public class CLIParser {

	private Map<String, String> mCLIParams;
	private List<String> mValidParameters;

	/**
	 * Constructs the CLIParser. Some syntax notes: --custom-auth=APP
	 * --custom-words=FILENAME_PATH FIXME: FILENAME_PATH currently does not like
	 * using quotes.
	 * 
	 * @param aCommandLine
	 *            String array with contents of command line arguments as
	 *            received from the JVM.
	 */
	public CLIParser(String[] aCommandLine) {
		mCLIParams = parseCommandLine(aCommandLine);
	}

	/**
	 * Returns the data related to the parameter specified.
	 * 
	 * @param aKey
	 *            The key of the parameter to get. Specifically: mode ->
	 *            "discover" or "test", empty if there is none (this should be
	 *            an error) cauth -> "app" or empty if there is none cwords ->
	 *            "filename" or empty if there is none url -> URL of site * }
	 * @return Command line map with the parsed results matched to specific
	 *         keys.
	 */
	public String get(String aKey) {
		String result = mCLIParams.get(aKey);
		if (result == null)
		{
			result = "";
		}
		return result;
	}

	/**
	 * Returns a map with all the parameters that were parsed from the command
	 * line.
	 * 
	 * @param aCommandLine
	 *            The command line arguments as given to the Java application.
	 *            The syntax for the command line parameter is:
	 * 
	 * <pre>
	 * fuzz [discover | test] url OPTIONS
	 * </pre>
	 * 
	 * {@return 
	 *            Command line map with the parsed results matched to
	 *            specific keys. Specifically: "String" -> "String" Keys ->
	 *            Values: mode -> "discover" or "test", empty if there is none
	 *            (this should be an error) cauth -> "app" or empty if there is
	 *            none cwords -> "filename" or empty if there is none * }
	 */
	private Map<String, String> parseCommandLine(String[] aCommandLine) {
		Map<String, String> result = new HashMap<>();
		result.put("mode", "");
		result.put("cauth", "");
		result.put("cwords", "");
		result.put("vectors", "");
		result.put("sensitive", "");
		result.put("random", "");
		result.put("slow", "");
		result.put("url", "");

		if (aCommandLine.length >= 2)
		{
			String command = aCommandLine[0];
			if (command.equals("discover") || command.equals("test") ) {
				result.put("mode", command);
			}

			result.put("url", aCommandLine[1]);

			if (aCommandLine.length > 2)
			{
				//FIXME parse options conditionally
				Options opts = new Options();
				//both
				opts.addOption(OptionBuilder.withLongOpt("custom-auth").withValueSeparator().hasArg().create("cauth"));
				
				//discovery
				opts.addOption(OptionBuilder.withLongOpt("common-words").withValueSeparator().hasArg().create("cwords"));
				
				//test
				opts.addOption(OptionBuilder.withLongOpt("vectors").withValueSeparator().hasArg().create("vectors"));
				opts.addOption(OptionBuilder.withLongOpt("sensitive").withValueSeparator().hasArg().create("sensitive"));
				opts.addOption(OptionBuilder.withLongOpt("random").withValueSeparator().hasArg().create("random"));
				opts.addOption(OptionBuilder.withLongOpt("slow").withValueSeparator().hasArg().create("slow"));
				
				CommandLineParser parser = new GnuParser();
				CommandLine line = null;
				

				String[] restOfArgs = Arrays.copyOfRange(aCommandLine, 2, aCommandLine.length);
				try {
					line = parser.parse(opts, restOfArgs);
				} catch (ParseException e) {
					// FIXME leaking information!
					e.printStackTrace();
				}
				
				if (line != null) {
					if (line.hasOption("cauth")) {
						result.put("cauth", line.getOptionValue("cauth"));
					}
					if (line.hasOption("cwords")) {
						result.put("cwords", line.getOptionValue("cwords"));
					}
					if (line.hasOption("vectors")) {
						result.put("vectors", line.getOptionValue("vectors"));
					}
					if (line.hasOption("sensitive")) {
						result.put("sensitive", line.getOptionValue("sensitive"));
					}
					if (line.hasOption("random")) {
						result.put("random", line.getOptionValue("random"));
					}
					if (line.hasOption("slow")) {
						result.put("slow", line.getOptionValue("slow"));
					}
				}
				
					
			}
		}
		
		return result;
	}
}
