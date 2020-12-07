package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Employee extends Container {
	
	String employeeID;
	String firstName;
	String lastName;
	String gender;
	String address;
	String phoneNumber;
	int groupID;
	float salary;
	
	float minSalary = 10000;
	float maxSalary = 150000;

	public Employee() {
		
	}
	
	//constructor that takes params for each field
	public Employee(String newID, String f, String l, String g, String a
			,String phone, int group, float sal) {
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
	public Container GetNewRandom() {
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
		
		float salary = minSalary + rand.nextFloat() * (maxSalary - minSalary);
		salary = (float) (Math.round(salary * 100.0)/100.0);
		//make the new employee
		Employee newEmp = new Employee(getRandID(),
				GetRandomFirstName(),
				GetRandomLastName(),
				gen,
				GetRandomAddress(),
				GetRandomPhone(),
				group,
				salary
				);
		
		phone.close();
		//if its a duplicate dont increment id
		return newEmp;
	}
	

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	
	
}
