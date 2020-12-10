import java.io.*;
import java.sql.*;

/**
 * @formatter:off
 * To use WebAppQuery, 
 * 		1. Create an WebAppQuery object by calling 
 * 		   WebAppQuery(String username, String password, Connection dbconn) 
 * 		2. Then use any of the methods to print the queries for the "Web App" portion
 * 		   of the queries.
 * @formatter:on
 */
public class WebAppQuery {
	private String username = null;
	private String password = null;
	private Connection dbconn = null;

	public WebAppQuery(String username, String password, Connection dbconn) {
		this.username = username;
		this.password = password;
		this.dbconn = dbconn;
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: displayMemberByPhoneNum(String phoneNum) displays member information when given the
	 * 		   member's phone number.
	 * 
	 * Parameters: String phoneNum is the given phone number to search by.
	 * 
	 * Purpose: To get a user's information.
	 * 
	 * Returns: N/A.
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public void displayMemberByPhoneNum(String phoneNum) {
		String query = "SELECT firstName, lastName, dob, rewardPoints FROM " + username
				+ ".Member WHERE phoneNumber = '" + phoneNum + "'";

		// create a statement
		Statement stmt = null;
		ResultSet answer = null;

		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		try {
			while (answer.next()) {
				System.out.println("Member: " + answer.getString("firstName") + " " + answer.getString("lastName"));
				System.out.println("Birth Date: " + answer.getString("dob"));
				System.out.println("Reward Points: " + answer.getInt("rewardPoints") + "\n");
			}

			stmt.close();

		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + query + "]");
			System.out.println("Problem getting result from ResultSet in function displayMemberByPhoneNum");
			e.printStackTrace();
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: displayMemberById(String memberId) displays member information when given the member's ID
	 * 		   number.
	 * 
	 * Parameters: String memberId is the given member ID to search by.
	 * 
	 * Purpose: To get a user's information.
	 * 
	 * Returns: N/A.
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public void displayMemberById(String memberId) {
		String query = "SELECT firstName, lastName, dob, rewardPoints FROM Member WHERE memberID = '" + memberId + "'";

		// create a statement
		Statement stmt = null;
		ResultSet answer = null;

		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		try {
			if (answer == null) {
				System.out.println("There was no user found with Member Id: " + memberId + "\n");
			} else {
				System.out.println("Member: " + answer.getString("firstName") + " " + answer.getString("lastName"));
				System.out.println("Birth Date: " + answer.getString("dob"));
				System.out.println("Reward Points: " + answer.getInt("rewardPoints") + "\n");
			}
		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + query + "]");
			System.out.println("Problem getting result from ResultSet in function displayMemberById");
			e.printStackTrace();
		}
	}

	/** @formatter:off
	 * --------------------------------------------------------------------------------------------------
	 * Method: displayCurrenMonthProfit(String mm, String yyyy) displays the profit for the month mm in 
	 *         year yyyy. Should only be used for current month in the context of this program.
	 * 
	 * Parameters: String mm is current month, String yyyy is current year.
	 * 
	 * Purpose: To get this month's profit.
	 * 
	 * Returns: N/A.
	 * 
	 * ----------------------------------------------------------------------------------------------------
	 * @formatter:on
	 */
	public void displayCurrentMonthProfit(String mm, String yyyy) {

		ResultSet grossSales = null, numMembers = null, supplyCharge = null, laborCost = null;
		try {
			String query = "SELECT SUM(totalPrice) AS grossSales FROM Sale WHERE dateOf LIKE '" + mm + "/____/" + yyyy
					+ "'";
			Statement grossStmt = null;
			grossStmt = dbconn.createStatement();
			grossSales = grossStmt.executeQuery(query);

			query = "SELECT COUNT(*) AS numMembers FROM Member";
			Statement numMembersStmt = null;
			numMembersStmt = dbconn.createStatement();
			numMembers = numMembersStmt.executeQuery(query);

			query = "SELECT SUM(purchasePrice * amount) AS supplyCharge FROM productShipment WHERE incomingDate LIKE '"
					+ mm + "/____/" + yyyy + "'";
			Statement supplyChargeStmt = null;
			supplyChargeStmt = dbconn.createStatement();
			supplyCharge = supplyChargeStmt.executeQuery(query);

			query = "SELECT SUM(salary) AS laborCost FROM Employee";
			Statement laborCostStmt = null;
			laborCostStmt = dbconn.createStatement();
			laborCost = laborCostStmt.executeQuery(query);
		} catch (SQLException e) {

			System.err.println("*** SQLException:  " + "Could not fetch query results.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}

		try {
			float sales = 0, supply = 0, salaries = 0;
			int memberFees = 0;
			if (grossSales.next()) {
				sales = grossSales.getFloat("grossSales");
			}
			if (numMembers.next()) {
				memberFees = numMembers.getInt("numMembers") * 5;
			}
			if (supplyCharge.next()) {
				supply = supplyCharge.getFloat("supplyCharge");
			}
			if (laborCost.next()) {
				salaries = laborCost.getFloat("laborCost");
			}

			float currMonProfit = (sales + memberFees) - (supply + salaries);

			System.out.println("Profit for " + mm + "-" + yyyy + ": $" + currMonProfit + "\n");
		} catch (

		SQLException e) {
			System.out.println("Couldn't execute query: [queries]");
			System.out.println("Problem getting result from ResultSet in function displayMemberById");
			e.printStackTrace();
		}
	}

	public void displayMostProfitableProduct() {
		String query = "SELECT productId, (amountSold * (retailPrice * membershipDiscount - purchasePrice)) AS profit";
		query += " FROM (Product JOIN ProductShipment ON (Product.productId = ProductShipment.productId)) JOIN";
		query += " (SELECT productId, COUNT(*) AS amountSold FROM SubSale GROUP BY productId)";
		query += " ON (SubSale.productId = ProductShipment.productId) GROUP BY productId ORDER BY profit DESC";

		// create a statement
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet result = null;
		ResultSet result2 = null;

		try {
			stmt = dbconn.createStatement();
			result = stmt.executeQuery(query);

		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		try {
			String mostProfitableProductId = result.getString("productId");

			query = "SELECT name FROM Product WHERE productId = '" + mostProfitableProductId + "'";
			stmt2 = dbconn.createStatement();
			result2 = stmt2.executeQuery(query);

			System.out.println("Most Profitable Product: " + result.getString("name") + "\n");
		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + query + "]");
			System.out.println("Problem getting result from ResultSet in function displayMostProfitableProduct");
			e.printStackTrace();
		}
	}
}
