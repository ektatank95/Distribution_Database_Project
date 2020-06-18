import javax.swing.*;
import java.net.StandardSocketOptions;
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

        System.out.println("Before sorting List of Query Information..........");
        SelectQueryAnalysis.viewQueryListInfo(allqueryWithAttiributes);


        List<Query> allSortedqueryWithAttiributes=SelectQueryAnalysis.sortQueryByweightAndTablesize(allqueryWithAttiributes);

        System.out.println("After sorting List of Query Information..........");
        SelectQueryAnalysis.viewQueryListInfo(allSortedqueryWithAttiributes);


        List<DatabaseNode> databaseNodeList=DatabaseNodeAnalysis.getAllDatabaseNodeInfo(Configuration.DATABASE_INFO);
        List<DatabaseNode> databaseNodeListWithFragment=GreedyAlgo.findAllocationOnDatabaseForQuery(allSortedqueryWithAttiributes,databaseNodeList);


//        System.out.println("Database Node Information before Allocation Starts..........");
//        DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeList);



        Set<String> tableused=new HashSet<>();
        System.out.println(" \n----------------------------------------------Final Output of Algoithm--------------------------------------------------------------------------");
        Set<String> remaining=new HashSet<>();

         System.out.printf("%10s %30s %20s", "DATABASE NODE ", " QUERY ASSIGNED", "FRAGMENT ASSIGNED");
        System.out.println();
        for(DatabaseNode databaseNode: databaseNodeListWithFragment){
            Set<Query> queryList=databaseNode.getQueryList();
            //List<String> qList=new ArrayList<>();
            Set<String> qList= new HashSet<>();
            if(queryList!=null){
                Iterator<Query> itr = queryList.iterator();


                while(itr.hasNext()){
                    String query=itr.next().getQueryId();
                    qList.add(query);
                    remaining.add(query);
                    // System.out.println(itr.next());
                }
            }else{
                continue;
            }
            tableused.addAll(databaseNode.getFragmentList());
            System.out.format("%10s %30s %50s",
                    databaseNode.getServerID(), qList, databaseNode.getFragmentList());
            System.out.println();
        }
       List<String> allTables=  SelectQueryAnalysis.findAllTableOfDatabase();
        System.out.println("Remaining Tables");
        for(int i=0;i<allTables.size();i++){
            if(tableused.contains(allTables.get(i))){

            }else{
                System.out.println(allTables.get(i));
            }
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

//        System.out.println("lasttttt sorting List of Query Information..........");
//        SelectQueryAnalysis.viewQueryListInfo(allqueryWithAttiributes);

    }
}



