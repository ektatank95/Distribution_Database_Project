import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GreedyMain {


    public static void main(String[] args) throws SQLException {
        Statement stmt = UtlityClass.getConnection();

        //TODO uncomment below line if table name is required
        //  CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);
        //to check select query data;
        System.out.println("Before Sorting List of Query Information............");
        List<Query> allqueryWithAttiributes = SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE,false);
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
        System.out.println(databaseNodeListWithFragment.size());
        DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeListWithFragment);

        System.out.println(" \nFinal Output of Algoithm");
        System.out.println( "Database Node\tQuery Assigned on Database Node\t" );

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s", "DATABASE NODE ", " QUERY ASSIGNED", "FRAGMENT ASSIGNED");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        for(DatabaseNode databaseNode: databaseNodeListWithFragment){
            System.out.format("%10s %30s %50s",
                    databaseNode.getServerID(), databaseNode.getQueryList(), databaseNode.getFragmentList());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");
        //fragmentAllocation.fragmentAllocation(allSortedqueryWithAttiributes,databaseNodeList);

    }
}