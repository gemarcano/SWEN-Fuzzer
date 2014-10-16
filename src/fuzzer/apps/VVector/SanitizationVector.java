package fuzzer.apps.VVector;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class SanitizationVector implements VVector {

	private final HtmlPage mPage; //Original page
	String mDescription;
	public SanitizationVector(HtmlPage aPage)
	{
		mPage = aPage;
		mDescription = "";
	}
	
	
	
	@Override
	public boolean test(String vectorString) {
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, vectorString);
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
}
