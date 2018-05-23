package ca.bcit.comp4656.a2client.services;

import java.util.List;
import ca.bcit.comp4656.a2client.datamodels.Employee;
import ca.bcit.comp4656.a2client.datamodels.ResponseCode;

public interface EmployeeServices {
	public List<Employee> getEmployees();
	
	public Employee getEmployee(String id);
	
	public ResponseCode findEmployee(String id);
	
	public ResponseCode addEmployee(Employee emp);
	
	public ResponseCode deleteEmployee(String id);
	
	public ResponseCode updateEmployee(Employee emp);
}
