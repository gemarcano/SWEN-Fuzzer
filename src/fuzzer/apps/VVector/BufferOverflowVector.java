package fuzzer.apps.VVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fuzzer.apps.InputDiscovery;


public class BufferOverflowVector implements VVector {

	HtmlPage mPage;
	public BufferOverflowVector(HtmlPage aPage)
	{
		mPage = aPage;
	}
	
	private boolean testInputString(String inputString)
	{
		HtmlPage inputPage;
		try {
			inputPage = (HtmlPage)mPage.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		List<DomElement> DomForms = mPage.getElementsByTagName("form");
		List<HtmlForm> forms = InputDiscovery.getFormElements(inputPage);
		
		//for each form, get the input elements,
		//identify the submit button
		//Fill all inputs with something.
		for (HtmlForm form : forms)
		{
			List<HtmlInput> inputs = InputDiscovery.getInputsFromForm(form);
			List<? extends HtmlElement> submits = form.getElementsByAttribute("input", "type", "submit");
			inputs.removeAll(submits);
			
			for (HtmlElement input : inputs)
			{
				Page page = ((HtmlInput)input).setValueAttribute(inputString);
				if (page.isHtmlPage())
				{
					inputPage = (HtmlPage)page;
					System.out.println(((HtmlPage)inputPage).asText());
				}
			}
			
			Page resultPage;
			for (HtmlElement submit : submits)
			{
				try {
					resultPage = ((HtmlInput)submit).click();
					if (resultPage.isHtmlPage())
					{
						System.out.println(((HtmlPage)resultPage).asText());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		testInputString("1");
		
		String overflowString = "";
		int bound = 1<<16;
		for (int i = 0; i < bound; i++)
		{
			overflowString += "a";
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
