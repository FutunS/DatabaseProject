import java.sql.*;
import java.util.*;
public class BranchManagementProgram {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();

            boolean exit = false;
            while (!exit) {
                displayMenu();
                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        insertRecord(statement);
                        break;
                    case 2:
                        displayRecords(statement);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load MariaDB JDBC driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection error.");
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Table Branch:");
        System.out.println("1) Insert new record");
        System.out.println("2) Display all records");
        System.out.println("3) Exit");
        System.out.print("Choose an operation: ");
    }

    private static int getUserChoice() {
        int choice = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return choice;
    }

    private static void insertRecord(Statement statement) throws SQLException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Branch ID: ");
            int branchID = scanner.nextInt();
            System.out.print("Branch Phone Number: ");
            String bPhone = scanner.next();
            System.out.print("Branch City: ");
            String city = scanner.next();
            System.out.print("Branch State: ");
            String state = scanner.next();
            System.out.print("Branch Zip Code: ");
            String zip = scanner.next();

            String insertQuery = "INSERT INTO Branch (BranchID, BPhone, City, State, Zip) VALUES (" +
                    branchID + ", '" + bPhone + "', '" + city + "', '" + state + "', '" + zip + "')";

            statement.executeUpdate(insertQuery);
            System.out.println("Record inserted successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Failed to insert record.");
        }
    }

    private static void displayRecords(Statement statement) throws SQLException {
        String selectQuery = "SELECT * FROM Branch";
        ResultSet resultSet = statement.executeQuery(selectQuery);

        System.out.println("BranchID\tBPhone\t\tCity\t\tState\t\tZip");
        while (resultSet.next()) {
            int branchID = resultSet.getInt("BranchID");
            String bPhone = resultSet.getString("BPhone");
            String city = resultSet.getString("City");
            String state = resultSet.getString("State");
            String zip = resultSet.getString("Zip");

            System.out.println(branchID + "\t\t" + bPhone + "\t" + city + "\t\t" + state + "\t\t" + zip);
        }

        resultSet.close();
    }
}


/*
  import java.sql.*;
import java.util.Scanner;

public class VelocityMotorsDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/Velocity_Motors";
    private static final String USER = "yourUsername"; // Replace with your database username
    private static final String PASSWORD = "yourPassword"; // Replace with your database password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database.");

            while (true) {
                System.out.println("\n1. Add new branch");
                System.out.println("2. Show all branches");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addNewBranch(conn, scanner);
                        break;
                    case 2:
                        showAllBranches(conn);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void addNewBranch(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Branch ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Branch Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        System.out.print("Enter Zip Code: ");
        String zip = scanner.nextLine();

        String sql = "INSERT INTO Branch (BranchID, BPhone, City, State, Zip) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, phone);
            pstmt.setString(3, city);
            pstmt.setString(4, state);
            pstmt.setString(5, zip);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Branch added successfully.");
            } else {
                System.out.println("A problem occurred. Branch not added.");
            }
        }
    }

    private static void showAllBranches(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Branch";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("BranchID");
                String phone = rs.getString("BPhone");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zip = rs.getString("Zip");
                System.out.printf("Branch ID: %d, Phone: %s, City: %s, State: %s, Zip: %s%n", id, phone, city, state, zip);
            }
        }
    }
}

 

code provided in tutorial 8

 import java.sql.*;
public class MariaJdbcConn {
public static void main(String[] args) {
// TODO Auto-generated method stub
Connection connection = null;
String url = "jdbc:mariadb://localhost:3306/comp";
String user = "root";
String pwd = "";
try {
connection = DriverManager.getConnection(url, user, pwd);
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
System.out.println("Successfully connected to database");
try {
Statement stmt = connection.createStatement();
int Empno = 123;
String Ename = "Ahmad";
int Salary = 10000;
String Entry = "2021-10-10";
int Dno = 3;
String sql = "INSERT INTO Emp VALUES("
+Empno+",'"+Ename+"',"+Salary+",'"+Entry+"',"+Dno+")";
stmt.executeUpdate(sql);
ResultSet rs = null;
rs = stmt.executeQuery("SELECT * FROM Emp");
while(rs.next()) {
System.out.print(rs.getInt("Empno") + "\t");
System.out.print(rs.getString("Ename") + "\t");
System.out.print(rs.getInt("Salary") + "\t");
System.out.print(rs.getDate("Entry") + "\t");
System.out.print(rs.getInt("Dno") + "\t");
System.out.println();
}
stmt.close();
connection.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}


  */