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
		String query = "SELECT firstName, lastName, dob, rewardPoints FROM Member WHERE phoneNumber = " + phoneNum;
		ResultSet answer = executeQuery(query);

		try {
			if (!answer.next()) {
				System.out.println("There was no user found with Phone Number: " + phoneNum + "\n");
			} else {
				answer.beforeFirst();

				System.out.println("Member: " + answer.getString("firstName") + " " + answer.getString("lastName"));
				System.out.println("Birth Date: " + answer.getString("dob"));
				System.out.println("Reward Points: " + answer.getInt("rewardPoints") + "\n");
			}
		} catch (SQLException e) {
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
		String query = "SELECT firstName, lastName, dob, rewardPoints FROM Member WHERE memberID = " + memberId;
		ResultSet answer = executeQuery(query);

		try {
			if (!answer.next()) {
				System.out.println("There was no user found with Member Id: " + memberId + "\n");
			} else {
				answer.beforeFirst();

				System.out.println("Member: " + answer.getString("firstName") + " " + answer.getString("lastName"));
				System.out.println("Birth Date: " + answer.getString("dob"));
				System.out.println("Reward Points: " + answer.getInt("rewardPoints") + "\n");
			}
		} catch (SQLException e) {
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
		String query = "SELECT SUM(totalPrice) AS grossSales FROM Sale WHERE date LIKE '" + mm + "____" + yyyy + "'";
		ResultSet grossSales = executeQuery(query);

		query = "SELECT COUNT(*) AS numMembers FROM Member";
		ResultSet numMembers = executeQuery(query);

		query = "SELECT SUM(purchasePrice * amount) AS supplyCharge FROM productShipment WHERE incomingDate LIKE '" + mm
				+ "____" + yyyy + "'";
		ResultSet supplyCharge = executeQuery(query);

		query = "SELECT SUM(salary) AS laborCost FROM Employee";
		ResultSet laborCost = executeQuery(query);

		try {
			float sales = grossSales.getFloat("grossSales");
			int memberFees = numMembers.getInt("numMembers") * 5;
			float supply = supplyCharge.getFloat("supplyCharge");
			float salaries = laborCost.getFloat("laborCost");

			float currMonProfit = (sales + memberFees) - (supply + salaries);

			System.out.println("Profit for " + mm + "-" + yyyy + ": $" + currMonProfit + "\n");
		} catch (SQLException e) {
			System.out.println("Problem getting result from ResultSet in function displayMemberById");
			e.printStackTrace();
		}
	}

	public void displayMostProfitableProduct() {
		String query = "SELECT productId, (amountSold * (retailPrice * membershipDiscount - purchasePrice)) AS profit";
		query += " FROM (Product JOIN ProductShipment ON (Product.productId = ProductShipment.productId)) JOIN";
		query += " (SELECT productId, COUNT(*) AS amountSold FROM SubSale GROUP BY productId)";
		query += " ON (SubSale.productId = ProductShipment.productId) GROUP BY productId ORDER BY profit DESC";

		ResultSet result = executeQuery(query);

		try {
			String mostProfitableProductId = result.getString("productId");

			query = "SELECT name FROM Product WHERE productId = '" + mostProfitableProductId + "'";
			result = executeQuery(query);

			System.out.println("Most Profitable Product: " + result.getString("name") + "\n");
		} catch (SQLException e) {
			System.out.println("Problem getting result from ResultSet in function displayMostProfitableProduct");
			e.printStackTrace();
		}
	}

	private ResultSet executeQuery(String query) {
		// create a statement
		Statement stmt = null;
		ResultSet answer = null;
		try {
			stmt = dbconn.createStatement();
		} catch (SQLException e1) {
			System.out.println("Couldn't create statement.");
			e1.printStackTrace();
		}

		// execute the insert query
		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Couldn't execute query: [" + query + "]");
			e.printStackTrace();
		}

		return answer;
	}
}
