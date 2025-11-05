package org.example;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        
        getAllStudents();
        addStudent("Ahmed", "Omer", "him@gmail.com", "2025-11-07");
        getAllStudents();
        updateStudentEmail(4, "himothy@gmail.com");
        getAllStudents();
        deleteStudent(4);
        getAllStudents();

    }

    static Connection connectToDatabase() {

        // Needed definitions to connect to the database
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "SalihOmer12";

        // try is needed to catch exceptions
        try {
            // Creating a connection to the database
            Connection connection = DriverManager.getConnection(url, user, password);

            // Checking if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the database!");
                return connection;
            }else {
                System.out.println("Failed to make connection!");
            }

        // Catching exceptions
        } catch (Exception e) {
            System.out.println(e);
        }

        // We only reach here if we failed to make a connection, in which case we return null
        return null;
    }
    static void getAllStudents() {

        // establishing a connection using connectToDatabase method
        Connection connection = connectToDatabase();

        // try is needed to catch exceptions
        try {

            // creating a query to get all students
            Statement statement = connection.createStatement();

            // executing the query
            statement.executeQuery("select * from students");

            // getting the result via a ResultSet object
            ResultSet resultSet = statement.getResultSet();

            // iterating through the results and printing them
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("student_id") + ", ");
                System.out.print(resultSet.getString("first_name") + ", ");
                System.out.print(resultSet.getString("last_name") + ", ");
                System.out.print(resultSet.getString("email") + ", ");
                System.out.println(resultSet.getString("enrollment_date"));

            }

        // Catching exceptions
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static void addStudent(String first_name, String last_name, String email, String enrollment_date){

        // establishing a connection using connectToDatabase method
        Connection connection = connectToDatabase();

        // try is needed to catch exceptions
        try {

            // creating a query to add a new student
            String insertsql = "insert into students (first_name, last_name, email, enrollment_date) values (?, ?, ?, ?)";

            // injecting values into the query
            try(PreparedStatement pstmt= connection.prepareStatement(insertsql)) {
                pstmt.setString(1, first_name);
                pstmt.setString(2, last_name);
                pstmt.setString(3, email);
                pstmt.setDate(4, Date.valueOf(enrollment_date));

                // executing the query
                pstmt.executeUpdate();

            // catching exceptions
            }catch (Exception e){
                System.out.println(e);
            }

        // Catching exceptions
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    static void updateStudentEmail(int student_id, String new_email){

        // establishing a connection using connectToDatabase method
        Connection connection = connectToDatabase();

        // try is needed to catch exceptions
        try {

            // creating the query
            String insertsql = "UPDATE students SET email = ? WHERE student_id = ?" ;

            // injecting values into the query
            try(PreparedStatement pstmt= connection.prepareStatement(insertsql)) {
                pstmt.setString(1, new_email);
                pstmt.setInt(2, student_id);

                // executing the query
                pstmt.executeUpdate();

            // catching exceptions
            }catch (Exception e){
                System.out.println(e);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static void deleteStudent(int student_id){

        // establishing a connection using connectToDatabase method
        Connection connection = connectToDatabase();

        // try is needed to catch exceptions
        try {

            // creating the query
            String insertsql = "DELETE FROM students WHERE student_id = ?";

            // injecting values into the query
            try(PreparedStatement pstmt= connection.prepareStatement(insertsql)) {
                pstmt.setInt(1, student_id);

                // executing the query
                pstmt.executeUpdate();

            // catching exceptions
            }catch (Exception e){
                System.out.println(e);
            }

        // catching exceptions
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

