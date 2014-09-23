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
import com.gargoylesoftware.htmlunit.WebResponse;
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
	private static void discoverLinks(WebClient webClient, String url) {
		
		try {
			System.out.println("Discovering links for " + url + "...");
			HtmlPage page = webClient.getPage(url);
			//TODO dvwa
			List<HtmlAnchor> links = page.getAnchors();
			for (HtmlAnchor link : links) {
				System.out.println("Link discovered: " + link.asText() + " @URL=" + link.getHrefAttribute());
			}
			
		} catch (FailingHttpStatusCodeException | IOException e) {}
	}
	
	/**
	 * 
	 * @param webClient
	 */
	private static void guessPages(WebClient webClient, String url) {
		
		try {
			ArrayList<String> lines = getGuesses();
			for (String line : lines) {
				HtmlPage guess = webClient.getPage(url+line);
                WebResponse response = guess.getWebResponse();
                int statusCode = response.getStatusCode();
				if (guess.isHtmlPage() && statusCode != 404) {
					System.out.println("Page discovered: " + guess.getUrl());
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
			discoverLinks(webClient, "http://localhost:8000/bodgeit");
			guessPages(webClient, "http://localhost:8000/bodgeit");
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
