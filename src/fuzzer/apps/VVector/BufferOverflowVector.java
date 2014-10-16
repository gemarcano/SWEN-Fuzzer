package fuzzer.apps.VVector;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import fuzzer.apps.InputDiscovery;


public class BufferOverflowVector implements VVector {

	private final HtmlPage mPage; //Original page
	public BufferOverflowVector(HtmlPage aPage)
	{
		mPage = aPage;
	}
	
	private boolean testInputString(String inputString)
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
					input.setAttribute("size", "100000000");
					Page page = input.setValueAttribute(inputString);
					currentPage = (HtmlPage)page;
					System.out.println(currentPage.asText());
				}
				
				HtmlPage resultPage;
				try {
					resultPage = (HtmlPage)(submit.click());
					System.out.println(resultPage.asText());
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
		testInputString("1");
		
		String overflowString = "";
		int bound = 1<<16;
		for (int i = 0; i < bound; i++)
		{
			overflowString += "abcdefghij";
		}
		testInputString(overflowString);
		
		//Get page back
		//Now try to overflow.
		return result;
	}

	@Override
	public String getDescription() {
		return "";
	}

}
