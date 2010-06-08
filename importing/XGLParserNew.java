package importing;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XGLParserNew {
	public static void main()
	{
		Document dom;
		dom = parseXglFile("/lib/legoman.xgl");
		
		parseDocument(dom);
	}
	
	private static Document parseXglFile(String filePath)
	{
		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try
		{
			//get instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation
			dom = db.parse(filePath);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return dom;
	}
	
	private static void parseDocument(Document dom)
	{
		//Now that we've got the file in dom format, loop through elements
		Element rootElement = dom.getDocumentElement(); //Should be a WORLD tag
		
		String[] requiredTags = {"LIGHTING", "BACKGROUND" };
		String[] optionalTags = {"OBJECT", "MESH", "DATA", "NAME" };
		
		NodeList tagList;
		
		//Handle required tags
		for(String tag : requiredTags)
		{
			tagList = rootElement.getElementsByTagName(tag);
			if( tagList != null && tagList.getLength() == 1)
			{
				
			}else
			{
				System.out.println("Missing " + tag + " in WORLD");
			}
		}
		
		//Handle Optional Tags
		for(String tag : optionalTags)
		{
			tagList = rootElement.getElementsByTagName(tag);
			if( tagList != null && tagList.getLength() == 1)
			{
			
			}else
			{
				//Everything's fine and dandy, but let us know for Debugging purposes
				System.out.println("Missing optional " + tag + " in WORLD");
			}
		}
		
	}
}
