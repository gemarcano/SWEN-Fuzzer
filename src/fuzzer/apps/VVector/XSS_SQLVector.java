package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class XSS_SQLVector extends VVector {

	private final HtmlPage mPage; //Original page 
	private String vectorString;
	private Boolean random;
	
	public XSS_SQLVector(HtmlPage aPage, String vStr, Boolean random)
	{
		super("XSS_SQL");
		mPage = aPage;
		vectorString = vStr;
		this.random = random;
	}
	
	@Override
	public boolean test() {
		boolean result = true;
		List<HtmlPage> pages;
		
		if (random) {
			pages = InputManipulation.testSingleInput(mPage, vectorString);
		} else {
			pages = InputManipulation.testInputsWithGivenString(mPage, vectorString);
		}
		
		for (HtmlPage page : pages){
			int status = page.getWebResponse().getStatusCode();
			if ( (status >= 200) && (status < 300)) {
				result = false;
			}
		}
		
		loadDescription(result ? "The test run found a problem." : "Test run did not find a problem.");
		
		return result;
	}
}
