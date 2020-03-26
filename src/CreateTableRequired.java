import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CreateTableRequired {
    public static void createTable(String createTableQueryFile,Statement stmt) {
        try {
            List<String> inputFromFile = UtlityClass.getInputFromFile(createTableQueryFile);
            for (int i = 0; i < inputFromFile.size(); i++) {
                stmt.executeUpdate(inputFromFile.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

