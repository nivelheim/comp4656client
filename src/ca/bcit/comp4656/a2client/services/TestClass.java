package ca.bcit.comp4656.a2client.services;

import java.util.List;

import ca.bcit.comp4656.a2client.datamodels.Employee;

public class TestClass {

	public TestClass() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		EmployeeServicesImpl serv = new EmployeeServicesImpl();
		List<Employee> list = serv.getEmployees();
		Employee emp = new Employee();
		emp.setEmployeeId("A05999999");
		emp.setFirstName("Update");
		emp.setLastName("Update");
		for (Employee e : list) {
			System.out.println(e.getFirstName() + " " + e.getLastName());
		}
		
		System.out.println(serv.getEmployee("A01999999").getEmployeeId());
		
		System.out.println(serv.findEmployee("A01999999").getNote());
		
		System.out.println(serv.updateEmployee(emp));

	}

}
