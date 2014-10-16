package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class XSSVector implements VVector {

	private final HtmlPage mPage; //Original page
	String mDescription;
	public XSSVector(HtmlPage aPage)
	{
		mPage = aPage;
		mDescription = "";
	}
	
	@Override
	public boolean test(String vectorString) {
		boolean result = false;
		boolean success = false;
		
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, "<SCRIPT>alert(\"hello\")</SCRIPT>");
		
		success = true;
		for (HtmlPage page : pages){
			int status = page.getWebResponse().getStatusCode();
			if ( (status >= 200) && (status < 300)) {
				success &= true;
			}
		}
		
		if (success){
			String overflowString = "";
			int bound = 1<<4;
			for (int i = 0; i < bound; i++)
			{
				overflowString += "abcdefghij";
			}
			
			pages = InputManipulation.testInputsWithGivenString(mPage, overflowString);

			for (HtmlPage page : pages){
				result |= page.getWebResponse().getStatusCode() >= 400;
			}
			
			overflowString = "";
			bound = 1<<16;
			for (int i = 0; i < bound; i++)
			{
				overflowString += "abcdefghij";
			}
			
			pages = InputManipulation.testInputsWithGivenString(mPage, overflowString);
			
			for (HtmlPage page : pages){
				result |= page.getWebResponse().getStatusCode() >= 400;
			}
		}
		
		//Get page back
		//Now try to overflow.
		if (success)
		{
			mDescription += "Normal run succeeded and " + (result ? "the test runs found a problem." : "test runs did not find a problem.");
		}
		else
		{
			mDescription += "Normal run did not succeed!!! Tests did not run, since baseline could not be established.";
		}
		
		return success && result;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
}
