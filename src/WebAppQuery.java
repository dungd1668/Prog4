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
