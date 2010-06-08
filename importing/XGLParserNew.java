package importing;

import java.io.BufferedReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XGLParserNew extends Parser{
	public static void thing()
	{
		Document dom;
		dom = parseXglFile("./lib/legoman.xgl");
		
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
		//.
		//Now that we've got the file in dom format, loop through elements
		Element rootElement = dom.getDocumentElement(); //Should be a WORLD tag
		
		String[] defineTags = { "MAT", "OBJECT", "MESH", "LINESTYLE", "POINTSTYLE", "TEXTURE", "TEXTURERGB", "TEXTURERGBA", "TC"};
		String[] requiredTags = {"LIGHTING", "BACKGROUND" };
		String[] optionalTags = { "DATA", "NAME" };
		
		NodeList tagList;
		
		tagList = rootElement.getChildNodes();
		for(int i = 0; i < tagList.getLength(); i++ )
		{
			System.out.println( "Node " + i + ": " + tagList.item(i).getNodeName());
		}
		
		//Handle define tags
		for(String tag : defineTags)
		{
			tagList = rootElement.getElementsByTagName(tag);
			if( tagList != null && tagList.getLength() > 0)
			{
				
			}else
			{
				System.out.println("No define tag " + tag + " in WORLD");
				//throw exception?
			}
		}
		
		//Handle required tags
		for(String tag : requiredTags)
		{
			tagList = rootElement.getElementsByTagName(tag);
			if( tagList != null && tagList.getLength() == 1)
			{
				
			}else
			{
				System.out.println("Missing " + tag + " in WORLD");
				//throw exception?
			}
		}
		
		//Handle Optional Tags
		for(String tag : optionalTags)
		{
			tagList = rootElement.getElementsByTagName(tag);
			if( tagList != null && tagList.getLength() > 0)
			{
			
			}else
			{
				//Everything's fine and dandy, but let us know for Debugging purposes
				System.out.println("Missing optional " + tag + " in WORLD");
			}
		}
		
	}

	@Override
	public void readFile(BufferedReader f) {
		// TODO Auto-generated method stub
		
	}
}
