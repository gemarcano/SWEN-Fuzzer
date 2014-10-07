package fuzzer.apps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class PageLogin {
	private Map<String, String> mKnownUsernames;
	private Map<String, String> mKnownPasswords;

	public PageLogin() {
		mKnownUsernames = new HashMap<>();
		mKnownPasswords = new HashMap<>();

		mKnownUsernames.put("dvwa", "admin");
		mKnownPasswords.put("dvwa", "password");
		mKnownUsernames.put("bodgeit", "test@thebodgeitstore.com");
		mKnownPasswords.put("bodgeit", "password");
	}

	static private HtmlTextInput findUsernameField(HtmlPage aPage) {
		HtmlTextInput result = null;
		try {
			result = aPage.getElementByName("username");
		} catch (ElementNotFoundException e) {
		}
		return result;
	}

	static private HtmlPasswordInput findPasswordField(HtmlPage aPage) {
		HtmlPasswordInput result = null;
		try {
			result = aPage.getElementByName("password");
		} catch (ElementNotFoundException e) {
		}
		return result;
	}

	static private HtmlSubmitInput findLoginSubmitButton(HtmlPage aPage) {
		HtmlSubmitInput result = null;
		boolean found = false;

		// Gabriel Marcano: Documentation for getElementById is wrong.
		// It does not throw anything, but returns null if it fails
		// to find an element. This is according to the source code
		// of HtmlPage.java from HtmlUnit itself.
		result = (HtmlSubmitInput) aPage.getElementById("submit");

		if (result == null) {
			try {
				result = (HtmlSubmitInput) aPage.getElementByName("Login");
			} catch (ElementNotFoundException e) {
				result = null;
			}
		}

		return result;
	}

	/**
	 * Returns true if logon succeeded. False otherwise.
	 * 
	 * @param aPage
	 *            Page with login forms.
	 * @param app
	 *            The app to log in to.
	 * @return True upon sucessful login (assumes success if it cannot find
	 *         login forms on the page loaded after the username password
	 *         submit).
	 */
	public boolean logon(HtmlPage aPage, String app) {
		boolean success = false;

		HtmlTextInput userInput;
		HtmlPasswordInput passInput;
		HtmlSubmitInput submit;
		String username, password;

		// Attempt to find login forms
		// If found,
		// If a known site, use known information
		// Try to use a list of common username and password
		// try to bruteforce entry? (this should be a non-default option)

		// find username, password fields, and submit button
		userInput = findUsernameField(aPage);
		passInput = findPasswordField(aPage);
		submit = findLoginSubmitButton(aPage);

		if (userInput == null || passInput == null || submit == null) {
			return success; // No point in continuing if we can't find the login
							// portion
		}

		// Determine username and password combination to use
		username = determineUsername(app);
		password = determinePassword(app);

		userInput.setText(username);
		passInput.setText(password);

		// Attempt to log in.
		HtmlPage newPage = null;
		try {
			newPage = submit.click();
		} catch (IOException e) {
			// FIXME what is a reasonable thing to do here?
			e.printStackTrace();
		}

		// Verify that we are logged in. This may be done via cookies or making
		// sure that we have been moved to another page (one without login
		// prompts).
		if (findLoginSubmitButton(newPage) == null) {
			success = true;
		}
		return success;
	}

	public void printLogon(HtmlPage aPage, String app) {
		System.out.println("--------------------------------------");
		System.out.println("Custom authentication...");
		System.out.println("--------------------------------------");
		boolean loginResult = logon(aPage, app);
		if (loginResult) {
			String username = mKnownUsernames.get(app);
			String password = mKnownPasswords.get(app);
			System.out.println("Successfully logged in to the app " + app
					+ " at URL " + aPage.getUrl());
			System.out.println("username: " + username);
			System.out.println("password: " + password);
		} else {
			System.out.println("Failed to log in to the app " + app
					+ " at URL " + aPage.getUrl());
		}
	}
	
	private String determineUsername(String app)
	{
		String username = mKnownUsernames.get(app);
		
		if (username == null)
		{
			username = "";
		}
		
		return username;
	}
	
	private String determinePassword(String app)
	{
		String password = mKnownPasswords.get(app);
		
		if (password == null)
		{
			password = "";
		}
		
		return password;
	}
}
