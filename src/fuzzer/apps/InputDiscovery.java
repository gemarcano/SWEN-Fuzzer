/**
 * 
 */
package fuzzer.apps;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Joe <jak3122>
 * 
 */
public class InputDiscovery {
	
	/**
	 * Returns all input elements on a given HtmlPage.
	 * @param page The page to get inputs from.
	 * @return All inputs from page.
	 */
	public static List<HtmlElement> getInputs(HtmlPage page) {
		List<HtmlElement> input_list = new ArrayList<HtmlElement>();
		List<HtmlForm> formsList = page.getForms();
		Iterable<DomElement> input_elements;
		for (HtmlForm form : formsList) {
			input_elements = form.getChildElements();
			for (DomElement input : input_elements) {
				if (input instanceof HtmlInput) {
					input_list.add((HtmlInput)input);
				}
			}
		}
		return input_list;
	}
	
	public static void printInputs(HtmlPage page) {
		List<HtmlElement> inputs = getInputs(page);
		for (HtmlElement input : inputs) {
			System.out.println(input.asText());
		}
	}
}
