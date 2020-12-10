import java.sql.*;

/**
 * @formatter:off
 * To use Action, 
 * 		1. Create an Action object by calling 
 * 		   Action(String username, String password, Connection dbconn) 
 * 		2. Action.insert(relation, String[]) to insert into the table relation with the attributes in the string array.
 * 		   Action.update(String relation, String PK, String PKValue, String[] fieldsToUpdate, String tempLine[])
 * 		   			to update the fields with the primary key, PK = PKValue from a relation table.
 *         Action.delete(relation, String PrimaryKey) to delete from a table relation. 
 *         		(deleting from the ProductShipment will require the partial)
 * @formatter:on
 */

public class Action {

	static final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	static String username = null, password = null;
	static Connection dbconn = null;

	public Action(String usernameIn, String passwordIn, Connection dbconnIn) {
		this.username = usernameIn;
		this.password = passwordIn;
		this.dbconn = dbconnIn;
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: insert(String relation, String tempLine[]) inserts a record into a relation table with 
	 * 		the attributes of tempLine.
	 * 
	 * Parameters: String relation is the table be inserted into
	 * 				String array tempLine is an array of the values of the fields
	 * 
	 * Purpose: To set the query string for a record insertion
	 * 
	 * Returns: ret - a string representation of an insert query
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public void insert(String relation, String tempLine[]) {

		String query = insertHelper(relation, tempLine);
		executeQuery(query);

	} // end insert

	private String insertHelper(String relation, String[] tempLine) {
		String ret = ""; // the SQL insertion line

		if (relation.contentEquals("Member")) {
			ret = "INSERT INTO Member(memberID, firstName, lastName, dob, address, phoneNumber, rewardPoints)"
					+ " VALUES('" + tempLine[0] + "', '" + tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3]
					+ "', '" + tempLine[4] + "', '" + tempLine[5] + "', " + "'" + tempLine[6] + "')";

		} else if (relation.contentEquals("Sale")) {
			ret = "INSERT INTO Sale(saleID, dateOf, paymentMethod, totalPrice, memberID)" + " VALUES('" + tempLine[0]
					+ "', '" + tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3] + "', '" + tempLine[4] + "')";

		} else if (relation.contentEquals("SubSale")) {
			ret = "INSERT INTO SubSale(saleID, subSaleID, productID, price, amount)" + " VALUES('" + tempLine[0]
					+ "', '" + tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3] + "', '" + tempLine[4] + "')";

		} else if (relation.contentEquals("Emp")) {
			ret = "INSERT INTO Emp(employeeID, firstName, lastName, gender, address, phoneNumber, groupID, salary)"
					+ " VALUES('" + tempLine[0] + "', '" + tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3]
					+ "', '" + tempLine[4] + "', '" + tempLine[5] + "', " + "'" + tempLine[6] + "', '" + tempLine[7]
					+ "')";
		} else if (relation.contentEquals("Supplier")) {
			ret = "INSERT INTO Supplier(supplierID, name, address, contactPerson)" + " VALUES('" + tempLine[0] + "', '"
					+ tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3] + "')";

		} else if (relation.contentEquals("Product")) {
			ret = "INSERT INTO Product(productID, name, retailPrice, category, membershipDiscount, stockInfo)"
					+ " VALUES('" + tempLine[0] + "', '" + tempLine[1] + "', '" + tempLine[2] + "', '" + tempLine[3]
					+ "', '" + tempLine[4] + "', '" + tempLine[5] + "')";

		} else if (relation.contentEquals("ProductShipment")) {
			ret = "INSERT INTO ProductShipment(incomingDate, purchasePrice, amount)" + " VALUES('" + tempLine[0]
					+ "', '" + tempLine[1] + "', '" + tempLine[2] + "')";
		}

		return ret;
	} // end insertHelper

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: update(String relation, String[] fieldsToUpdate, String tempLine[]) updates a record 
	 * 		with a relation table with the attributes of tempLine given a Primary key.
	 * 
	 * Parameters: String relation is the table be inserted into
	 * 				String PK is the primary key field name to identify the record
	 * 				String PKValue is the value of the primary key
	 * 				String fieldsToUpdate is an array of the fields that are being updated
	 * 				String array tempLine is an array of the values of the fields
	 * 
	 * Purpose: To set the query string for a record insertion
	 * 
	 * Returns: ret - a string representation of an insert query
	 * 
	 * IMPORTANT: The fieldsToUpdate and the tempLine values must be in the same order and the same size
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public void update(String relation, String PK, String PKValue, String[] fieldsToUpdate, String tempLine[]) {
		if (fieldsToUpdate.length != tempLine.length) {
			System.out.println("Upate failed: Number of fields and values are not equal.");
		}
		String query = updateHelper(relation, PK, PKValue, fieldsToUpdate, tempLine);
		executeQuery(query);
	} // end update

	private String updateHelper(String relation, String PK, String PKValue, String[] fieldsToUpdate,
			String tempLine[]) {
		String ret = "";
		ret += "UPDATE " + relation + " ";
		ret += "SET ";
		for (int i = 0; i < fieldsToUpdate.length; i++) {
			ret += fieldsToUpdate[i] + " = " + tempLine[i];
			if (i + 1 < fieldsToUpdate.length) {
				ret += ", ";
			} else {
				ret += " ";
			}
		}
		ret += "WHERE " + PK + " = " + PKValue;
		return ret;
	} // end updateHelper

	public void delete(String relation, String PK, String PKValue) {
		String query = deleteHelper(relation, PK, PKValue);
		executeQuery(query);
	} // end delete

	private String deleteHelper(String relation, String PK, String PKValue) {
		String ret = "";
		ret += "DELETE FROM " + relation + " WHERE " + PK + " = " + PKValue;
		return ret;
	} // end delteHelper

	// check to see if the value exists in the relation
	// return true if the value exits
	static boolean checkID(String relation, String value) {
		String PK = null;
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
		String sqlCommand = "SELECT " + PK + " FROM " + relation + " WHERE EXISTS " + "(SELECT " + PK + " FROM "
				+ relation + " WHERE " + PK + " = '" + value + "' )";

		// create a statement
		Statement stmt;
		try {
			stmt = dbconn.createStatement();
		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		// execute the SQL command
		ResultSet answer = null;
		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(sqlCommand);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + sqlCommand + "]");
			e.printStackTrace();
		}

		// get the answer
		String validID = null;
		try {
			if (answer == null) {
				System.out.println("PK is valid.");
			} else {
				validID = answer.getString(PK);
			}
		} catch (SQLException e) {
			System.out.println(value + " is not in the " + relation + " Table");
			e.printStackTrace();
		}

		// return true if the value was found
		if (validID != null) {
			return true;
		} else {
			return false;
		}
	}

	private void executeQuery(String query) {
		// create a statement
		Statement stmt;
		try {
			stmt = dbconn.createStatement();
		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		// execute the insert query
		try {
			stmt = dbconn.createStatement();
			stmt.executeQuery(query);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + query + "]");
			e.printStackTrace();
		}
	} // end executeQuery
} // end Action
