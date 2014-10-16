package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class BufferOverflowVector extends VVector {

	private final HtmlPage mPage; //Original page
	public BufferOverflowVector(HtmlPage aPage)
	{
		super("BufferOverflow");
		mPage = aPage;
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		boolean success = false;
		
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, "abcdefg");
		
		success = true;
		for (HtmlPage page : pages){
			success = success && page.getWebResponse().getStatusCode() < 400;
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
				result = result || page.getWebResponse().getStatusCode() >= 400;
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
			loadDescription("Normal run succeeded and " + (result ? "the test runs found a problem." : "test runs did not find a problem."));
		}
		else
		{
			loadDescription("Normal run did not succeed!!! Tests did not run, since baseline could not be established.");
		}
		
		return success && result;
	}

	
}
