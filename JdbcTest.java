import java.sql.*;

public class JdbcTest {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";

		try {
			// Get a connection to database
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			System.out.println("Database connection successful!\n");

			// Create a statement
			myStmt = myConn.createStatement();

			// Execute SQL query
			myRs = myStmt.executeQuery("select * from employees");

			// Insert a new employee
			System.out.println("Inserting a new employee to database\n");

			myStmt.executeUpdate("insert into employee " + "(last_name, first_name, email, department, salary) "
					+ "values " + "('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 330000.00)");

			// Update an employee
			System.out.println("\nEXECUTING THE UPDATE FOR: John Doe\n");

			myStmt.executeUpdate("update employees " + "set email='john.doe@luv2code.com' "
					+ "where last_name='Doe' and first_name='John'");

			// Delete an employee
			System.out.println("\nDELETING THE EMPLOYEE: John Doe\n");

			myStmt.executeUpdate("delete from employees where last_name='Doe' and first_name='John'");

			// Prepared Statement
			PreparedStatement pStmt = myConn
					.prepareStatement("SELECT * FROM employees " + "WHERE salary > ? AND department = ?");

			// Set parameters
			pStmt.setDouble(1, 80000);
			pStmt.setString(2, "Legal");
			
			// Execute query
			myRs = pStmt.executeQuery();

			// Reuse the prepared statement
			System.out.println("\nReusing the prepared statement:");

			pStmt.setDouble(1, 25000);
			pStmt.setString(2, "HR");

			myRs = pStmt.executeQuery();

			// Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));

			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
