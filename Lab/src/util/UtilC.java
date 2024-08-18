package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;


public class UtilC {

	public static final Properties p = new Properties();
	private static final Logger logger = Logger.getLogger(UtilC.class.getName());

	static {
		try {
			p.load(UtilQ.class.getResourceAsStream("../config/config.properties"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}
}
