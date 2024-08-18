package a.b.c;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

public class UtilTransform extends UtilC {

	private static final ArrayList<Map<String, String>> l = new ArrayList<Map<String, String>>();
	
	private static final String employeeRequest = "src/b/c/d/EmployeeRequest.xml";
	private static final String employeeModifiedXsl = "src/b/c/d/Employee-modified.xsl";
	private static final String employeeModifiedResponseXml = "src/b/c/d/EmployeeResponse.xml";
	private final static Logger logger = Logger.getLogger(UtilTransform.class.getName());

	public static void RequestTransform() throws Exception {

		Source x = new StreamSource(new File(employeeRequest));
		Source s = new StreamSource(new File(employeeModifiedXsl));
		Result o = new StreamResult(new File(employeeModifiedResponseXml));
		TransformerFactory.newInstance().newTransformer(s).transform(x, o);
	}

	public static ArrayList<Map<String, String>> XMLXPATHS() throws Exception {

		try {
			
			final Map<String, String> m = new HashMap<String, String>();;
			
			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(employeeModifiedResponseXml);
			XPath x = XPathFactory.newInstance().newXPath();
			int n = Integer.parseInt((String) x.compile("count(//Employees/Employee)").evaluate(d, XPathConstants.STRING));
			
			for (int i = 1; i <= n; i++) {
				m.put("XpathEmployeeIDKey", (String) x.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
						.evaluate(d, XPathConstants.STRING));
				m.put("XpathEmployeeNameKey", (String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
						.evaluate(d, XPathConstants.STRING));
				m.put("XpathEmployeeAddressKey",
						(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(d,
								XPathConstants.STRING));
				m.put("XpathFacultyNameKey", (String) x.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
						.evaluate(d, XPathConstants.STRING));
				m.put("XpathDepartmentKey", (String) x.compile("//Employees/Employee[" + i + "]/Department/text()")
						.evaluate(d, XPathConstants.STRING));
				m.put("XpathDesignationKey", (String) x.compile("//Employees/Employee[" + i + "]/Designation/text()")
						.evaluate(d, XPathConstants.STRING));
				l.add(m);
			}
			
			return l;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		
	}
}
