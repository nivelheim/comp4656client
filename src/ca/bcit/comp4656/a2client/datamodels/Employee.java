package ca.bcit.comp4656.a2client.datamodels;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Employee implements Serializable {

	private static final long serialVersionUID = 1718360042968214639L;
	private String employeeId;
	private String firstName;
	private String lastName;
	private Date dob;

	public Employee() {
			
	}
	
	public Employee(String id, String firstName, String lastName, Date dob) {
		this.employeeId = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
}
