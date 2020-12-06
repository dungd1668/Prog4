package InputCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ContainerClasses.Employee;

//creates the input files for use with the database
public class InputFileCreator {

	// these container classes will return new random containers of data
	public static Employee employee = new Employee();
	public static List<Employee> generatedEmployees;
	public static int duplicatedEmps = 0;

	
	public static void main(String[] args) {

		// generating the random employees
		generatedEmployees = new ArrayList<Employee>();

		for (int i = 0; i < 100; i++) {

			Employee randEmp = (Employee) employee.GetNewRandom(employee.getId(), false);
			
			//check for duplicates
			while (IsDuplicateEmployee(randEmp) && generatedEmployees.size() < 1) {
				System.out.println("Rerolling Duplicate Name");
				duplicatedEmps++;
				randEmp = (Employee) employee.GetNewRandom(employee.getId(), true);
			}
			//add it to our list
			generatedEmployees.add(randEmp);
			System.out.println(randEmp.getFirstName() + " " + randEmp.getLastName() + " ID:" + randEmp.getEmployeeID() + " Adress: " + randEmp.getAddress());
			//System.out.println("Duplicates Found:" + duplicatedEmps);
		}
		
		// finished generating employees

		
		//generating supplier 
		
		
		//finished supplier
		
		
		//generating product file
		
		
		//finished product file
		
		
		//generating member file
		
		
		//finished member file
		
		
		
		//generating sales file
		
		
		//finished sales file
		
	}
	

	/**
	 * returns weather or not the employeeID already exists in the system
	 * 
	 * @param emp
	 * @return
	 */
	public static boolean IsDuplicateEmployee(Employee emp) {
		for (Employee curr : generatedEmployees) {
			if (emp.getEmployeeID() == curr.getEmployeeID()) {
				return true;
			}
		}
		return false;
	}

}
