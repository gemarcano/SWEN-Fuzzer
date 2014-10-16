package fuzzer.apps;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class InputManipulation {
	/**
	 * Finds all forms in the page, then for each form, and each submission object found, give the inputs in the form (except submission objects) the given string as a value, then submit the strings.
	 * Pseudocode:
	 * 	for (form in forms)
	 * 		for (submit in submitObjects in form)
	 * 			for (input in inputs in form)
	 * 				input.setValueAttribute(aInputString)
	 * 				submit.click()
	 * 
	 * @param aPage Page to 
	 * @param aInputString
	 * @return HtmlPages resulting from each submission done in the test.
	 */
	public static List<HtmlPage> testInputsWithGivenString(HtmlPage aPage, String aInputString)
	{
		List<HtmlPage> result = new ArrayList<>();
		HtmlPage currentPage;
		try {
			currentPage = (HtmlPage) aPage.refresh();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<>();
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
					input.setAttribute("size", Integer.toString(aInputString.length()));
					Page page = input.setValueAttribute(aInputString);
					currentPage = (HtmlPage)page;
				}
				
				HtmlPage resultPage;
				try {
					resultPage = (HtmlPage)(submit.click());
					result.add(resultPage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new ArrayList<>();
				}
			}
		}
		return result;
	}
}
