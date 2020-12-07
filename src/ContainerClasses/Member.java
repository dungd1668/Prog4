package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Member extends Container {
	
	String memberID;
	String firstName;
	String lastName;
	String dob;
	String address;
	String phoneNumber;
	int rewardPoints;
	

	public Member() {
		
	}
	
	//constructor that takes params for each field
	public Member(String newID, String f, String l, String dob, String a
			,String phone, int rewards) {
		memberID = newID;
		firstName = f;
		lastName = l;
		this.dob = dob;
		address = a;
		phoneNumber = phone;
		rewardPoints = rewards;
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
		
		int rewards = rand.nextInt(200);
	
		
		//make the new employee
		Member newEmp = new Member(getRandID(),
				GetRandomFirstName(),
				GetRandomLastName(),
				GetRandomDOB(),
				GetRandomAddress(),
				GetRandomPhone(),
				rewards
				);
		
		//if its a duplicate dont increment id
		return newEmp;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	





	
	
}
