import java.sql.Statement;
import java.util.List;

public class GreedyMain {


    public static void main(String[] args) {
        Statement stmt = UtlityClass.getConnection();

        //TODO uncomment below line if table name is required
        //  CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);

        //to check select query data;
        System.out.println("Before Sorting List of Query Information............");
        List<Query> allqueryWithAttiributes = SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE);
        SelectQueryAnalysis.viewQueryListInfo(allqueryWithAttiributes);

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("After sorting List of Query Information..........");
        List<Query> allSortedqueryWithAttiributes = SelectQueryAnalysis.sortQueryByweightAndTablesize(allqueryWithAttiributes);
        SelectQueryAnalysis.viewQueryListInfo(allSortedqueryWithAttiributes);


        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Database Node Information before Allocation Starts..........");
        List<DatabaseNode> databaseNodeList = DatabaseNodeAnalysis.getAllDatabaseNodeInfo(Configuration.DATABASE_INFO);
        DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeList);


        System.out.println("Database Node Information after Allocation done..........");
        //call  on actual algo
        List<DatabaseNode> databaseNodeListWithFragment = GreedyAlgo.findAllocationOnDatabaseForQuery(allSortedqueryWithAttiributes, databaseNodeList);
        DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeListWithFragment);

        //fragmentAllocation.fragmentAllocation(allSortedqueryWithAttiributes,databaseNodeList);

    }
}