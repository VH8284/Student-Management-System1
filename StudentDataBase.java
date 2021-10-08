package com.jdbc.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentDataBase {
	
	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		StudentDataBase studentDataBase = new StudentDataBase();
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String dbURL = "jdbc:mysql://localhost:3306/jdbcdb";
			String username = "root";
			String password = "root";
			
			connection = DriverManager.getConnection(dbURL, username, password);
			while(true)
			{
				System.out.println("Enter choice");
				System.out.println("1. Insert student record");
				System.out.println("2. Select student record");
				System.out.println("3. Update student record");
				System.out.println("4. Delete student record");
				System.out.println("5. Exit");
				
				int choice = scanner.nextInt();
			
				switch (choice) {
				case 1:
					studentDataBase.insertRecord();
					break;
				case 2:
					studentDataBase.selectRecord();
					break;
				case 3:
					studentDataBase.updateRecord();
					break;
				case 4:
					studentDataBase.deleteRecord();
					break;
				case 5:
					System.out.println("******Thank you visit Again!!!******");
					System.exit(0);
				}
			}
	}
	private void insertRecord() throws SQLException {
		
		String sql = "insert into student(name, grade_level, courses_enrolled, tuition_balance) values (?,?,?,?)";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		System.out.println("Enter name");
		scanner.nextLine();
		preparedStatement.setString(1, scanner.nextLine());
		System.out.println("Enter grade_level");
		preparedStatement.setInt(2, scanner.nextInt());
		System.out.println("Enter courses_enrolled");
		 scanner.nextLine();
		preparedStatement.setString(3, scanner.nextLine());
		System.out.println("Enter tuition_balance");
		preparedStatement.setInt(4, scanner.nextInt());
		int rows = preparedStatement.executeUpdate();
		
		if (rows > 0) {
			System.out.println("**********Record inserted successfully**********");
		}
	}
	public void selectRecord() throws SQLException {
		
		System.out.println("Enter roll number to find result");
		int number = scanner.nextInt();
		
		String sql = "select * from student where roll_number = "+number;
		
		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery(sql);
		
		if(result.next()) {
			int rollNumber = result.getInt("roll_number");
			String name = result.getString("name");
			int gradeLevel = result.getInt("grade_level");
			String coursesEnrolled = result.getString("courses_enrolled");
			int tuitionBalance = result.getInt("tuition_balance");
			
			System.out.println("Roll number is " +rollNumber);
			System.out.println("Name is " +name);
			System.out.println("Grade Level is " +gradeLevel);
			System.out.println("Courses Enrolled is " +coursesEnrolled);
			System.out.println("Tuition Balance is " +tuitionBalance);
			
		} else {
			System.out.println("*****No record found...*****");
		}
	}
	private void updateRecord() throws SQLException {
		
		System.out.println("Enter roll number to update record");
		int roll = scanner.nextInt();
		String sql = "select * from student where roll_number = "+roll;
		
		Statement statement  = connection.createStatement();
		
		ResultSet result = statement.executeQuery(sql);
		
		
		if(result.next()) {
			int rollNumber = result.getInt("roll_number");
			String name = result.getString("name");
			int gradeLevel = result.getInt("grade_level");
			String coursesEnrolled = result.getString("courses_enrolled");
			int tuitionBalance = result.getInt("tuition_balance");
			
			System.out.println("Roll number is " +rollNumber);
			System.out.println("Name is " +name);
			System.out.println("Grade Level is " +gradeLevel);
			System.out.println("Courses Enrolled is " +coursesEnrolled);
			System.out.println("Tuition Balance is " +tuitionBalance);
			
			System.out.println("What do you want to update?");
			System.out.println("1. Name");
			System.out.println("2. Grade Level");
			System.out.println("3. Courses Enrolled");
			System.out.println("4. Tuition Balance");
			
			int choice = scanner.nextInt();
			
			//update student set name = 'deep' where roll_number = 11002;
			String sqlQuery = "update student set ";
			switch (choice) {
			case 1:
				System.out.println("Enter new  name");
				scanner.nextLine();
				String newName = scanner.nextLine();
				sqlQuery = sqlQuery + "name = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, newName);
				
				int rows = preparedStatement.executeUpdate();
				if(rows > 0) {
					System.out.println("***Record updated successfully...***");
				}
				break;
			case 2:
				System.out.println("Enter new grade_level");
				int newgradeLevel = scanner.nextInt();
				sqlQuery = sqlQuery + "grade_level = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
				preparedStatement1.setInt(1, newgradeLevel);
				
				int rows1 = preparedStatement1.executeUpdate();
				if(rows1 > 0) {
					System.out.println("***Record updated successfully...***");
				}
				
				break;
			case 3:
				System.out.println("Enter new courses_enrolled");
				scanner.nextLine();
				String newcoursesEnrolled = scanner.nextLine();
				sqlQuery = sqlQuery + "courses_enrolled = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery);
				preparedStatement2.setString(1, newcoursesEnrolled);
				
				int rows2 = preparedStatement2.executeUpdate();
				if(rows2 > 0) {
					System.out.println("***Record updated successfully...***");
				}
				break;
			case 4:
				System.out.println("Enter new tuition_balance");
				int newtuitionBalance = scanner.nextInt();
				sqlQuery = sqlQuery + "tuition_balance = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery);
				preparedStatement3.setInt(1, newtuitionBalance);
		
				int rows3 = preparedStatement3.executeUpdate();
				if(rows3 > 0) {
					System.out.println("***Record updated successfully...***");
				}
				break;
			default:
				break;
			}
		} else {
			System.out.println("****Records not found...****");
		}
	}
	public void deleteRecord() throws SQLException{
		System.out.println("Enter roll number to delete.");
		int rollNumber = scanner.nextInt();
		String sql = "delete from student where roll_number = "+rollNumber;
		//Statement statement = connection.createStatement();
		PreparedStatement ps = connection.prepareStatement(sql);
		int rows = ps.executeUpdate(sql);
		if(rows >0 ) {
			System.out.println("*********Record is deleted successfully...*********");
		}
	}
}