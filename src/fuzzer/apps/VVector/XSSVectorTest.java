package fuzzer.apps.VVector;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class XSSVectorTest {

	@Test
	public void test() {
		
		WebClient client = new WebClient();
		HtmlPage page;
		try {
			page = client.getPage("http://127.0.0.1/dvwa/login.php");
			XSSVector vector = new XSSVector(page);
			assertTrue(vector.test());
			
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
