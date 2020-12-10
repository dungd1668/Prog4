
/**
 * @formatter:off
 * 
 * Prog4.java is a Program that prompts the user for input to decide what action to complete.
 * The tables can be accessed using the prefix username.TABLE where TABLE if the table name,
 * 		Member, Emp, Sale, SubSale, Supplier, Product, ProductShipment
 * 
 * Important: the Program also needs valid ORACLE SQL login information provided through the
 * arguments like:
 * 		java Prog3 USERNAME PASSWORD
 * 
 * @formatter:on
 * 
 * CMD Instructions example:
 *  	javac Prog4.java Action.java WebAppQuery.java
 *  	java CreateRelationTable username password fileName relation
 * 
 * The input file should be a csv file 
 * 
 * Instructors: Lester I. McCann
 * 		   TAs: Zheng Tang and Chenghao Xiong
 * 
 *	  Due Date: 12/10/2020
 * 
 * Operational Requirements: JavaSE-1.8
 * 
 * @author Cullen Bates, Nick DiMatteo, David Dung, Graysen Meyers
 * 
 */
import java.io.*;
import java.sql.*; // For access to the SQL interaction methods
import java.util.Scanner;

public class Prog4 {
	final static String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	static String username, password;

	static Connection dbconn = null;

	static Action action;
	static WebAppQuery query;

	/**
	 * @formatter:off --------------------------------------------------------------------------------------------------
	 *                Method: main(String[] args) is the main function of the Prog4
	 *                application. The program prompts the user for an input to
	 *                determine what task to complete. Then the program will prompt
	 *                the user for field values.
	 * 
	 *                Parameters: String[] args are the ORACLE login credentials
	 *                args[0] should be a username args[1] should be a password
	 * 
	 *                Purpose: Start of the Prog4. The method first creates a link
	 *                using ORACLE and then it prompt the user for input, runs the
	 *                query and prints the results to the screen.
	 * 
	 *                Returns: none
	 * 
	 *                ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public static void main(String[] args) {
		// get the username and password
		getLogin(args);

		// create an Action object
		action = new Action(username, password, dbconn);

		// added this this morning
		query = new WebAppQuery(username, password, dbconn);

		// get user input
		launch();
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: launch() is a function that retrieve the user's input 
	 * 
	 * Purpose: To get user input and call insertRecord(Scanner), deleteRecord(Scanner), or updateRecord(Scanner). 
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static void launch() {
		Scanner scan = new Scanner(System.in);
		int userInput;
		System.out.println("Welcome to the text interface for the Tucson Mall database!");
		displayMenu();
		while (true) {
			userInput = scan.nextInt();

			switch (userInput) {

			case (0):
				scan.close();
				exitProgram();
				break;

			case (1):
				displayInsertMenu();
				insertRecord(scan);
				break;

			case (2):
				displayDeleteMenu();
				deleteRecord(scan);
				break;

			case (3):
				displayUpdateMenu();
				updateRecord(scan);
				break;
			// memebrs by phone num
			case (4):
				String num = null;
				System.out.print("Please enter 7-11 digit phone number: ");
				num = scan.nextLine();
				while (num.length() < 7) {
					System.out.println("Not a valid number, try again.");
					System.out.print("Please enter 7-11 digit phone number: ");
					num = scan.nextLine();
				}
				// Query here
				query.displayMemberByPhoneNum(num);
				break;
			// member by id
			case (5):
				System.out.print("Please enter an ID:");
				String id = null;
				id = scan.nextLine();
				while (action.checkID("Member", id)) {
					System.out.println("Not a valid id, try again.");
					System.out.print("Please enter an ID:");
					id = scan.nextLine().trim();
				}
				// Query here
				query.displayMemberById(id);
				break;

			case (6):
				System.out.println("Displaying current months profit.");
				// Query here
				query.displayCurrentMonthProfit("12", "2020");
				break;

			}
			/*
			 * 
			 * if (userInput.equals("0")) { // user decided to end program scan.close();
			 * break; } if (userInput.length() != 1 || !(userInput.equals("1") ||
			 * userInput.equals("2") || userInput.equals("3"))) { errorMessage(); } else {
			 * if (userInput.equals("1")) { displayInsertMenu(); insertRecord(scan); } else
			 * if (userInput.equals("2")) { displayDeleteMenu(); deleteRecord(scan); } else
			 * { displayUpdateMenu(); updateRecord(scan); } }
			 */

			System.out.println();
			displayMenu();
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: insertRecord(Scanner scan) a function that prompts the user for a table to insert into
	 * and calls gatherInsert(String relation) to get the input to insert.
	 * 
	 * Parameters: Scanner scan is a scanner to get user input
	 * 
	 * Purpose: To get user input and insert into a relation table
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
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
				gatherInsert("Member");
			} else if (userInput.equals("2")) {
				// emp
				gatherInsert("Emp");
			} else if (userInput.equals("3")) {
				// product
				gatherInsert("Product");
			} else if (userInput.equals("4")) {
				// supplier
				gatherInsert("Supplier");
			} else if (userInput.equals("5")) {
				// sale
				gatherInsert("Sale");
			} else {
				// subsale
				gatherInsert("SubSale");
			}
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: updateRecord(Scanner scan) a function that prompts the user for a table to update into
	 * and calls gatherUpdate(String relation) to get the input to insert.
	 * 
	 * Parameters: Scanner scan is a scanner to get user input
	 * 
	 * Purpose: To get user input and update a table record
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
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
				gatherUpdate("Member");
			} else if (userInput.equals("2")) {
				// emp
				gatherUpdate("Emp");
			} else if (userInput.equals("3")) {
				// product
				gatherUpdate("Product");
			} else if (userInput.equals("4")) {
				// supplier
				gatherUpdate("Supplier");
			} else if (userInput.equals("5")) {
				// sale
				gatherUpdate("Sale");
			} else {
				// subsale
				gatherUpdate("SubSale");
			}
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: deleteRecord(Scanner scan) a function that prompts the user for a table to delete from
	 * and calls deleteRecord(String relation) to get the input to delete.
	 * 
	 * Parameters: Scanner scan is a scanner to get user input
	 * 
	 * Purpose: To get user input and delete a table record
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static void deleteRecord(Scanner scan) {
		String userInput = null;
		userInput = scan.nextLine();
		if (userInput.equals("0")) {
			return;
		}
		if (userInput.length() != 1 || !(userInput.equals("1") || userInput.equals("2") || userInput.equals("3")
				|| userInput.equals("4"))) {
			errorMessage();
			displayDeleteMenu();
			deleteRecord(scan);
		} else {
			if (userInput.equals("1")) {
				// member
				gatherDelete("Member");
			} else if (userInput.equals("2")) {
				// emp
				gatherDelete("Emp");
			} else if (userInput.equals("3")) {
				// product
				gatherDelete("Product");
			} else {
				// supplier
				gatherDelete("Supplier");
			}
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: gatherInsert(String relation) is a function that gets valid user input to insert into a 
	 * table.
	 * 
	 * Parameters: String relation is a string that represents a table name
	 * 
	 * Purpose: To get user input to insert into a table
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static void gatherInsert(String relation) {
		// use scanner to gather insert info from user (make sure to validate as
		// shown on spec)
		Scanner sc = new Scanner(System.in);

		// arrays of each of the table fields
		String[] memberFields = { "Member ID (NOT NULL) PK", "First Name (NOT NULL)", "Last Name (NOT NULL)",
				"Date of Birth (MM/DD/YYYY)", "Address", "Phone Number (NOT NULL)", "Reward Points" };
		String[] empFields = { "Employee ID (NOT NULL) PK", "First Name (NOT NULL)", "Last Name (NOT NULL)", "Gender",
				"Address", "Phone Number (NOT NULL)", "Group ID", "Salary" };
		String[] productFields = { "Product ID (NOT NULL) PK", "Name", "Retail Price", "Category",
				"Membership Discount", "Stock Info" };
		String[] supplierFields = { "Supplier ID (NOT NULL) PK", "Name", "Address", "Contact Person" };
		String[] saleFields = { "Sale ID (NOT NULL)", "Date (MM/DD/YYY)", "Payment Method", "Total Price",
				"Member ID (NOT NULL)" };
		String[] subSaleFields = { "Sale ID (NOT NULL)", "Sub-Sale ID (NOT NULL) PK", "Product ID", "Price", "Amount" };

		// build a string array with the member info and then call
		// action.insert(member,stringArray)

		// set the length of the general fields array
		String[] fields;
		String PK = null;
		int numberOfFields = 0;
		if (relation.equals("Member")) {
			// member
			numberOfFields = 7;
			fields = memberFields;
			PK = "memberID";
		} else if (relation.equals("Emp")) {
			// emp
			numberOfFields = 8;
			fields = empFields;
			PK = "employeeID";
		} else if (relation.equals("Product")) {
			// product
			numberOfFields = 6;
			fields = productFields;
			PK = "productID";
		} else if (relation.equals("Supplier")) {
			// supplier
			numberOfFields = 4;
			fields = supplierFields;
			PK = "supplierID";
		} else if (relation.equals("Sale")) {
			// sale
			numberOfFields = 5;
			fields = saleFields;
			PK = "saleID";
		} else {
			// subsale
			numberOfFields = 5;
			fields = subSaleFields;
			PK = "subSaleID";
		}

		// get the user input for each field
		String[] input = new String[numberOfFields];

		// @formatter:off
		System.out.println(
				"Number of values that need to be provided. Optional fields can be skipped by providing no input.\n");
		for (int i = 0; i < numberOfFields; i++) {
			System.out.print("\t" + fields[i] + ":");

			String userInput = sc.nextLine();
			userInput = userInput.trim();

			// check if the input is valid
			while (
			// check if the primary key already exists
			(fields[i].contains("PK") && action.checkID(relation, userInput))
					// check if the field can be null
					|| (userInput.length() == 0 && fields[i].contains("(NOT NULL)"))
					// check if the date is formatted correctly
					|| ((fields[i].contains("Date") && userInput.length() > 0) && userInput.charAt(2) != '/'
							&& userInput.charAt(5) != '/')
					|| ( // check if the field is numeric
					(fields[i].contains("Phone Number") || fields[i].contains("Salary")
							|| fields[i].contains("Retail Price") || fields[i].contains("Stock")
							|| fields[i].contains("Reward Points") || fields[i].contains("Total Price")
							|| fields[i].contains("Group ID") || fields[i].contains("Membership Discount"))
							&& !isNumeric(userInput) && userInput.length() > 0)) {
				System.out.println("INVALID INPUT. TRY AGAIN.");

				// print null value needs to be filled
				if (userInput.length() == 0 && fields[i].contains("(NOT NULL)")) {
					System.out.println("ERROR: " + fields[i] + " CANNOT BE EMPTY.");
				}

				// print date need to be formatted
				if ((fields[i].contains("Date") && userInput.length() > 0) && userInput.charAt(2) != '/'
						&& userInput.charAt(5) != '/') {
					System.out.println("ERROR: DATE NEEDS TO HAVE THE FORMAT: MM/DD/YYYY.");
				}

				// print phone number needs to be numeric
				if ((fields[i].contains("Phone Number") || fields[i].contains("Salary")
						|| fields[i].contains("Retail Price") || fields[i].contains("Stock")
						|| fields[i].contains("Reward Points") || fields[i].contains("Total Price")
						|| fields[i].contains("Group ID") || fields[i].contains("Membership Discount"))
						&& !isNumeric(userInput)) {
					System.out.println("ERROR: " + fields[i] + " NEEDS TO BE NUMERIC.");
				}
				// print primary key needs to be unique
				if (fields[i].contains("PK") && action.checkID(relation, userInput)) {
					System.out.println("ERROR: " + fields[i] + " NEEDS TO BE UNIQUE. " + userInput + " ALREADY EXISTS");
				}

				// prompt the user again
				System.out.print("\t" + fields[i] + ":");

				userInput = sc.nextLine();
				userInput = userInput.trim();
			} // end while

			input[i] = userInput;
		} // end for

		action.insert(relation, input);
		// @formatter:on
	} // end gatherInsert

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: gatherUpdate(String relation) is a function that gets valid user input to update a record
	 * in a table
	 * 
	 * Parameters: String relation is a string that represents a table name
	 * 
	 * Purpose: To get user input to update a table record
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static void gatherUpdate(String relation) {
		// use scanner to gather insert info from user (make sure to validate as
		// shown on spec)
		Scanner sc = new Scanner(System.in);

		// arrays of each of the table field names to be printed
		String[] memberFields = { "Member ID (NOT NULL)", "First Name (NOT NULL)", "Last Name (NOT NULL)",
				"Date of Birth (MM/DD/YYY)", "Address", "Phone Number (NOT NULL)", "Reward Points" };
		String[] empFields = { "Employee ID (NOT NULL)", "First Name (NOT NULL)", "Last Name (NOT NULL)", "Gender",
				"Address", "Phone Number (NOT NULL)", "Group ID", "Salary" };
		String[] productFields = { "Product ID (NOT NULL)", "Name", "Retail Price", "Category", "Membership Discount",
				"Stock Info" };
		String[] supplierFields = { "Supplier ID (NOT NULL)", "Name", "Address", "Contact Person" };
		String[] saleFields = { "Sale ID (NOT NULL)", "Date Of Sale (MM/DD/YYY)", "Payment Method", "Total Price",
				"Member ID (NOT NULL)" };
		String[] subSaleFields = { "Sale ID (NOT NULL)", "Sub-Sale ID", "Product ID", "Price", "Amount" };

		// arrays of each of the actual table fields
		String[] member = { "memberID", "firstName", "lastName", "dob", "address", "phoneNumber", "rewardPoints" };
		String[] emp = { "employeeID", "firstName", "lastName", "gender", "address", "phoneNumber", "groupID",
				"salary" };
		String[] product = { "productID", "name", "retailPrice", "category", "membershipDiscount", "stockInfo" };
		String[] supplier = { "supplierID", "name", "address", "contactPerson" };
		String[] sale = { "saleID", "dateOf", "paymentMethod", "totalPrice", "memberID" };
		String[] subSale = { "saleID", "subSaleID", "productID", "price", "amount" };

		// the field name of the primary key of a table
		String PK = null;

		// set the length of the general fields array
		String[] fields;
		String[] table;
		int numberOfFields = 0;
		if (relation.equals("Member")) {
			// member
			numberOfFields = 7;
			fields = memberFields;
			table = member;
			PK = "memberID";
		} else if (relation.equals("Emp")) {
			// emp
			numberOfFields = 8;
			fields = empFields;
			table = emp;
			PK = "employeeID";
		} else if (relation.equals("Product")) {
			// product
			numberOfFields = 6;
			fields = productFields;
			table = product;
			PK = "productID";
		} else if (relation.equals("Supplier")) {
			// supplier
			numberOfFields = 4;
			fields = supplierFields;
			table = supplier;
			PK = "supplierID";
		} else if (relation.equals("Sale")) {
			// sale
			numberOfFields = 5;
			fields = saleFields;
			table = sale;
			PK = "saleID";
		} else {
			// subsale
			numberOfFields = 5;
			fields = subSaleFields;
			table = subSale;
			PK = "subSaleID";
		}

		// get the user input for each field
		int curr = 0; // keeps track of the current index of the fieldsToUpdate and input arrays
		String[] fieldsToUpdate = new String[numberOfFields];
		String[] input = new String[numberOfFields];

		// get the primary key value
		System.out.print("Enter a primary key value for " + relation + " (" + PK + "):");
		String PKValue = sc.nextLine();
		PKValue = PKValue.trim();
		System.out.println();

		while (!action.checkID(relation, PKValue)) {
			System.out.println("Invalid Primary Key.");
			// get the primary key value
			System.out.print("Enter a primary key value for " + relation + " (" + PK + "):");
			PKValue = sc.nextLine();
			PKValue = PKValue.trim();
			System.out.println();
		}

		// @formatter:off
		System.out.println(
				"Number of values that need to be provided. Optional fields can be skipped by providing no input.\n");
		for (int i = 0; i < numberOfFields; i++) {

			// skip the ID fields
			if (fields[i].contains("ID")) {
				continue;
			}

			System.out.print("\t" + fields[i] + ":");

			String userInput = sc.nextLine();
			userInput = userInput.trim();

			while (((fields[i].contains("Date") && userInput.length() > 0)) || ( 
					// check if these fields are numeric
					(fields[i].contains("Phone Number") || fields[i].contains("Salary") || fields[i].contains("Retail Price")
					|| fields[i].contains("Stock") || fields[i].contains("Reward Points")
					|| fields[i].contains("Total Price") || fields[i].contains("Group ID")
					|| fields[i].contains("Membership Discount")) && !isNumeric(userInput) && userInput.length() > 0)) {

				if (userInput.length() == 0) {
					break;
				}

				if (userInput.charAt(2) == '/' && userInput.charAt(5) == '/' && isNumeric(userInput.substring(0, 2))
						&& isNumeric(userInput.substring(3, 5)) && isNumeric(userInput.substring(6))) {
					break;
				}

				System.out.println("INVALID INPUT. TRY AGAIN.");

				// print null value needs to be filled
				if (userInput.length() == 0 && fields[i].contains("(NOT NULL)")) {
					System.out.println("ERROR: " + fields[i] + " CANNOT BE EMPTY.");
				}

				// print date need to be formatted
				if ((fields[i].contains("Date") && userInput.length() > 0) && userInput.charAt(2) != '/'
						&& userInput.charAt(5) != '/') {
					System.out.println("ERROR: DATE NEEDS TO HAVE THE FORMAT: MM/DD/YYYY.");
				}

				// print phone number needs to be numeric
				if ((fields[i].contains("Phone Number") || fields[i].contains("Salary")
						|| fields[i].contains("Retail Price") || fields[i].contains("Stock")
						|| fields[i].contains("Reward Points") || fields[i].contains("Total Price")
						|| fields[i].contains("Group ID") || fields[i].contains("Membership Discount"))
						&& !isNumeric(userInput)) {
					System.out.println("ERROR: " + fields[i] + " NEEDS TO BE NUMERIC.");
				}

				// prompt the user again
				System.out.print("\t" + fields[i] + ":");

				userInput = sc.nextLine();
				userInput = userInput.trim();
			} // end while
				// @formatter:on

			// updated the fields to update if the user provided valid input
			if (userInput.length() != 0 && userInput != null) {
				System.out.println(fields[i] + ">>>" + table[i]);
				System.out.println(input[curr] + ">>>" + userInput);
				fieldsToUpdate[curr] = table[i];
				input[curr] = userInput;
				curr++;
			}

		} // end for loop

		// normalize the arrays
		fieldsToUpdate = normalize(fieldsToUpdate, curr);
		input = normalize(input, curr);

		// call the update function
		action.update(relation, PK, PKValue, fieldsToUpdate, input);
	} // end gatherUpdate

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: gatherDelete(String relation) is a function that gets valid user input to delete a record
	 * in a table
	 * 
	 * Parameters: String relation is a string that represents a table name
	 * 
	 * Purpose: To get user input to delete a table record
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static void gatherDelete(String relation) {
		// use scanner to gather delete info from user (make sure to validate as
		// shown on spec)
		Scanner sc = new Scanner(System.in);

		// set the length of the general fields array
		String PK;
		if (relation.equals("Member")) {
			// member
			PK = "memberID";
		} else if (relation.equals("Emp")) {
			// emp
			PK = "employeeID";
		} else if (relation.equals("Product")) {
			// product
			PK = "productID";
		} else if (relation.equals("Supplier")) {
			// supplier
			PK = "supplierID";
		} else if (relation.equals("Sale")) {
			// sale
			PK = "saleID";
		} else {
			// subsale
			PK = "subSaleID";
		}

		// get the primary key value
		System.out.print("Enter a primary key value for " + relation + ":");
		String PKValue = sc.nextLine();
		PKValue = PKValue.trim();
		System.out.println();

		while (!action.checkID(relation, PKValue)) {
			System.out.println(PKValue + " does not exist in " + relation);

			// get the primary key value
			System.out.print("Enter a primary key value for " + relation + ":");
			PKValue = sc.nextLine();
			PKValue = PKValue.trim();
			System.out.println();
		}

		action.delete(relation, PK, PKValue);
	} // end gatherDelete

	private static void displayMenu() {
		System.out
				.print("Enter 1 to INSERT a record\n" + "Enter 2 to DELETE a record\n" + "Enter 3 to UPDATE a record\n"
						+ "Enter 4 to display members by phone\n" + "Enter 5 to display member by id\n"
						+ "Enter 6 to display current month profit\n" + "Enter 0 to EXIT\n" + "Enter here: ");
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
		System.out.print(
				"\nEnter 1 to DELETE a member\n" + "Enter 2 to DELETE an employee\n" + "Enter 3 to DELETE a product\n"
						+ "Enter 4 to DELETE a supplier\n" + "Enter 0 to return to main menu\n" + "Enter here: ");
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: exitProgram() is a function that closes the database connection dbconn and closes the 
	 * program.
	 * 
	 * Purpose: To close the program
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
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

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: getLogin(String[] args) is a fuction that sets the username and password from args[] and 
	 * creates a database connection.
	 * 
	 * Parameters: String[] args are the ORACLE login credentials
	 * 				args[0] should be a username
	 * 				args[1] should be a password
	 * 
	 * Purpose: To set the username and password and to create the database connection
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
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

	/**
	 * --------------------------------------------------------------------------------------------------
	 * Method: isNumeric(String str) takes a string and determines if it is a
	 * numerical value. The function works by trying to convert the string into a
	 * double. If it passes, the string is a numerical value, if it fails, the
	 * string is not a numerical value.
	 * 
	 * Purpose: To determine whether or not a string is a numerical value. Used to
	 * check phoneNumber, salary, amount, price, groupID, reatailPrice,
	 * purchasePrice.
	 * 
	 * Parameters: String str - a string of a value need to be checked
	 * 
	 * Returns: boolean true - if the input string is numerical boolean false - if
	 * the input string is not numerical
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 */
	public static boolean isNumeric(String str) {
		if (str == null)
			return false;
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	} // end isNumeric

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: normalize(String[] tempLine) formats a String array by deleting null
	 * and empty strings.
	 * 
	 * Purpose: to format a String array
	 * 
	 * Parameters: String[] tempLine - a string array
	 * 				int size - the number of valid values in the array tempLine
	 * 
	 * Returns: a String array of a normalized array
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static String[] normalize(String[] tempLine, int size) {
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			String field = tempLine[i];
			if (field == null) {
				ret[i] = null;
				continue;
			} else if (field == "") {
				ret[i] = "";
				continue;
			} else {
				ret[i] = field;
			}
		}
		return ret;
	} // end normalize
}