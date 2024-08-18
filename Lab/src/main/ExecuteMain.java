package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import services.GetEmpService;
import util.UtilTransform;

public class ExecuteMain {
	
	//Logger Implementation
	private static final Logger logger = Logger.getLogger(ExecuteMain.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetEmpService employeeService = new GetEmpService();
		try {
			UtilTransform.requestTransform();
			employeeService.employyeeFromXML();
			employeeService.employeeTableCreate();
			employeeService.employeesAdd();
			employeeService.employeeGetByID("EMP10004");
			employeeService.employeeDelete("EMP10001");
			employeeService.employeeDisplay();
		} catch (Exception e) {
			//Logging the Exceptions
			logger.log(Level.SEVERE, e.toString());
		}

	}

}
