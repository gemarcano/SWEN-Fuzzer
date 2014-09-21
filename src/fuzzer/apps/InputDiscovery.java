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
	public static ArrayList<DomElement> getInputs(HtmlPage page) {
		/* As far as I can tell, there's no way in HtmlUnit
		 * to simply get all inputs from a page or form,
		 * so here I get all DOM elements in all forms
		 * and take out the input elements.
		 */
		List<HtmlForm> formsList = page.getForms();
		Iterable<DomElement> formElements;
		ArrayList<DomElement> formChildren = new ArrayList<DomElement>();
		for (HtmlForm form : formsList) {
			formElements = form.getChildElements();
			for (DomElement e : formElements) {
				formChildren.add(e);
				formChildren.addAll(_getElements(e));
			}
		}
		ArrayList<DomElement> inputs = new ArrayList<DomElement>();
		for (DomElement e : formChildren) {
			if (e instanceof HtmlInput) {
				inputs.add(e);
			}
		}
		
		return inputs;
	}
	
	/**
	 * Recursively get all the child elements of a DOM element
	 * @param e element to get children of
	 * @return all child elements of e
	 */
	private static ArrayList<DomElement>_getElements(DomElement e) {
		ArrayList<DomElement> elements = new ArrayList<DomElement>();
		Iterable<DomElement> children = e.getChildElements();
		for (DomElement child : children) {
			elements.add(child);
			elements.addAll(_getElements(child));
		}
		return elements;
	}
	
	public static void printInputs(HtmlPage page) {
		ArrayList<DomElement> inputs = getInputs(page);
		System.out.println("Number of inputs:" + inputs.size());
        int n = 0;
		for (DomElement input : inputs) {
			System.out.print("Input " + n + ": ");
            System.out.print("name: " + input.getAttribute("name"));
            System.out.print(" type: " + input.getAttribute("type"));
            System.out.println();
            n += 1;
		}
	}
}




