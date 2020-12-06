package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Employee extends Container {
	
	int employeeID = 0;
	String firstName = "";
	String lastName = "";
	String gender = "";
	String address = "";
	int phoneNumber = 0;
	int groupID = 0;
	int salary = 0;
	

	public Employee() {
		
	}
	
	//constructor that takes params for each field
	public Employee(int newID, String f, String l, String g, String a
			,int phone, int group, int sal) {
		employeeID = newID;
		firstName = f;
		lastName = l;
		gender = g;
		address = a;
		phoneNumber = phone;
		groupID = group;
		salary = sal;
	}
	
	//returns a random employee
	public Container GetNewRandom(int currID, boolean reroll) {
		//random gender
		Random rand = new Random();
		
		String gen = "";
		if(rand.nextInt(2) == 0) {
			gen = "Male";
		}
		else
			gen = "Female";
		
		IntStream phone = rand.ints(100000,999999);
		
		int group = rand.nextInt(3);
		
		IntStream salary = rand.ints(10000,150000);
		
		//make the new employee
		Employee newEmp = new Employee(currID,GetRandomFirstName(),GetRandomLastName(),
				gen,GetRandomAddress(),phone.findFirst().getAsInt(),group,salary.findFirst().getAsInt());
		phone.close();
		salary.close();
		//if its a duplicate dont increment id
		if(reroll)
			return newEmp;
		id++;
		return newEmp;
	}
	
	
	

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	
	
}
