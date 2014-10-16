package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class XSS_SQLVector extends VVector {

	private final HtmlPage mPage; //Original page
	String vectorString;
	
	public XSS_SQLVector(HtmlPage aPage, String vStr)
	{
		super("XSS_SQL");
		mPage = aPage;
		vectorString = vStr;
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, vectorString);
		
		for (HtmlPage page : pages){
			int status = page.getWebResponse().getStatusCode();
			if ( (status >= 200) && (status < 300)) {
				result = true;
			}
		}
		
		loadDescription(result ? "The test run found a problem." : "Test run did not find a problem.");
		
		return result;
	}
}
