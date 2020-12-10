// @authors: David Dung, Graysen Meyers, Cullen Bates

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
				System.out.println("Please enter 9 digit phone number");
				String num = scan.nextLine();
				while (num.length() < 9) {
					System.out.println("Not a valid number, try again");
					num = scan.nextLine();
				}
				// Query here
				query.displayMemberByPhoneNum(num);
				break;
			// member by id
			case (5):
				System.out.println("Please enter 10 digit id");
				String id = scan.nextLine();
				while (id.length() != 10) {
					System.out.println("Not a valid id, try again");
					id = scan.nextLine();
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

	private static void gatherInsert(String relation) {
		// use scanner to gather insert info from user (make sure to validate as
		// shown on spec)
		Scanner sc = new Scanner(System.in);

		// arrays of each of the table fields
		String[] memberFields = { "Member ID (NOT NULL) PK", "First Name (NOT NULL)", "Last Name (NOT NULL)",
				"Date of Birth (MM/DD/YYTY)", "Address", "Phone Number (NOT NULL)", "Reward Points" };
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

			while (((fields[i].contains("Date") && userInput.length() > 0)) || ( // check if these fields are numeric
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