
/**
 * 
 * CMD Instructions example:
 *  	javac CreateRelationTable.java
 *  	java CreateRelationTable username password fileName relation
 * 
 * The input file should be a csv file 
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

	static String query = ""; // a query string to create the table

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

		// create an Action object
		action = new Action(username, password, dbconn);

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

		// Create a statement
		Statement stmt = null;
		try {
			stmt = dbconn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Create the table
		try {
			// delete the table if it already exists
			stmt.executeQuery("DROP TABLE " + relation);

			String createTable = null; // the sql string to create a table
			// create table @formatter:off
			if (relation.contentEquals("Member")) {
//				String memberID = generateMemberID();
				createTable = "CREATE TABLE Member (" 
						+ "memberID varchar2(10) NOT NULL,"
						+ "firstName varchar2(15) NOT NULL," 
						+ "lastName varchar2(15) NOT NULL," 
						+ "dob varchar2(10),"
						+ "address varchar2(35)," 
						+ "phoneNumber integer(10) NOT NULL," 
						+ "rewardPoints integer(8) )";
			} else if (relation.contentEquals("Sale")) {
				createTable = "CREATE TABLE Sale (" 
						+ "saleID varchar2(10) NOT NULL,"
						+ "date varchar2(10),"
						+ "paymentMethod varchar2(20),"
						+ "totalPrice decimal(8,2),"
						+ "memberID varchar2(10) NOT NULL )";
			} else if (relation.contentEquals("SubSale")) {
				createTable = "CREATE TABLE SubSale (" 
						+ "saleID varchar2(10) NOT NULL,"
						+ "SubSaleID varchar2(10),"
						+ "productID varchar2(10),"
						+ "price decimal(8,2),"
						+ "amount integer(8) )";
			} else if (relation.contentEquals("Employee")) {
				createTable = "CREATE TABLE Employee (" 
						+ "employeeID varchar2(10) NOT NULL,"
						+ "firstName varchar2(15) NOT NULL,"
						+ "lastName varchar2(15) NOT NULL,"
						+ "gender varchar2(15),"
						+ "address varchar2(35),"
						+ "phoneNumber integer(10) NOT NULL," 
						+ "groupID integer(1),"
						+ "salary decimal(8,2) )";
			} else if (relation.contentEquals("Supplier")) {
				createTable = "CREATE TABLE Supplier (" 
						+ "supplierID varchar2(10) NOT NULL,"
						+ "name varchar2(25),"
						+ "address varchar2(35),"
						+ "contactPerson varchar2(25) )";
			} else if (relation.contentEquals("Product")) {
				createTable = "CREATE TABLE Product (" 
						+ "productID varchar2(10) NOT NULL,"
						+ "name varchar2(25),"
						+ "retailPrice decimal(8,2),"
						+ "category varchar2(15),"
						+ "membershipDiscount decimal(8,2),"
						+ "stockInfo integer(8) )";
			} else if (relation.contentEquals("ProductShipment")) {
				createTable = "CREATE TABLE ProductShipment ("
						+ "incomingDate varchar2(10) NOT NULL,"
						+ "purchasePrice decimal(8,2),"
						+ "amount integer(8) )";
			}
			// @formatter:on

			// check if the sql command was set
			assert createTable != null;

			stmt.executeQuery(createTable);

			// add the contents of the csv file to the sql table
			try {
				int count = addFile(stmt);
				System.out.println("records inserted: " + count);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// close the connection to the DBMS
			stmt.close();
			dbconn.close();

		} catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not fetch query results.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
	}

	private static int addFile(Statement stmt) throws FileNotFoundException {
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

				// System.out.println(query);
				try {
					stmt = dbconn.createStatement();
					stmt.executeQuery(query);
					stmt.close();
					count++;
				} catch (SQLException e) {
					System.out.println(query);
					e.printStackTrace();
				}

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

	// private static String generateMemberID() {
	// Statement idToCheck = null;
	// ResultSet answer = null;
	// try {
	// idToCheck = dbconn.createStatement();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// // check if the generated ID already exists
	// String idCheck = "SELECT memberID FROM Member WHERE EXISTS (" + "SELECT
	// memberID FROM Member WHERE memberID = "
	// + idToCheck + ")";
	// boolean idExists = false;
	// try {
	// answer = idToCheck.executeQuery(idCheck);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// try {
	// if (answer != null) {
	// answer.next();
	// if (answer.getBoolean(columnLabel))
	// idExists = true;
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }
}
