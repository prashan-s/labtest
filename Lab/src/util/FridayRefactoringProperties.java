package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;


public class FridayRefactoringProperties {

	public static final Properties properties = new Properties();
	
	//Logger Implementation
	private static final Logger logger = Logger.getLogger(FridayRefactoringProperties.class.getName());

	static {
		try {
			properties.load(EmployeeQueryProvider.class.getResourceAsStream("../config/config.properties"));
		} catch (Exception e) {
			//Logging the Exceptions
			logger.log(Level.SEVERE, e.toString());
		}
	}
}
