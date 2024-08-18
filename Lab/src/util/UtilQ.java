package util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class UtilQ extends UtilC {
	
	private static final String employeeQueryPath = "src/config/EmployeeQuery.xml";
	private static final String queryName = "query";
	private static final String attributeName = "id";
	private static final Logger logger = Logger.getLogger(UtilQ.class.getName());
	
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
			logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		
	}
	
}
