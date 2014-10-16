package fuzzer.apps.VVector;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BufferOverflowVectorTest {

	@Test
	public void test() {
		
		WebClient client = new WebClient();
		HtmlPage page;
		try {
			client.getOptions().setThrowExceptionOnFailingStatusCode(false);
			page = client.getPage("http://127.0.0.1/dvwa/login.php");
			BufferOverflowVector vector = new BufferOverflowVector(page, false);
			assertFalse(vector.test());
			
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
