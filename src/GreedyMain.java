import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class GreedyMain {


    public static void main(String[] args) throws SQLException {
        Statement stmt = UtlityClass.getConnection();

        //TODO uncomment below line if table name is required
        // CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);

        //to check select query data;

        List<Query> allqueryWithAttiributes = SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE,false);
        List<Query> allSortedqueryWithAttiributes=SelectQueryAnalysis.sortQueryByweightAndTablesize(allqueryWithAttiributes);
        List<DatabaseNode> databaseNodeList=DatabaseNodeAnalysis.getAllDatabaseNodeInfo(Configuration.DATABASE_INFO);
        List<DatabaseNode> databaseNodeListWithFragment=GreedyAlgo.findAllocationOnDatabaseForQuery(allSortedqueryWithAttiributes,databaseNodeList);

        System.out.println(" \n----------------------------------------------Final Output of Algoithm--------------------------------------------------------------------------");
        Set<String> remaining=new HashSet<>();

         System.out.printf("%10s %30s %20s", "DATABASE NODE ", " QUERY ASSIGNED", "FRAGMENT ASSIGNED");
        System.out.println();
        for(DatabaseNode databaseNode: databaseNodeListWithFragment){
            Set<Query> queryList=databaseNode.getQueryList();

            Iterator<Query> itr = queryList.iterator();

            List<String> qList=new ArrayList<>();
            while(itr.hasNext()){
                String query=itr.next().getQueryId();
                qList.add(query);
                remaining.add(query);
               // System.out.println(itr.next());
            }

            List<String> query;
            System.out.format("%10s %30s %50s",
                    databaseNode.getServerID(), qList, databaseNode.getFragmentList());
            System.out.println();
        }

//        System.out.println(" \n--------------------------------------------------------------------------------------------------------------------------------------------------");
//
//
//        if( allqueryWithAttiributes.size()!=remaining.size()) {
//
//            System.out.println("\nQuery that are not able to schedule because database node are full \n ");
//            for (int i = 0; i < allqueryWithAttiributes.size(); i++) {
//                if (!remaining.contains(allqueryWithAttiributes.get(i).getQueryId())) {
//                    System.out.println(allqueryWithAttiributes.get(i).getQueryId());
//                }
//            }
//        }
//
//        System.out.println(" \n--------------------------------------------------------------------------------------------------------------------------------------------------");

    }
}



