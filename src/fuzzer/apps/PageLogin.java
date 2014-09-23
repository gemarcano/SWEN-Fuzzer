package fuzzer.apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class PageLogin {
	private Map<String,String> mKnownUsernames;
	private Map<String,String> mKnownPasswords;
	
	public PageLogin()
	{
		mKnownUsernames = new HashMap<>();
		mKnownPasswords = new HashMap<>();
		
		mKnownUsernames.put("dvwa", "admin");
		mKnownPasswords.put("dvwa", "password");
		mKnownUsernames.put("bodgeit", "test@thebodgeitstore.com");
		mKnownPasswords.put("bodgeit", "password");
	}
	
	static private HtmlTextInput findUsernameField(HtmlPage aPage) 
	{
		HtmlTextInput result = null;
		try {
			result = aPage.getElementByName("username");
		} catch (ElementNotFoundException e) {}
		return result;
	}
	
	static private HtmlPasswordInput findPasswordField(HtmlPage aPage) 
	{
		HtmlPasswordInput result = null;
		try {
			result = aPage.getElementByName("password");
		} catch (ElementNotFoundException e) {}
		return result;
	}
	
	static private HtmlSubmitInput findLoginSubmitButton(HtmlPage aPage) 
	{
		HtmlSubmitInput result = null;
		boolean found = false;
		
		//Gabriel Marcano: Documentation for getElementById is wrong.
		//It does not throw anything, but returns null if it fails
		//to find an element. This is according to the source code
		//of HtmlPage.java from HtmlUnit itself.
		result = (HtmlSubmitInput) aPage.getElementById("submit");
		
		if (result == null)
		{
			try
			{
				result = (HtmlSubmitInput) aPage.getElementByName("Login");
			} catch(ElementNotFoundException e) {
				result = null;
			}
		}
		
		return result;
	}
	
	/**	Returns true if logon succeeded. False otherwise.
	 * 
	 * @param aPage Page with login forms.
	 * @return True upon sucessful login (assumes success if it cannot find login forms on the page loaded after the username password submit).
	 */
	public boolean logon(HtmlPage aPage)
	{
		boolean success = false;
		
		HtmlTextInput userInput;
		HtmlPasswordInput passInput;
		HtmlSubmitInput submit;
		String username, password;
		
		//find username, password fields, and submit button
		userInput = findUsernameField(aPage);
		passInput = findPasswordField(aPage);
		submit = findLoginSubmitButton(aPage);
		
		
		if (userInput == null || passInput == null || submit == null)
		{
			return success; //No point in continuing if we can't find the login portion
		}
		
		List<String> usernames = new ArrayList<>();
		List<String> passwords = new ArrayList<>();
		
		usernames.add(mKnownUsernames.get("dvwa"));
		usernames.add(mKnownUsernames.get("bodgeit"));
		passwords.add(mKnownPasswords.get("dvwa"));
		passwords.add(mKnownPasswords.get("bodgeit"));
		for (int i = 0; !success && i < usernames.size(); i++)
		{
			//Determine username and password combination to use
			username = usernames.get(i);
			password = passwords.get(i);
			
			userInput.setText(username);
			passInput.setText(password);
			
			//Attempt to log in.
			HtmlPage newPage = null;
			try {
				newPage = submit.click();
			} catch (IOException e) {
				// FIXME what is a reasonable thing to do here?
				e.printStackTrace();
				break;
			}
			
			//Verify that we are logged in. This may be done via cookies or making sure that we have been moved to another page (one without login prompts).
			if (findLoginSubmitButton(newPage) == null)
			{
				success = true;
			}
			
			//Attempt to find login forms
			//If found,
			//	If a known site, use known information
			//	Try to use a list of common username and password
			//	try to bruteforce entry? (this should be a non-default option)
		}
		return success;
	}
	
	private static boolean findForm(HtmlPage aPage, String aName)
	{
		boolean result;
		List<HtmlForm> list = aPage.getForms();
		try {
			HtmlForm form = aPage.getFormByName(aName);
			result = true;
		} catch (ElementNotFoundException e) {
			result = false;
		}
		return result;
	}
}
