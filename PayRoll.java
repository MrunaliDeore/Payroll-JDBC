package JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class PayRoll {
	public static void connectToMysql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/payrollservice",
					"root",
					"Mrunali@17");

			System.out.println("Successfully connected to MySQL database test" + connection);
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL databse");
			e.getMessage();
		} catch (ClassNotFoundException e) {
			System.out.println("cannot find the driver in the class path !");
			e.getMessage();
		}

	}

	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/payrollservice",
				"root",
				"Mrunali@17");

		System.out.println("Successfully connected to MySQL database test" + connection);
		return connection;
	}

	public static void getAllDataUsingPreparedStatemnt() throws SQLException {
		System.out.println("\nShow table data");
		String select = "SELECT * FROM employeepayroll";
		System.out.println(select);
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(select);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");			
				String address = resultSet.getString("address");
				String salary = resultSet.getString("salary");

				System.out.println("reading data : "+ name + ", " + salary+ ", " + address);
			}
			connection.commit();
			System.out.println("Transaction is committed.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}

	public static void updateDataUsingPreparedStatement(String salary, String basicpay,int id) throws SQLException {
		System.out.println("\nUpdate Employye salary");
		String update = "UPDATE employeepayroll SET salary=?,basicpay=? where id=?";
		System.out.println(update);
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1,salary);
			statement.setString(2,basicpay);
			statement.setInt(3,id);
			int rowEffected = statement.executeUpdate();
			System.out.println(rowEffected + " records updated");
			connection.commit();
			System.out.println("Transaction is committed.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}
	
	public static void retrieveAllDataUsingPreparedStatemnt() throws SQLException {
		System.out.println("\nRetrieve data who is join on particular date");
		String select = "select * from employeepayroll where startdate between cast('2022-08-3' as date) and date(now());";
		System.out.println(select);
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(select);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");			
				String startdate = resultSet.getString("startdate");

				System.out.println("reading data : "+ name + ", " + startdate);
			}
			connection.commit();
			System.out.println("Transaction is committed.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}
	
	public static void countAllDataUsingPreparedStatemnt() throws SQLException {
		System.out.println("\nSum of data in table");
		String select = "select gender, count(salary) as total_count from employeepayroll where gender='F' group by gender;";
		System.out.println(select);
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(select);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
							
				String gender = resultSet.getString("gender");
				String salary = resultSet.getString("total_count");
				System.out.println("reading data : " + gender+","+salary);
			}
			connection.commit();
			System.out.println("Transaction is committed.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}

	public static void main(String[] args) throws SQLException {
		connectToMysql();
		//prpared stmt show table data
		getAllDataUsingPreparedStatemnt();
		//update employee salary
		updateDataUsingPreparedStatement("5000","200",1);
		//retrieve who has join on particular date
		retrieveAllDataUsingPreparedStatemnt();
		//count
		countAllDataUsingPreparedStatemnt();
		
	}
}