import java.sql.*;
import java.util.*;


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
        } catch (SQLException e) {
        System.out.println("Error executing the query: " + e.getMessage());
    }
    }

    private static void showAllBranches(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Branch";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             boolean hasBranches = false;
            while (rs.next()) {
                int id = rs.getInt("BranchID");
                String phone = rs.getString("BPhone");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zip = rs.getString("Zip");
                System.out.printf("Branch ID: %d, Phone: %s, City: %s, State: %s, Zip: %s%n", id, phone, city, state, zip);
               
                hasBranches = true;
            }
             if (!hasBranches) {
            System.out.println("No branches found.");
        }
        }  catch (SQLException e) {
         System.out.println("Error connecting to the database: ");
         e.printStackTrace();
    } 
    }
    
    
}



