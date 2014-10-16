package fuzzer.apps.VVector;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import fuzzer.apps.InputDiscovery;


public class BufferOverflowVector implements VVector {

	private final HtmlPage mPage; //Original page
	String mDescription;
	public BufferOverflowVector(HtmlPage aPage)
	{
		mPage = aPage;
		mDescription = "";
	}
	
	private boolean testInputString(String inputString, BigInteger aSize)
	{
		boolean result = false;
		HtmlPage currentPage;
		try {
			currentPage = (HtmlPage) mPage.refresh();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		List<HtmlForm> forms = InputDiscovery.getFormElements(currentPage);
		
		//for each form, get the input elements,
		//identify the submit button
		//Fill all inputs with something.
		for (HtmlForm form : forms)
		{
			List<HtmlInput> inputs = InputDiscovery.getInputsFromForm(form);
			List<HtmlSubmitInput> submits = InputDiscovery.getSubmitsFromForm(form);
			inputs.removeAll(submits);
			
			for (HtmlSubmitInput submit : submits) {
				for (HtmlInput input : inputs)
				{
					input.setAttribute("size", aSize.toString());
					Page page = input.setValueAttribute(inputString);
					currentPage = (HtmlPage)page;
				}
				
				HtmlPage resultPage;
				try {
					resultPage = (HtmlPage)(submit.click());
					result = resultPage.getWebResponse().getStatusCode() >= 400;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		boolean success = false;
		BigInteger size = new BigInteger("10000000000000000");
		
		success = !testInputString("1", size);
		
		if (success){
			String overflowString = "";
			int bound = 1<<16;
			for (int i = 0; i < bound; i++)
			{
				overflowString += "abcdefghij";
			}
			result = testInputString(overflowString, size);
		}
		
		//Get page back
		//Now try to overflow.
		if (success)
		{
			mDescription += "Normal run succeeded and " + (result ? "the test run found a problem." : "test run did not find a problem.");
		}
		else
		{
			mDescription += "Normal run did not succeed!!! Test did not run, since baseline could not be established.";
		}
		
		return success && result;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}
}
