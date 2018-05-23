package ca.bcit.comp4656.a2client.controller;


import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import ca.bcit.comp4656.a2client.datamodels.Employee;
import ca.bcit.comp4656.a2client.datamodels.EmployeeLazyList;
import ca.bcit.comp4656.a2client.datamodels.ResponseCode;
import ca.bcit.comp4656.a2client.services.EmployeeServicesImpl;

@ManagedBean(name="qrBean")
@ViewScoped
public class QueriesController implements Serializable {

	private static final long serialVersionUID = -4096191377787365218L;
	private LazyDataModel<Employee> employees;
	private transient EmployeeServicesImpl empServ;
	
	private String searchId;
	private String deleteId;
	private String addId;
	private String addFirstName;
	private String addLastName;
	private String addDateBirth; 
	
	private String message;
	private Properties props;
	
	@PostConstruct
	public void init(){
		this.setEmployees(new EmployeeLazyList());
		empServ = new EmployeeServicesImpl();
		props= new Properties();
		try {
			props.load(QueriesController.class.getResourceAsStream("/msg.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSearchId() {
		return searchId;
	}

	
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	
	public String getDeleteId() {
		return deleteId;
	}

	
	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}


	public String getAddId() {
		return addId;
	}



	public void setAddId(String addId) {
		this.addId = addId;
	}



	public String getAddFirstName() {
		return addFirstName;
	}



	public void setAddFirstName(String addFirstName) {
		this.addFirstName = addFirstName;
	}



	public String getAddLastName() {
		return addLastName;
	}



	public void setAddLastName(String addLastName) {
		this.addLastName = addLastName;
	}



	public String getAddDateBirth() {
		return addDateBirth;
	}



	public void setAddDateBirth(String addDateBirth) {
		this.addDateBirth = addDateBirth;
	}

	

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public LazyDataModel<Employee> getEmployees() {
		return employees;
	}


	public void setEmployees(LazyDataModel<Employee> employees) {
		this.employees = employees;
	}



	public void findEmployee() throws NullPointerException {
		empServ = new EmployeeServicesImpl();
		ResponseCode response = new ResponseCode();
		
		if (searchId.equals("") || searchId == null) {
			FacesContext.getCurrentInstance().addMessage("addMsg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("findError"), null));
		}
		
		else {
			response = empServ.findEmployee(searchId);
				
			if (response.getResponseCode() != 0) {
				FacesContext.getCurrentInstance().addMessage("searchMsg",
		                new FacesMessage(FacesMessage.SEVERITY_WARN, 
		                		props.getProperty("findError"), null));
			}
			else {
				String message = response.getNote();
				FacesContext.getCurrentInstance().addMessage("searchMsg",
		                new FacesMessage("Found: " + message));
				FacesContext.getCurrentInstance().addMessage("searchMsg",
		                new FacesMessage(props.getProperty("findSuccess")));
			}
				
	
			
			this.setNull();
		}
	}
	
	public void addEmployee() throws ClassNotFoundException {
		ResponseCode rc = new ResponseCode();
		Employee localEmp = new Employee();
		localEmp.setEmployeeId(addId);
		localEmp.setFirstName(addFirstName);
		localEmp.setLastName(addLastName);
		
		DateFormat fmt = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
		Date date = new Date();
		
		try {
			date = fmt.parse(addDateBirth);
			localEmp.setDob(date);
		} catch (ParseException e) {
			localEmp.setDob(null);
			e.printStackTrace();
		}
		
		empServ = new EmployeeServicesImpl();
		rc = empServ.addEmployee(localEmp);
		if (rc.getResponseCode() == 200) {
			FacesContext.getCurrentInstance().addMessage("addMsg",
	                new FacesMessage(props.getProperty("insertSuccess")));
		}	
		
		else if (rc.getResponseCode() == 901){
			FacesContext.getCurrentInstance().addMessage("addMsg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("insertError"), null));
		}
		
		else {
		FacesContext.getCurrentInstance().addMessage("addMsg",
				new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("insertDuplicate"), null));
		}
		
		this.reload();
		this.setNull();
	}
	
	public void deleteEmployee() {
		empServ = new EmployeeServicesImpl();
		ResponseCode rc = new ResponseCode();
		
		if (deleteId.equals("") || deleteId == null) {
			FacesContext.getCurrentInstance().addMessage("addMsg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("delError"), null));
		}
		
		else {
			rc = empServ.deleteEmployee(deleteId);
			if (rc.getResponseCode() == 1) {
				FacesContext.getCurrentInstance().addMessage("addMsg",
						new FacesMessage(props.getProperty("delSuccess")));;
			}
			else {
				FacesContext.getCurrentInstance().addMessage("addMsg",
						new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("delError"), null));
			}	
			
			this.reload();
			this.setNull();
		}
	}
	
	public void updateEmployee() throws ClassNotFoundException {
		ResponseCode rc = new ResponseCode();
		Employee localEmp = new Employee();
		localEmp.setEmployeeId(addId);
		localEmp.setFirstName(addFirstName);
		localEmp.setLastName(addLastName);
		
		empServ = new EmployeeServicesImpl();
		rc = empServ.updateEmployee(localEmp);
		if (rc.getResponseCode() == 200) {
			FacesContext.getCurrentInstance().addMessage("updMsg",
	                new FacesMessage(props.getProperty("updSuccess")));
		}	
		
		else if (rc.getResponseCode() == 901){
			FacesContext.getCurrentInstance().addMessage("updMsg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("updError"), null));
		}
		
		else {
		FacesContext.getCurrentInstance().addMessage("updMsg",
				new FacesMessage(FacesMessage.SEVERITY_WARN, props.getProperty("updError"), null));
		}
		
		this.reload();
		this.setNull();
	}
	
	public LazyDataModel<Employee> getAllEmployees() throws ClassNotFoundException, SQLException{
		return employees;
	}
	
	public void reload() {
		employees = new EmployeeLazyList();
	}

	
	/*
	public void logout() throws IOException{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.invalidateSession();
	    ec.redirect("index.xhtml");
	}
	*/
	
	public void reset() {
        RequestContext.getCurrentInstance().reset("delEmp:delMsg");
        RequestContext.getCurrentInstance().reset("searchEmp:searchMsg");
        RequestContext.getCurrentInstance().reset("addEmp:addMsg");
    }
	
	public void setNull() {
		this.searchId = null;
		this.deleteId = null;
		this.addId = null;
		this.addFirstName = null;
		this.addLastName = null;
		this.addDateBirth = null; 
		this.message = null;
	}



	


}
