package InputCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
		System.out.println("employeeID,firstName,lastName,gender,address,phoneNumber,groupID,salary");
		
		for (int i = 0; i < 100; i++) {

			Employee randEmp = (Employee) employee.GetNewRandom();

			// check for duplicates
			while (IsDuplicateEmployee(randEmp) && generatedEmployees.size() > 1) {
				System.out.println("Rerolling Duplicate Name");
				duplicatedEmps++;
				randEmp = (Employee) employee.GetNewRandom();
			}
			// add it to our list
			generatedEmployees.add(randEmp);
			String empLine = GetEmpString(randEmp);
			System.out.println(empLine);
			// System.out.println("Duplicates Found:" + duplicatedEmps);
		}

		try {
			WriteEmployees();
		} catch (IOException e) {
			System.out.println("Failed to write employee.txt");
		}

		// finished generating employees

		// generating supplier

		// finished supplier

		// generating product file

		// finished product file

		// generating member file

		// finished member file

		// generating sales file

		// finished sales file

	}

	/**
	 * returns weather or not the employeeID already exists in the system
	 * 
	 * @param emp
	 * @return
	 */
	public static boolean IsDuplicateEmployee(Employee emp) {
		for (Employee curr : generatedEmployees) {
			if (emp.getEmployeeID().equals(curr.getEmployeeID())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * writes the employees to the employee file
	 * 
	 * @param emp
	 * @return
	 */
	private static void WriteEmployees() throws IOException {

		File fout = new File("employees.txt");
		FileOutputStream fos;
		fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		//write header
		bw.write("employeeID,firstName,lastName,gender,address,phoneNumber,groupID,salary");
		bw.newLine();
		//write all other employees
		for (Employee curr : generatedEmployees) {
			bw.write(GetEmpString(curr));
			bw.newLine();
		}
		bw.close();
	}
	/**
	 * returns the properly formatted employee string
	 * 
	 * @param emp
	 * @return
	 */
	private static String GetEmpString(Employee randEmp) {
		String empLine = randEmp.getEmployeeID() + "," + randEmp.getFirstName() + "," + randEmp.getLastName() + ","
				+ randEmp.getGender() + "," + randEmp.getAddress() + "," + randEmp.getPhoneNumber() + "," + randEmp.getGroupID() + ","
				+ randEmp.getSalary();
		return empLine;
	}

}
