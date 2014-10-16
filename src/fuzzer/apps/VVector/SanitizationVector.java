package fuzzer.apps.VVector;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class SanitizationVector extends VVector {

	private final HtmlPage mPage; //Original page
    private Map<String, String> mSanit; // map of unsanitized string -> sanitized string
    private String mVectorString;
    
	public SanitizationVector(HtmlPage aPage, String vectorString)
	{
		super("Sanitization");
		mPage = aPage;
		mVectorString = vectorString;
	}
	
	@Override
	public boolean test() {
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, mVectorString);
        boolean success = true;
        for (HtmlPage page : pages) {
            success &= page.getWebResponse().getStatusCode() < 400;
        }
        if (success) {
        	loadDescription("Sanitization tests passed.");
        } else {
        	loadDescription("Sanitization tests failed.");
        }
		return success;
	}
    
    public void setSanitizer(Map<String, String> map) {
        mSanit = map;
    }
}
