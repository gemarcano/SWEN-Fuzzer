package fuzzer.apps;
/**
 * 
 * @authors Connor <csh6900>, Gabriel <gem5597>, Joe <jak3122>
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class fuzzer {

	/**
	 * Function to receive a list of strings to try and guess from a given file
	 * @return - A list of strings with potential links to find
	 */
	public static ArrayList<String> getGuesses() {
		File file = new File("docs/pageGuesses.txt");
		BufferedReader reader;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}
	
	/**
	 * This code is for showing how you can get all the links on a given page, and visit a given URL
	 * @param webClient
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private static void discoverLinks(WebClient webClient) {
		
		try {
			System.out.println("Discovering links for BodgeIt...");
			HtmlPage bodge = webClient.getPage("http://localhost:8080/bodgeit");
			//TODO dvwa
			List<HtmlAnchor> bodgeLinks = bodge.getAnchors();
			for (HtmlAnchor link : bodgeLinks) {
				System.out.println("Link discovered: " + link.asText() + " @URL=" + link.getHrefAttribute());
			}
			System.out.println("Discovering links for DVWA...");
			HtmlPage dvwa = webClient.getPage("http://localhost:8080/dvwa");
			//TODO dvwa
			List<HtmlAnchor> dvwaLinks = dvwa.getAnchors();
			for (HtmlAnchor link : dvwaLinks) {
				System.out.println("Link discovered: " + link.asText() + " @URL=" + link.getHrefAttribute());
			}
			
			
		} catch (FailingHttpStatusCodeException | IOException e) {}
	}
	
	/**
	 * 
	 * @param webClient
	 */
	private static void guessPages(WebClient webClient) {
		
		try {
			ArrayList<String> lines = getGuesses();
			for (String line : lines) {
				HtmlPage guessBodgeit = webClient.getPage("http://localhost:8080/bodgeit"+line);
				if (guessBodgeit.isHtmlPage()) {
					System.out.println("Page discovered: " + guessBodgeit.asText());
				}
				HtmlPage guessDVWA = webClient.getPage("http://localhost:8080/bodgeit"+line);
				if (guessDVWA.isHtmlPage()) {
					System.out.println("Page discovered: " + guessDVWA.asText());
				}
			}
			
		} catch (FailingHttpStatusCodeException | IOException e) {}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WebClient webClient = new WebClient();
        WebClientOptions options = webClient.getOptions();
        options.setThrowExceptionOnFailingStatusCode(false);
        options.setPrintContentOnFailingStatusCode(false);
        
		try {
			discoverLinks(webClient);
			guessPages(webClient);
			HtmlPage page = webClient.getPage("http://localhost:8080/bodgeit/login.jsp?username=test&password=hello");
            System.out.println("URL:");
            System.out.println(page.getUrl());
            System.out.println("URL inputs:");
            System.out.println(InputDiscovery.getUrlInputs(page.getUrl()));
			InputDiscovery.printInputs(webClient, page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CLIParser commandParser = new CLIParser(args); //See CLIParser.get() for description of how to get parameters
	}
}
