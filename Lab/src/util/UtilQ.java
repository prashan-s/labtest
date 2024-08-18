package a.b.c;

import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import java.io.File;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerConfigurationException;

public class UtilQ extends UtilC {
	
	private static final String employeeQueryPath = "src/b/c/d/EmployeeQuery.xml";
	private static final String queryName = "query";
	private static final String attributeName = "id";
	
	public static String Query(String id) throws Exception {
		
		try {
			
			Element e = null;
			
			NodeList n = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new File(employeeQueryPath))
					.getElementsByTagName(queryName);
			
			for (int x = 0; x < n.getLength(); x++) {
				e = (Element) n.item(x);
				if (e.getAttribute(attributeName).equals(id))
					break;
			}
			
			return e.getTextContent().trim();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
}
