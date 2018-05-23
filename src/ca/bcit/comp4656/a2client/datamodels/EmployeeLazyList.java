package ca.bcit.comp4656.a2client.datamodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ca.bcit.comp4656.a2client.services.EmployeeServicesImpl;
import ca.bcit.comp4656.a2client.util.LazySorter;

public class EmployeeLazyList extends LazyDataModel<Employee> {

	private static final long serialVersionUID = -5799997942987583768L;
	private List<Employee> employees;
	
	public EmployeeLazyList() {
		EmployeeServicesImpl empServ = new EmployeeServicesImpl();
		this.employees = empServ.getEmployees();
	}
	
	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		if ( sortField!=null ){
			Collections.sort(employees, new LazySorter(sortField, sortOrder));
		}
		
		if ( filters!=null && filters.size()>0 ){
			if ( filters.keySet().contains("firstName")){
				List<Employee> localList =  employees.stream().collect(Collectors.toList());
				localList = filterByFirstName( localList, filters.get("firstName") );
				setRowCount(employees.size());
				setPageSize(pageSize);
				return localList;
			}
		}
		
		setRowCount(employees.size());
		setPageSize(pageSize);
		
		if ( employees.size()>pageSize ){
			try{
				return new ArrayList<>(employees.subList(first, first+pageSize));
			}catch (IndexOutOfBoundsException e ){
				return new ArrayList<>(employees.subList(first,  first+(employees.size()%pageSize)));
			}
		}
		else{
			return new ArrayList<>(employees);
		}
	}
	
	private List<Employee> filterByFirstName(List<Employee> list, Object object) {
		String firstName = (String)object;
		
		return employees.stream()
			.filter( e -> e.getFirstName().contains(firstName) )
			.collect(Collectors.toList());
	}
	
	@Override
	public Employee getRowData(String employeeId) {
		for (Employee employee : employees) {
			if (employeeId.equals(employee.getEmployeeId())) {
				return employee;
			}
		}
		return null;
	}
	
	@Override
	public int getRowCount() {
		return employees.size();
	}
	
	

}
