package ca.bcit.comp4656.a2client.util;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

import ca.bcit.comp4656.a2client.datamodels.Employee;


public class LazySorter implements Comparator<Employee> {

	
	private String sortField;
	private SortOrder sortOrder;
	
	public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
	
	@Override
	public int compare(Employee e1, Employee e2) {
		
		try {
			//Accessing the class field via Reflection:
			Field field1 = Employee.class.getDeclaredField( this.sortField );
			field1.setAccessible(true);
			
			Field field2 = Employee.class.getDeclaredField(this.sortField);
			field2.setAccessible(true);
			
			
			Object value1 = field1.get(e1);
			Object value2 = field2.get(e2);
			
			if (value1 == null) {
				value1 = new Date(0);
			}
			
			if (value2 == null) {
				value2 = new Date(0);
			}
			@SuppressWarnings({ "rawtypes", "unchecked" }) //There're not much I can do about raw type casting so I suppressed the warnings!
			int value = ((Comparable)value1).compareTo(value2);
	        return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
