import java.sql.*;

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