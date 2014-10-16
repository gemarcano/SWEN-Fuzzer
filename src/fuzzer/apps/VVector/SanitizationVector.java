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
    private Boolean random;
    
	public SanitizationVector(HtmlPage aPage, String vectorString, Boolean random)
	{
		super("Sanitization");
		mPage = aPage;
		mVectorString = vectorString;
		this.random = random;
	}
	
	@Override
	public boolean test() {
		List<HtmlPage> pages;
		if (random) {
			pages = InputManipulation.testSingleInput(mPage, mVectorString);
		} else {
			pages = InputManipulation.testInputsWithGivenString(mPage, mVectorString);
		}
        boolean success = false;
        for (HtmlPage page : pages) {
            success = page.getWebResponse().getStatusCode() < 400;
        }
        if (success) {
        	loadDescription("Sanitization tests passed. No exploits found.");
        } else {
        	loadDescription("Sanitization tests failed. Exploit found.");
        }
		return !success;
	}
    
    public void setSanitizer(Map<String, String> map) {
        mSanit = map;
    }
}
