package services;

import services.GetEmpService;
import model.EmployeeModel;

import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.sql.Statement;
import util.FridayRefactoringProperties;
import util.EmployeeQueryProvider;
import util.UtilTransform;
import java.util.ArrayList;
import java.util.Map;
import constant.Constant;

public class GetEmpService extends FridayRefactoringProperties {

	private final ArrayList<EmployeeModel> employeeList = new ArrayList<EmployeeModel>();

	private static Connection connection;

	private static Statement statement;

	private PreparedStatement preparedStatement;
	
	private static final Logger logger = Logger.getLogger(GetEmpService.class.getName());

	public GetEmpService() {
		try {
			Class.forName(properties.getProperty("driverName"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employyeeFromXML() {

		try {
			int s = UtilTransform.XMLXPATHS().size();
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTransform.XMLXPATHS().get(i);
				EmployeeModel employee = new EmployeeModel();
				employee.setEmployeeId(l.get(Constant.XPATH_EMPLOYEE_ID_KEY));
				employee.setFullName(l.get(Constant.XPATH_EMPLOYEE_NAME_KEY));
				employee.setAddress(l.get(Constant.XPATH_EMPLOYEE_ADDRESS_KEY));
				employee.setFacultyName(l.get(Constant.XPATH_FACULTY_NAME_KEY));
				employee.getDepartment(l.get(Constant.XPATH_DEPARTMENT_KEY));
				employee.setDesignation(l.get(Constant.XPATH_DESIGNATION_KEY));
				employeeList.add(employee);
				System.out.println(employee.toString() + "\n");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeTableCreate() {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(EmployeeQueryProvider.Query("q2"));
			statement.executeUpdate(EmployeeQueryProvider.Query("q1"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeesAdd() {
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryProvider.Query("q3"));
			connection.setAutoCommit(false);
			for(int i = 0; i < employeeList.size(); i++){
				EmployeeModel e = employeeList.get(i);
				preparedStatement.setString(1, e.getEmployee());
				preparedStatement.setString(2, e.getFullName());
				preparedStatement.setString(3, e.getAddress());
				preparedStatement.setString(4, e.getFacultyName());
				preparedStatement.setString(5, e.getDepartment());
				preparedStatement.setString(6, e.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeGetByID(String eid) {

		EmployeeModel e = new EmployeeModel();
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryProvider.Query("q4"));
			preparedStatement.setString(1, eid);
			ResultSet R = preparedStatement.executeQuery();
			while (R.next()) {
				e.setEmployeeId(R.getString(1));
				e.setFullName(R.getString(2));
				e.setAddress(R.getString(3));
				e.setFacultyName(R.getString(4));
				e.getDepartment(R.getString(5));
				e.setDesignation(R.getString(6));
			}
			ArrayList<EmployeeModel> l = new ArrayList<EmployeeModel>();
			l.add(e);
			employeeOutput(l);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeDelete(String eid) {

		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryProvider.Query("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeDisplay() {

		ArrayList<EmployeeModel> employeeList = new ArrayList<EmployeeModel>();
		
		try {
			preparedStatement = connection.prepareStatement(EmployeeQueryProvider.Query("q5"));
			ResultSet r = preparedStatement.executeQuery();
			while (r.next()) {
				EmployeeModel e = new EmployeeModel();
				e.setEmployeeId(r.getString(1));
				e.setFullName(r.getString(2));
				e.setAddress(r.getString(3));
				e.setFacultyName(r.getString(4));
				e.getDepartment(r.getString(5));
				e.setDesignation(r.getString(6));
				employeeList.add(e);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
		employeeOutput(employeeList);
	}
	
	public void employeeOutput(ArrayList<EmployeeModel> l){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
		for(int i = 0; i < l.size(); i++){
			EmployeeModel e = l.get(i);
			System.out.println("Employee:"+ e);
			System.out.println(e.getEmployee() + "\t" + e.getFullName() + "\t\t"
					+ e.getAddress() + "\t" + e.getFacultyName() + "\t" + e.getDepartment() + "\t"
					+ e.getDesignation() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
