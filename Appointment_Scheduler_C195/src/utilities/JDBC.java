package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * The JDBC class provides utilities for managing a JDBC connection to a MySQL database.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 *
 */
public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * Opens a connection to the MySQL database using the specified JDBC URL, username, and password.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the established database connection.
     *
     * @return The established database connection.
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the database connection, if it is open.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            //do nothing
        }
    }
}
