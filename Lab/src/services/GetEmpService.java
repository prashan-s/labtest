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
import util.UtilC;
import util.UtilQ;
import util.UtilTransform;
import java.util.ArrayList;
import java.util.Map;

public class GetEmpService extends UtilC {

	private final ArrayList<EmployeeModel> el = new ArrayList<EmployeeModel>();

	private static Connection c;

	private static Statement s;

	private PreparedStatement ps;
	
	private static final Logger logger = Logger.getLogger(GetEmpService.class.getName());

	public GetEmpService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employyeeFromXML() {

		try {
			int s = UtilTransform.XMLXPATHS().size();
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTransform.XMLXPATHS().get(i);
				EmployeeModel EMPLOYEE = new EmployeeModel();
				EMPLOYEE.setEmployeeId(l.get("XpathEmployeeIDKey"));
				EMPLOYEE.setFullName(l.get("XpathEmployeeNameKey"));
				EMPLOYEE.setAddress(l.get("XpathEmployeeAddressKey"));
				EMPLOYEE.setFacultyName(l.get("XpathFacultyNameKey"));
				EMPLOYEE.getDepartment(l.get("XpathDepartmentKey"));
				EMPLOYEE.setDesignation(l.get("XpathDesignationKey"));
				el.add(EMPLOYEE);
				System.out.println(EMPLOYEE.toString() + "\n");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeTableCreate() {
		try {
			s = c.createStatement();
			s.executeUpdate(UtilQ.Query("q2"));
			s.executeUpdate(UtilQ.Query("q1"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeesAdd() {
		try {
			ps = c.prepareStatement(UtilQ.Query("q3"));
			c.setAutoCommit(false);
			for(int i = 0; i < el.size(); i++){
				EmployeeModel e = el.get(i);
				ps.setString(1, e.getEmployee());
				ps.setString(2, e.getFullName());
				ps.setString(3, e.getAddress());
				ps.setString(4, e.getFacultyName());
				ps.setString(5, e.getDepartment());
				ps.setString(6, e.getDesignation());
				ps.addBatch();
			}
			ps.executeBatch();
			c.commit();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeGetByID(String eid) {

		EmployeeModel e = new EmployeeModel();
		try {
			ps = c.prepareStatement(UtilQ.Query("q4"));
			ps.setString(1, eid);
			ResultSet R = ps.executeQuery();
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
			ps = c.prepareStatement(UtilQ.Query("q6"));
			ps.setString(1, eid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public void employeeDisplay() {

		ArrayList<EmployeeModel> l = new ArrayList<EmployeeModel>();
		try {
			ps = c.prepareStatement(UtilQ.Query("q5"));
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				EmployeeModel e = new EmployeeModel();
				e.setEmployeeId(r.getString(1));
				e.setFullName(r.getString(2));
				e.setAddress(r.getString(3));
				e.setFacultyName(r.getString(4));
				e.getDepartment(r.getString(5));
				e.setDesignation(r.getString(6));
				l.add(e);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
		employeeOutput(l);
	}
	
	public void employeeOutput(ArrayList<EmployeeModel> l){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
		for(int i = 0; i < l.size(); i++){
			EmployeeModel e = l.get(i);
			System.out.println(e.getEmployee() + "\t" + e.getFullName() + "\t\t"
					+ e.getAddress() + "\t" + e.getFacultyName() + "\t" + e.getDepartment() + "\t"
					+ e.getDesignation() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
