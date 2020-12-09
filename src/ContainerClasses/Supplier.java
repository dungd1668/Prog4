package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Supplier extends Container {



	String supplierID;
	String name;
	String address;
	String contactPerson;

	public Supplier() {

	}

	
	
	
	
	public Supplier(String supplierID, String name, String address, String contactPerson) {
		super();
		this.supplierID = supplierID;
		this.name = name;
		this.address = address;
		this.contactPerson = contactPerson;
	}





	// returns a random sale
	public Container GetNewRandom() {

		Supplier newEmp = new Supplier(getRandID(), GetRandomFirstName() + " Supplies", GetRandomAddress(), GetRandomFirstName());

		// if its a duplicate dont increment id
		return newEmp;
	}





	public String getSupplierID() {
		return supplierID;
	}





	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public String getContactPerson() {
		return contactPerson;
	}





	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	
	
	
	

}
