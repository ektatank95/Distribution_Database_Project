import java.sql.Statement;

public class GreedyMain {


    public static void main(String[] args) {
        Statement stmt = UtlityClass.getConnection();

        // uncomment below line if table name is required
      //  CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);

        //     SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE);

        //to check select query data;
        SelectQueryAnalysis.viewQueryListInfo(SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE));
    }
}