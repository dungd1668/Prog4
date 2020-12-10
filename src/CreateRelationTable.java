/**
 * @formatter:off
 * 
 * CreateRelationTable.java is a Program that creates an tables for a SQL database containing the Tucson mall 
 * database for Black Friday.
 * The tables can be accessed using the prefix dungd.TABLE where TABLE if the table name,
 * 		Member, Emp, Sale, SubSale, Supplier, Product, ProductShipment
 * 
 * Important: the Program also needs valid ORACLE SQL login information provided through the
 * arguments like:
 * 		java Prog3 USERNAME PASSWORD
 * 
 * @formatter:on
 * 
 * CMD Instructions example:
 *  	javac CreateRelationTable.java
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
 * @author David Dung
 * 
 */
import java.io.*;
import java.text.Normalizer;
import java.sql.*;

public class CreateRelationTable {
	static final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	static String username = null, password = null;
	static Connection dbconn = null;
	static String fileName; // the name of the input file
	static String relation; // the name of the table to be created
	static Action action;

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: main(String[] args) is the main function of the CreateRelationTable application. 
	 * The program create a table using the contents of the file provided with the name, relation.
	 * 
	 * Parameters: String[] args are the ORACLE login credentials, username, password, filename, relation
	 * 				args[0] should be a username
	 * 				args[1] should be a password
	 * 				args[2] should be the filename
	 * 				args[3] should be the table relation
	 * 
	 * Purpose: Start of the CreateTableRelation. The method first creates a link using ORACLE and then it 
	 * 		prompt the user for input, runs the query and prints the results to the screen. 
	 * 
	 * Returns: none
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public static void main(String[] args) {
		// System.out.println("Running");

		// get username/password from cmd line args
		if (args.length == 4) {
			username = args[0];
			password = args[1];
			fileName = args[2];
			relation = args[3];
		} else {
			System.out.println("\nUsage:  java JDBC <username> <password>\n"
					+ "    where <username> is your Oracle DBMS" + " username,\n    and <password> is your Oracle"
					+ " password (not your system password).\n" + "args[2] is the file location.\n"
					+ "args[3] is the name of the table to be created.\n");
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
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password);
		} catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}

		// create an Action object
		action = new Action(username, password, dbconn);

		// Create the table
		try {
			Statement stmt = dbconn.createStatement();
			stmt.executeQuery("drop table " + relation);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Table " + relation + " did not exist: Continuing...");
		}

		String createTable = null; // the sql string to create a table
		// create table @formatter:off
		if (relation.contentEquals("Member")) {
			createTable = "CREATE TABLE Member (" 
					+ "memberID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "firstName varchar2(15) NOT NULL," 
					+ "lastName varchar2(15) NOT NULL," 
					+ "dob varchar2(10),"
					+ "address varchar2(60)," 
					+ "phoneNumber integer NOT NULL," 
					+ "rewardPoints integer )";
		} else if (relation.contentEquals("Sale")) {
			createTable = "CREATE TABLE Sale (" 
					+ "saleID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "dateOf varchar2(10),"
					+ "paymentMethod varchar2(20),"
					+ "totalPrice decimal(8,2),"
					+ "memberID varchar2(10) NOT NULL )";
		} else if (relation.contentEquals("SubSale")) {
			createTable = "CREATE TABLE SubSale (" 
					+ "saleID varchar2(10) NOT NULL,"
					+ "SubSaleID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "productID varchar2(10),"
					+ "price decimal(8,2),"
					+ "amount integer )";
		} else if (relation.contentEquals("Emp")) {
			createTable = "CREATE TABLE Emp (" 
					+ "employeeID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "firstName varchar2(15) NOT NULL,"
					+ "lastName varchar2(15) NOT NULL,"
					+ "gender varchar2(15),"
					+ "address varchar2(60),"
					+ "phoneNumber integer NOT NULL," 
					+ "groupID integer,"
					+ "salary decimal(8,2) )";
		} else if (relation.contentEquals("Supplier")) {
			createTable = "CREATE TABLE Supplier (" 
					+ "supplierID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "name varchar2(25),"
					+ "address varchar2(60),"
					+ "contactPerson varchar2(25) )";
		} else if (relation.contentEquals("Product")) {
			createTable = "CREATE TABLE Product (" 
					+ "productID varchar2(10) NOT NULL PRIMARY KEY,"
					+ "name varchar2(25),"
					+ "retailPrice decimal(8,2),"
					+ "category varchar2(15),"
					+ "membershipDiscount decimal(8,2),"
					+ "stockInfo integer )";
		} else if (relation.contentEquals("ProductShipment")) {
			createTable = "CREATE TABLE ProductShipment ("
					+ "incomingDate varchar2(10) NOT NULL,"
					+ "purchasePrice decimal(8,2),"
					+ "amount integer )";
		}
		// @formatter:on

		// check if the sql command was set
		assert createTable != null;
		try {
			Statement stmt = dbconn.createStatement();
			stmt.executeQuery(createTable);
			stmt.close();
		} catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not create table " + relation + ".");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}

		// add the contents of the csv file to the sql table
		try {
			int count = addFile();
			System.out.println("records inserted: " + count);
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not open file " + fileName);
			System.exit(-1);
		}

		// close the connection to the DBMS
		try {

			dbconn.close();
		} catch (SQLException e) {
			System.err.println("Unable to close connection.");
			e.printStackTrace();
		}

	} // end main

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: addFile() opens a file and inserts each record into the database using an Action object.
	 * Action.insert(...) is used. 
	 * 
	 * Purpose: To insert a csv file into the database
	 * 
	 * Returns: count is the number of records inserted into the table
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static int addFile() throws FileNotFoundException {
		FileReader file; // used for the input file

		// initialize the file and the buffer
		file = new FileReader(fileName);
		BufferedReader in = new BufferedReader(file);

		// initialize and empty line
		String line = "";

		// read the documentation line
		try {
			line = in.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// read the first line of data from the csv file
		try {
			line = in.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int count = 0; // the number of records that were inserted

		// Iterate through the file and process each line
		try {
			while (line != null) {

				// IMPORTANT: IF THE STRING "Pause***Pause" IS READ, THE PROGRAM WILL STOP
				// READING
				if (line.contentEquals("Pause***Pause")) {
					break;
				}

				if (line.startsWith("\\\\") || line.startsWith("//")) {
					line = in.readLine();
					continue;
				}

				line = line.trim();
				if (line.length() < 1) {
					line = in.readLine();
					continue;
				}

				// Split the csv line
				String[] tempLine = splitLine(line);
				tempLine = normalize(tempLine);
				action.insert(relation, tempLine);
				count++;

				line = in.readLine();
			} // end while
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	} // end addFile

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: splitLine(String line) takes a csv String line and iterates through
	 * the string and splits the string into fields separated by commma's. 
	 * 
	 * Note: comma's inside of quotations and comma's following a backslash will not
	 * be counted commas. Quotation marks following a backslash will not be counted.
	 * i.e. The comma's in "first,second" and "third\,fourth" don't count
	 * 
	 * Purpose: To split a csv string into fields and return it as an array
	 * 
	 * Parameters: String line is a line
	 * 
	 * Returns: tempLine is an array of the fields
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static String[] splitLine(String line) {
		// normalize the line
		line = Normalizer.normalize(line, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");

		// call a function to normalized data fields (add spaces, convert special
		// characters)
		String[] tempLine = new String[10]; // Array to hold the fields of the data from each line
		int fieldIndex = 0; // integer to keep track of the current field being added
		int i = 0; // integer to keep track of the current index of the line
		String field = ""; // String to represent the current field
		boolean insideQuotation = false; // Boolean to indicate when a string is inside of a quotations

		// A while loop that iterates through the line to separate the fields
		while (i < line.length()) {
			// Check to see if the quotation mark is a real quotation
			if (line.charAt(i) == '"') {
				// Check if first character
				if (i - 1 >= 0) {
					if (line.charAt(i - 1) != '\\') {
						// if it is real, change the state insideQuotation
						if (insideQuotation)
							insideQuotation = false;
						else
							insideQuotation = true;
					}
				} else {
					if (insideQuotation)
						insideQuotation = false;
					else
						insideQuotation = true;
				}
			}

			// Check if a real comma is reached (not inside of a String) - if so, then add
			// it to tempLine[]
			if ((line.charAt(i) == ',' && !insideQuotation) || i == line.length() - 1) {
				if (i == line.length() - 1) {
					field += line.charAt(i);
				}

				// Check if the field is empty
				if (field.contentEquals("")) {
					field = "";
				}

				if (field.contentEquals("")) {
					tempLine[fieldIndex] = "NULL";
				} else {
					tempLine[fieldIndex] = field;
				}
				fieldIndex++;
				field = "";
				i++;
				continue;
			}

			// If no special character is found, add the character at index i to the current
			// string "field"
			field += line.charAt(i);

			// increment i
			i++;
		} // end while

		return tempLine;
	} // end splitLine

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: normalize(String[] tempLine) formats a String array by deleting null
	 * and empty strings.
	 * 
	 * Purpose: to format a String array
	 * 
	 * Parameters: String[] tempLine - a string array
	 * 
	 * Returns: a String array of a normalized array
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	private static String[] normalize(String[] tempLine) {
		String[] ret = new String[tempLine.length];
		int i = 0;
		for (String field : tempLine) {

			if (field == null) {
				ret[i] = null;
				i++;
				continue;
			} else if (field == "") {
				ret[i] = "";
				i++;
				continue;
			}

			String n = "";
			for (int j = 0; j < field.length(); j++) {
				if (field.charAt(j) == '\'') {
					n += "'";
				}
				n += field.charAt(j);
			}
			ret[i] = n;
			i++;
		}
		return ret;
	} // end normalize

} // end CreateRelationTable
