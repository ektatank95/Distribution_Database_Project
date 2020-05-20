import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtlityClass {
    static int databasePort = Configuration.DATABASE_PORT;
    static String databaseAddress = Configuration.DATABASE_ADDRESS;
    static String databaseProvider = Configuration.DATABASE_PROVIDER;
    static String databaseName = Configuration.DATABASE_NAME;
    static String username = Configuration.USERNAME;
    static String password = Configuration.PASSWORD;

    public static Statement getConnection() throws SQLException {
        java.sql.Connection c = null;
        Statement stmt=null;
        try {
            Class.forName("org.postgresql.Driver");
            String connectionURL = "jdbc:" + databaseProvider + "://" + databaseAddress + ":" + databasePort + "/" + databaseName;
            c = DriverManager.getConnection(connectionURL, username, password);
            stmt  = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //create table query
        return stmt;
    }

    public static List<String> getInputFromFile(String fileName,String mode) {
        List<String> inputStringList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String query;
            while ((query = br.readLine()) != null) {
                inputStringList.add(query);
            }
        } catch (FileNotFoundException e) {
            if (mode.equals(Configuration.ANALYTICAL_MODE)){
                System.out.println("Analytical Query File not found.. analytical queries is not available...Algorithm cannot be run");
            }else if( mode.equals(Configuration.CREATE_MODE)){
                System.out.println("Table Creation File available...Can't proceed further without table creation");
                System.out.println("No Transactional query File is available...will not consider transaction query in allocation calcuation");
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStringList;
    }
}