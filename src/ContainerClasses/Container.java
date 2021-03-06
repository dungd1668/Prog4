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
public File nounFile = new File("src/RawRandomData/nouns.txt");

/***
 * returns a version of this container with random info 
 * 
 * @return
 */
public abstract Container GetNewRandom();






//RETURNS a random 10 char string as a new ID
	public String getRandID() {
		String r = "";
		Random ran = new Random();
		for(int i = 0; i < 10; i++) {
			char c = (char)(ran.nextInt(26) + 'a');
			r += c;
		}
		return r;
		
	}
	
	public long GetRandomPhone() {
		long num;
		Random ran = new Random();
		num =  (long)(1000000000 + ran.nextFloat() * (9999999999l - 1000000000));
		return num;
	}
	
	public String GetRandomDOB() {
		Random ran = new Random();
		int day = ran.nextInt(29) + 1;
		int month  = ran.nextInt(13);
		int year = (int) (1950 + ran.nextFloat() * (2000 - 950));
		return String.format("%d/%d/%d", day,month,year);
	}
	
	public String GetRandomSaleDate() {
		Random ran = new Random();
		int day = ran.nextInt(29) + 1;
		int month  = ran.nextInt(13);
		int year = (int) (2018 + ran.nextFloat() * (2020 - 2018));
		return String.format("%d/%d/%d", day,month,year);
	}

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
		//r = r.replace(",", "%");
		r = "\"" + r + "\"";
		scan.close();
		return r;
		
	} catch (FileNotFoundException e) {
		System.out.println("Cant Find File");
	}
	return "null";
}

/***
 * returns a random noun from nouns.txt
 */
public String GetRandomNoun() {
	try {
		Scanner scan = new Scanner(nounFile);
		int count = 0;
		while (scan.hasNextLine()) {
		    count++;
		    scan.nextLine();
		}
		scan.close();
		scan= new Scanner(nounFile);
		Random rand = new Random();
		int name = rand.nextInt(count);
		count = 0;
		while (scan.hasNextLine() && count < name) {
		    count++;
		    scan.nextLine();
		}
		String r = scan.nextLine();
		//r = r.replace(",", "%");
		r = "\"" + r + "\"";
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
