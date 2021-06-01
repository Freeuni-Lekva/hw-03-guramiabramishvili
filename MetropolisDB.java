
import java.sql.*;


public class MetropolisDB {

    public static String database = "cs108";
    private static String pass = "root";
    private static String user = "root";
    private static Connection connection;

        static {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String server = "jdbc:mysql://localhost:3306/" + database;
                connection = DriverManager.getConnection(server, "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static Connection getConnection() {
            return connection;
        }

        public static void close() {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}