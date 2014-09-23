package fuzzer.apps;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PageLoginTest {

	@Test
	public void testPageLogin() {
		PageLogin login = new PageLogin();
	}

	@Test
	public void testLogon() {
		PageLogin login = new PageLogin();
		WebClient client = new WebClient();
		HtmlPage page;
		try {
			page = client.getPage("http://127.0.0.1/dvwa/login.php");
			assertTrue(login.logon(page));
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
