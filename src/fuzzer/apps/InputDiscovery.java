/**
 * 
 */
package fuzzer.apps;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;

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

    /**
     * Returns an ArrayList of the GET inputs in the given url.
     */
    public static ArrayList<String> getUrlInputs(URL url) {
        ArrayList<String> inputs;
        String queries = url.getQuery();
        if (queries != null) {
            String[] queries_split = queries.split("&");
            inputs = new ArrayList<String>(Arrays.asList(queries_split));
        } else {
            inputs = null;
        }
        return inputs;
    }

	/**
     * Prints all the inputs that the fuzzer discovers on
     * the given page and web client:
     * - Form input tags (from page)
     * - Cookies (from client)
     * - Url GET inputs (from page)
     */
	public static void printInputs(WebClient client, HtmlPage page) {
		ArrayList<DomElement> inputs = getInputs(page);
		System.out.println("Form input elements");
        int n = 0;
		for (DomElement input : inputs) {
			System.out.print("Input " + n + ": ");
            System.out.print("name: " + input.getAttribute("name"));
            System.out.print(" type: " + input.getAttribute("type"));
            System.out.println();
            n += 1;
		}
        System.out.println("Cookies");
        Set<Cookie> cookies = client.getCookies(page.getUrl());
        n = 0;
        for (Cookie cookie : cookies) {
            System.out.print("Cookie " + n + ": ");
            System.out.print("name: " + cookie.getName());
            System.out.print(" value: " + cookie.getValue());
            System.out.println();
        }
	}
}




