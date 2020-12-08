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
import ContainerClasses.Member;
import ContainerClasses.Product;


/**
 * Authot: Cullen Bates
 * 
 * CMD Instructions example:
 *  	not implemented yet
 * 
 * Will create input files for each table for prog 4.
 * booleans decide which file will be made 
 */





//creates the input files for use with the database
public class InputFileCreator {

	// these container classes will handle employee info
	public static Employee employee = new Employee();
	public static List<Employee> generatedEmployees;
	public static int duplicatedEmps = 0;

	// these containers will be responsible for member info
	public static Member member = new Member();
	public static List<Member> generatedMembers;
	public static int duplicatedMembers = 0;
	
	//these containers will be responsible for product info
	public static Product product = new Product();
	public static List<Product> generatedProducts;
	
	
	//triggers for making files
	static boolean makeEmps = true;
	static boolean makeMems = true;
	static boolean makeProducts = true;
	
	
	
	
	public static void main(String[] args) {

		if (makeEmps) {
			// generating the random employees
			generatedEmployees = new ArrayList<Employee>();
			
			
			for (int i = 0; i < 100; i++) {

				Employee randEmp = (Employee) employee.GetNewRandom();

				// check for duplicates
				while (IsDuplicateEmployee(randEmp) && generatedEmployees.size() > 1) {
					System.out.println("Rerolling Duplicate ID");
					duplicatedEmps++;
					randEmp = (Employee) employee.GetNewRandom();
				}
				// add it to our list
				generatedEmployees.add(randEmp);
				String empLine = GetEmpString(randEmp);
				//System.out.println(empLine);
				//System.out.println("Duplicates Found:" + duplicatedEmps);
			}

			try {
				WriteEmployees();
			} catch (IOException e) {
				System.out.println("Failed to write employee.txt");
			}
			
			System.out.println("Wrote Employees");
			System.out.println("employeeID,firstName,lastName,gender,address,phoneNumber,groupID,salary");

		}
		// finished generating employees

		// generating supplier

		// finished supplier

		// generating product file
		if(makeProducts) {
			generatedProducts = new ArrayList<Product>();
			
			
			
			for (int i = 0; i < 100; i++) {

				Product randProd = (Product) product.GetNewRandom();

				// check for duplicates
				while (IsDuplicateProduct(randProd) && generatedProducts.size() > 1) {
					System.out.println("Rerolling Duplicate Prodcut");
					randProd = (Product) product.GetNewRandom();
				}
				// add it to our list
				generatedProducts.add(randProd);
				String memLine = GetProdString(randProd);
				//System.out.println(memLine);
				//System.out.println("Duplicates Found:" + duplicatedEmps);
			}

			try {
				WriteProducts();
			} catch (IOException e) {
				System.out.println("Failed to write employee.txt");
			}

			System.out.println("Wrote Products");
			System.out.println("productID,name,retailPrice,category,membershipDiscount,stockInfo");
		}
		// finished product file

		// generating member file
		if (makeMems) {
			generatedMembers = new ArrayList<Member>();
			
			

			for (int i = 0; i < 100; i++) {

				Member radMem = (Member) member.GetNewRandom();

				// check for duplicates
				while (IsDuplicateMember(radMem) && generatedMembers.size() > 1) {
					System.out.println("Rerolling Duplicate ID");
					duplicatedEmps++;
					radMem = (Member) member.GetNewRandom();
				}
				// add it to our list
				generatedMembers.add(radMem);
				String memLine = GetMemString(radMem);
				//System.out.println(memLine);
				//System.out.println("Duplicates Found:" + duplicatedEmps);
			}

			try {
				WriteMembers();
			} catch (IOException e) {
				System.out.println("Failed to write employee.txt");
			}
			

			System.out.println("Wrote Members");
			System.out.println("memberID,firstName,lastName,dob,address,phoneNumber,rewardPoints");
			
		}
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
		// write header
		bw.write("employeeID,firstName,lastName,gender,address,phoneNumber,groupID,salary");
		bw.newLine();
		// write all other employees
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
				+ randEmp.getGender() + "," + randEmp.getAddress() + "," + randEmp.getPhoneNumber() + ","
				+ randEmp.getGroupID() + "," + randEmp.getSalary();
		return empLine;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * returns weather or not the memberID already exists in the system
	 * 
	 * @param emp
	 * @return
	 */
	public static boolean IsDuplicateMember(Member m) {
		for (Member curr : generatedMembers) {
			if (m.getMemberID().equals(curr.getMemberID())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * writes the members to the member file
	 * 
	 * @param emp
	 * @return
	 */
	private static void WriteMembers() throws IOException {

		File fout = new File("members.txt");
		FileOutputStream fos;
		fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		// write header
		bw.write("memberID,firstName,lastName,dob,address,phoneNumber,rewardPoints");
		bw.newLine();
		// write all other employees
		for (Member curr : generatedMembers) {
			bw.write(GetMemString(curr));
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
	private static String GetMemString(Member randMem) {
		String memLine = randMem.getMemberID() + "," + randMem.getFirstName() + "," + randMem.getLastName() + ","
				+ randMem.getDob() + "," + randMem.getAddress() + "," + randMem.getPhoneNumber() + ","
				+ randMem.getRewardPoints();
		return memLine;
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * returns weather or not the product id already exists in the system
	 * 
	 * @param emp
	 * @return
	 */
	public static boolean IsDuplicateProduct(Product p) {
		for (Product curr : generatedProducts) {
			if (p.getProductID().equals(curr.getProductID())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * writes the products to the product file
	 * 
	 * @param emp
	 * @return
	 */
	private static void WriteProducts() throws IOException {

		File fout = new File("products.txt");
		FileOutputStream fos;
		fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		// write header
		bw.write("productID,name,retailPrice,category,membershipDiscount,stockInfo");
		bw.newLine();
		// write all other employees
		for (Product curr : generatedProducts) {
			bw.write(GetProdString(curr));
			bw.newLine();
		}
		bw.close();
	}

	/**
	 * returns the properly formatted product string
	 * 
	 * @param emp
	 * @return
	 */
	private static String GetProdString(Product randProd) {
		String memLine = randProd.getProductID() + "," + randProd.getName() + "," + randProd.getRetailPrice() + ","
				+ randProd.getCategory() + "," + randProd.getMembershipDiscount() + "," + randProd.getStockInfo();
		return memLine;
	}

	
}
