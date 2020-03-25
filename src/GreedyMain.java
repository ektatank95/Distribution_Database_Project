import java.sql.Statement;
import java.util.List;

public class GreedyMain {


    public static void main(String[] args) {
        Statement stmt = UtlityClass.getConnection();

        //TODO uncomment below line if table name is required
      //  CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);

       // List<Query> allqueryAttiributes = SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE);
        //to check select query data;
        System.out.println("Before Sorting............");
        SelectQueryAnalysis.viewQueryListInfo(SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE));
        //SelectQueryAnalysis.sortQueryByweightAndTablesize(SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE));
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("After sorting..........");
        SelectQueryAnalysis.viewQueryListInfo(SelectQueryAnalysis.sortQueryByweightAndTablesize(SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE)));


    }
}