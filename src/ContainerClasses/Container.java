package ContainerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/***
 * A container class that will define
 * what we need in each container of data
 */
public abstract class Container {
	
public int id = 1;

public File firstNameFile = new File("src/RawRandomData/first-names.txt");
public File middleNameFile = new File("src/RawRandomData/middle-names.txt");
public File addressesFile = new File("src/RawRandomData/addresses.txt");


/***
 * returns a version of this container with random info 
 * 
 * @return
 */
public abstract Container GetNewRandom(int currID, boolean reroll);




/***
 * returns a random name from our random first name list
 */
public String GetRandomFirstName() {
	try {
		Scanner scan = new Scanner(firstNameFile);
		int count = 0;
		while (scan.hasNextLine()) {
		    count++;
		    scan.nextLine();
		}
		scan.close();
		scan= new Scanner(firstNameFile);
		Random rand = new Random();
		int name = rand.nextInt(count);
		count = 0;
		while (scan.hasNextLine() && count < name) {
		    count++;
		    scan.nextLine();
		}
		String r = scan.nextLine();
		scan.close();
		return r;
		
	} catch (FileNotFoundException e) {
		System.out.println("Cant Find File");
	}
	return "null";
}


/***
 * returns a random name from our random middle name list
 */
public String GetRandomLastName() {
	try {
		Scanner scan = new Scanner(middleNameFile);
		
		int count = 0;
		while (scan.hasNextLine()) {
		    count++;
		    scan.nextLine();
		}
		scan.close();
		scan= new Scanner(middleNameFile);
		Random rand = new Random();
		int name = rand.nextInt(count);
		count = 0;
		while (scan.hasNextLine() && count < name) {
		    count++;
		    scan.nextLine();
		}
		String r = scan.nextLine();
		scan.close();
		return r;
		
	} catch (FileNotFoundException e) {
		System.out.println("Cant Find File");
	}
	return "null";
}
/***
 * returns a random address from address.txt
 */
public String GetRandomAddress() {
	try {
		Scanner scan = new Scanner(addressesFile);
		int count = 0;
		while (scan.hasNextLine()) {
		    count++;
		    scan.nextLine();
		}
		scan.close();
		scan= new Scanner(addressesFile);
		Random rand = new Random();
		int name = rand.nextInt(count);
		count = 0;
		while (scan.hasNextLine() && count < name) {
		    count++;
		    scan.nextLine();
		}
		String r = scan.nextLine();
		r = r.replace(",", "%");
		scan.close();
		return r;
		
	} catch (FileNotFoundException e) {
		System.out.println("Cant Find File");
	}
	return "null";
}



public int getId() {
	return id;
}




public void setId(int id) {
	this.id = id;
}




}
