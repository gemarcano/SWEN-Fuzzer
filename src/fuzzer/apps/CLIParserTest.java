package fuzzer.apps;

import static org.junit.Assert.*;

import org.junit.Test;

public class CLIParserTest {

	@Test
	public void testCLIParser() {
		String[] str = new String[3];
		str[0] = "discover";
		str[1] = "www.google.com";
		str[2] = "--custom-auth=testuser::testpass";
		CLIParser parser = new CLIParser(str);
		//fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		String[] str = new String[4];
		str[0] = "discover";
		str[1] = "www.google.com";
		str[2] = "--custom-auth=testuser::testpass";
		str[3] = "--common-words=alksdhfkalsdjhfkjasdhflkjashdlfkjadshlfkjdahslfkjh";
		CLIParser parser = new CLIParser(str);
		assertEquals("discover", parser.get("mode"));
		assertEquals("www.google.com", parser.get("url"));
		assertEquals("testuser::testpass", parser.get("cauth"));
		assertEquals("alksdhfkalsdjhfkjasdhflkjashdlfkjadshlfkjdahslfkjh", parser.get("cwords"));
	}

}
