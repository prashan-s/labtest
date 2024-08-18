package main;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import services.getEmpService;
import util.UtilTransform;

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		getEmpService employeeService = new getEmpService();
		try {
			UtilTransform.requestTransform();
			employeeService.employyeeFromXML();
			employeeService.employeeTableCreate();
			employeeService.employeesAdd();
			employeeService.employeeGetByID("EMP10004");
			employeeService.employeeDelete("EMP10001");
			employeeService.employeeDisplay();
		} catch (Exception e) {
		}

	}

}
