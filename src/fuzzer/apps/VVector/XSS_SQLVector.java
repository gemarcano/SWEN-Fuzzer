package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class XSS_SQLVector implements VVector {

	private final HtmlPage mPage; //Original page
	String mDescription;
	String vectorString;
	
	public XSS_SQLVector(HtmlPage aPage, String vectorString)
	{
		mPage = aPage;
		mDescription = "";
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		boolean success = false;
		
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, vectorString);
		
		success = true;
		for (HtmlPage page : pages){
			int status = page.getWebResponse().getStatusCode();
			if ( (status >= 200) && (status < 300)) {
				success &= true;
			}
		}
		
		if (success){
			mDescription += "Normal run succeeded and " + (result ? "the test runs found a problem." : "test runs did not find a problem.");
		} else {
			mDescription += "Normal run did not succeed!!! Tests did not run, since baseline could not be established.";
		}
		
		return success && result;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
}
