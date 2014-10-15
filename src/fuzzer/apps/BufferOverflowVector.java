package fuzzer.apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class BufferOverflowVector implements VVector {

	HtmlPage mPage;
	public BufferOverflowVector(HtmlPage aPage)
	{
		mPage = aPage;
	}
	
	@Override
	public boolean test() {
		boolean result = false;
		//Find all inputs
		ArrayList<DomElement> inputs = InputDiscovery.getInputs(mPage);
		//Try to find a submit button
		List<DomElement> submits = InputDiscovery.getSubmitElements(mPage);
		//Give input some reasonable amount of text
		
		for (DomElement input : inputs)
		{
			if (input instanceof HtmlTextInput)
			{
				HtmlTextInput i = ((HtmlTextInput)input);
				i.setValueAttribute("1");
			}
		}
		
		System.out.println(mPage.asText());
		try {
			((HtmlSubmitInput)submits).click();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Get page back
		//Now try to overflow.
		return result;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
