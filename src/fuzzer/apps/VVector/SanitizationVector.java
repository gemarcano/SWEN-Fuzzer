package fuzzer.apps.VVector;

import java.util.List;
import java.util.HashMap;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class SanitizationVector implements VVector {

	private final HtmlPage mPage; //Original page
	String mDescription;
    private Map<String, String> mSanit; // map of unsanitized string -> sanitized string
    
	public SanitizationVector(HtmlPage aPage)
	{
		mPage = aPage;
		mDescription = "";
	}
	
	@Override
	public boolean test(String vectorString) {
		List<HtmlPage> pages = InputManipulation.testInputsWithGivenString(mPage, vectorString);
        boolean success = true;
        for (HtmlPage page : pages) {
            success &= page.getWebResponse().getStatusCode() < 400;
        }
        if (success) {
            mDescription += "Sanitization tests passed.";
        } else {
            mDescription += "Sanitization tests failed.";
        }
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
    
    public void setSanitizer(Map<String, String> map) {
        mSanit = map;
    }
}
