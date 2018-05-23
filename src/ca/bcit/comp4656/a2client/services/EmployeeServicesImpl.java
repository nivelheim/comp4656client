package ca.bcit.comp4656.a2client.services;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import ca.bcit.comp4656.a2client.datamodels.Employee;
import ca.bcit.comp4656.a2client.datamodels.ResponseCode;

public class EmployeeServicesImpl implements EmployeeServices, Serializable {
	
	private static final long serialVersionUID = -52230821895421259L;
	private ClientConfig config;
	private Client client;
	private WebResource service;
	private ObjectMapper objectMapper;
	private TypeFactory typeFactory;

	public EmployeeServicesImpl() {
		config = new DefaultClientConfig();
		client = Client.create(config);
		service = client.resource(getBaseURI());
		objectMapper = new ObjectMapper();
		typeFactory = objectMapper.getTypeFactory();
	}
	
	public String getEmps() {
		return service.path("/employee/employees").accept(MediaType.APPLICATION_JSON).get(String.class);
	}
	
	public List<Employee> getEmployees() {
		String responseStr = service.path("/employee/employees").accept(MediaType.APPLICATION_JSON).get(String.class);
		List<Employee> responseList = new ArrayList<Employee>();
		
		try {
			 responseList = objectMapper.readValue(responseStr, typeFactory.constructCollectionType(ArrayList.class, Employee.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseList;
	}

	public Employee getEmployee(String id) {
		String responseStr = service.path("employee/get/" + id).accept(MediaType.APPLICATION_JSON).get(String.class);
		Employee emp = new Employee();
		
		try {
			emp = objectMapper.readValue(responseStr, typeFactory.constructType(Employee.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public ResponseCode findEmployee(String id) {
		String responseStr = service.path("employee/find/" + id).accept(MediaType.APPLICATION_JSON).get(String.class);
		ResponseCode rc = new ResponseCode();
		try {
			rc = objectMapper.readValue(responseStr, typeFactory.constructType(ResponseCode.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rc;
	}

	@Override
	public ResponseCode addEmployee(Employee emp) {
		/*
		String obj = "";
		try {
			obj = objectMapper.writeValueAsString(emp);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		String responseStr = service.path("Employee/add").accept(MediaType.APPLICATION_JSON).post(String.class, obj);
		*/
		String responseStr = service.path("employee/add").accept(MediaType.APPLICATION_JSON).post(String.class, emp);
		ResponseCode rc = new ResponseCode();
		
		try {
			rc = objectMapper.readValue(responseStr, typeFactory.constructType(ResponseCode.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rc;
	}

	@Override
	public ResponseCode deleteEmployee(String id) {
		String responseStr = service.path("employee/delete/" + id).accept(MediaType.APPLICATION_JSON).delete(String.class);
		ResponseCode rc = new ResponseCode();
		
		try {
			rc = objectMapper.readValue(responseStr, typeFactory.constructType(ResponseCode.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	@Override
	public ResponseCode updateEmployee(Employee emp) {
		String responseStr = service.path("employee/update").accept(MediaType.APPLICATION_JSON).put(String.class, emp);
		ResponseCode rc = new ResponseCode();
		
		try {
			rc = objectMapper.readValue(responseStr, typeFactory.constructType(ResponseCode.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/a00918606ws/services").build();
	}

}
