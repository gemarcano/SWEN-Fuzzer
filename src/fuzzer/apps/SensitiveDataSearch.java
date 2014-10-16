package fuzzer.apps;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SensitiveDataSearch {
    private HtmlPage page;
    private ArrayList<String> data = new ArrayList<String>();
    
	public SensitiveDataSearch(HtmlPage page, String dataPath) {
        this.page = page;
        readDataFile(dataPath);
    }
    
    private void readDataFile(String dataPath) {
        File dataFile = new File(dataPath);
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(dataFile));
			String line;
			while ((line = reader.readLine()) != null) {
				data.add(line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Returns an ArrayList of instances where data was found.
     */
    public ArrayList<String> search() {
        ArrayList<String> results = new ArrayList<String>();
        BufferedReader reader;
        int lineNum = 0;
        try {
            reader = new BufferedReader(new StringReader(page.asXml()));
            String line;
            while ((line = reader.readLine()) != null) {
                for (String dataEntry : data) {
                    if (line.toLowerCase().contains(dataEntry.toLowerCase())) {
                        results.add("line " + lineNum + ": " + line);
                    }
                }
                lineNum++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    
}



