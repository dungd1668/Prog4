// @authors: David Dung, Graysen Meyers, Cullen Bates

import java.io.*;
import java.sql.*; // For access to the SQL interaction methods
import java.util.Scanner;

public class Prog4 {
	final static String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	static String username, password;

	static Connection dbconn = null;

	static Action action;

	public static void main(String[] args) {
		// get the username and password
		getLogin(args);

		// create an Action object
		action = new Action(username, password, dbconn);

		// get user input
		launch();
	}

	private static void launch() {
		Scanner scan = new Scanner(System.in);
		String userInput = null;
		System.out.println("Welcome to the text interface for the Tucson Mall database!");
		displayMenu();
		while (true) {
			userInput = scan.nextLine();
			if (userInput.equals("0")) {
				// user decided to end program
				scan.close();
				break;
			}
			if (userInput.length() != 1 || !(userInput.equals("1") || 
					userInput.equals("2") || userInput.equals("3"))) {
				errorMessage();
			} else {
				if (userInput.equals("1")) {
					displayInsertMenu();
					insertRecord(scan);
				} else if (userInput.equals("2")) {
					displayDeleteMenu();
					deleteRecord(scan);
				} else {
					displayUpdateMenu();
					updateRecord(scan);
				}
			}
			System.out.println();
			displayMenu();
		}
		exitProgram();
	}

	private static void insertRecord(Scanner scan) {
		String userInput = null;
		
		userInput = scan.nextLine();
		if (userInput.equals("0")) {
			return;
		}
		if (userInput.length() != 1 || !(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") 
			|| userInput.equals("4") || userInput.equals("5") || userInput.equals("6"))) {
			errorMessage();
			displayInsertMenu();
			insertRecord(scan);
		} else {
			if (userInput.equals("1")) {
				// member
				gatherInsertMember();
			} else if (userInput.equals("2")) {
				// emp
				gatherInsertEmp();
			} else if (userInput.equals("3")) {
				// product
				gatherInsertProduct();
			} else if (userInput.equals("4")) {
				// supplier
				gatherInsertSupplier();
			} else if (userInput.equals("5")) {
				// sale
				gatherInsertSale();
			} else {
				// subsale
				gatherInsertSubSale();
			}
		}
	}

	private static void updateRecord(Scanner scan) {
		String userInput = null;
		
		userInput = scan.nextLine();
		if (userInput.equals("0")) {
			return;
		}
		if (userInput.length() != 1 || !(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") 
			|| userInput.equals("4") || userInput.equals("5") || userInput.equals("6"))) {
			errorMessage();
			displayUpdateMenu();
			updateRecord(scan);
		} else {
			if (userInput.equals("1")) {
				// member
			} else if (userInput.equals("2")) {
				// emp
			} else if (userInput.equals("3")) {
				// product
			} else if (userInput.equals("4")) {
				// supplier
			} else if (userInput.equals("5")) {
				// sale
			} else {
				// subsale
			}
		}
	}

	private static void deleteRecord(Scanner scan) {
		String userInput = null;
		userInput = scan.nextLine();
		if (userInput.equals("0")) {
			return;
		}
		if (userInput.length() != 1 || !(userInput.equals("1") || userInput.equals("2") 
			|| userInput.equals("3") || userInput.equals("4"))) {
			errorMessage();
			displayDeleteMenu();
			deleteRecord(scan);
		} else {
			if (userInput.equals("1")) {
				// member
			} else if (userInput.equals("2")) {
				// emp
			} else if (userInput.equals("3")) {
				// product
			} else {
				// supplier
			}
		}
	}

	// these helper methods will be needed for each type of insert, delete, or update
	// I added all the insert ones but the delete and udpate need to be added as well

	private static void gatherInsertMember() {
		// use scanner to gather insert member info from user (make sure to validate as shown on spec)
		
		// build a string array with the member info and then call action.insert(member, stringArray)
	}

	private static void gatherInsertEmp() {

	}

	private static void gatherInsertProduct() {

	}

	private static void gatherInsertSupplier() {

	}

	private static void gatherInsertSale() {

	}

	private static void gatherInsertSubSale() {
		
	}

	private static void displayMenu() {
		System.out.print("Enter 1 to INSERT a record\n" + "Enter 2 to DELETE a record\n"
				+ "Enter 3 to UPDATE a record\n" + "Enter 0 to EXIT\n" + "Enter here: ");
	}

	private static void displayInsertMenu() {
		System.out.print("\nEnter 1 to INSERT a member\n" + "Enter 2 to INSERT an employee\n"
				+ "Enter 3 to INSERT a product\n" + "Enter 4 to INSERT a supplier\n" + "Enter 5 to INSERT a sale\n"
				+ "Enter 6 to INSERT a subsale\n" + "Enter 0 to return to main menu\n" + "Enter here: ");
	}

	private static void displayUpdateMenu() {
		System.out.print("\nEnter 1 to UPDATE a member\n" + "Enter 2 to UPDATE an employee\n"
				+ "Enter 3 to UPDATE a product\n" + "Enter 4 to UPDATE a supplier\n" + "Enter 5 to UPDATE a sale\n"
				+ "Enter 6 to UPDATE a subsale\n" + "Enter 0 to return to main menu\n" + "Enter here: ");
	}

	private static void displayDeleteMenu() {
		System.out.print("\nEnter 1 to DELETE a member\n" + "Enter 2 to DELETE an employee\n" + "Enter 3 to DELETE a product\n"
				+ "Enter 4 to DELETE a supplier\n" + "Enter 0 to return to main menu\n" + "Enter here: ");
	}

	private static void exitProgram() {
		try {
			dbconn.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not close dbconn.");
			System.exit(-1);
		}
		System.out.println("Thank you for using the Tucson Mall text interface!" + "\nHave a nice day!");
		System.exit(0);
	}

	private static void errorMessage() {
		System.out.println("\nPlease enter a number from the menu!\n");
	}

	private static void getLogin(String[] args) {
		if (args.length == 2) { // get username/password from cmd line args
			username = args[0];
			password = args[1];
		} else {
			System.out.println("\nUsage:  java JDBC <username> <password>\n"
					+ "    where <username> is your Oracle DBMS" + " username,\n    and <password> is your Oracle"
					+ " password (not your system password).\n");
			System.exit(-1);
		}
		// load the (Oracle) JDBC driver by initializing its base
		// class, 'oracle.jdbc.OracleDriver'.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.  \n"
					+ "\tPerhaps the driver is not on the Classpath?");
			System.exit(-1);
		}
		// make and return a database connection to the user's Oracle database
		dbconn = null;
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password);
		} catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
	}
}