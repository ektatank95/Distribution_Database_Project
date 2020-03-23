import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GreedyMain {
    static int databasePort = Configuration.DATABASE_PORT;
    static String databaseAddress = Configuration.DATABASE_ADDRESS;
    static String databaseProvider = Configuration.DATABASE_PROVIDER;
    static String databaseName = Configuration.DATABASE_NAME;
    static String username = Configuration.USERNAME;
    static String password = Configuration.PASSWORD;
    static String createQueryTxtFile = Configuration.CREATE_QUERY_TXT_FILE;

    public static void main(String[] args) {

        Connection c = null;
        BufferedReader br = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            String connectionURL = "jdbc:" + databaseProvider + "://" + databaseAddress + ":" + databasePort + "/" + databaseName;
            c = DriverManager.getConnection(connectionURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        //create table query
        System.out.println("creating storage server as per requirment of DFS");

        int n = 0;
        try {
            br = new BufferedReader(new FileReader(createQueryTxtFile));
            String query;
            stmt = c.createStatement();
            while ((query = br.readLine()) != null) {
                System.out.println(query);
                int i = stmt.executeUpdate(query);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}