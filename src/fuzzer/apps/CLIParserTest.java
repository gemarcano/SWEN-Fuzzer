package fuzzer.apps;

import static org.junit.Assert.*;

import org.junit.Test;

public class CLIParserTest {

	@Test
	public void testCLIParser() {
		String[] str = new String[2];
		str[0] = "discover";
		str[1] = "--custom-auth=testuser::testpass";
		CLIParser parser = new CLIParser(str);
		//fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		String[] str = new String[3];
		str[0] = "discover";
		str[1] = "--custom-auth=testuser::testpass";
		str[2] = "--common-words=alksdhfkalsdjhfkjasdhflkjashdlfkjadshlfkjdahslfkjh";
		CLIParser parser = new CLIParser(str);
		assertEquals("discover", parser.get("mode"));
		assertEquals("testuser::testpass", parser.get("cauth"));
		assertEquals("alksdhfkalsdjhfkjasdhflkjashdlfkjadshlfkjdahslfkjh", parser.get("cwords"));
	}

}
