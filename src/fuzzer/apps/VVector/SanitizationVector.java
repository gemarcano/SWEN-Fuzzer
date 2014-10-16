package fuzzer.apps.VVector;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputManipulation;


public class SanitizationVector implements VVector {

	private final HtmlPage mPage; //Original page
	private String mDescription;
    private Map<String, String> mSanit; // map of unsanitized string -> sanitized string
    private String mVectorString;
    
	public SanitizationVector(HtmlPage aPage, String vectorString)
	{
		mPage = aPage;
		mDescription = "";
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
            mDescription += "Sanitization tests passed.";
        } else {
            mDescription += "Sanitization tests failed.";
        }
		return success;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
    
    public void setSanitizer(Map<String, String> map) {
        mSanit = map;
    }
}
