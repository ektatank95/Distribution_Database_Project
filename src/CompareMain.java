import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CompareMain {
    public static void main(String[] args) throws SQLException {
        Statement stmt = UtlityClass.getConnection();

        //TODO uncomment below line if table name is required
        // CreateTableRequired.createTable(Configuration.CREATE_TABLE_QUERY_TXT_FILE, stmt);

        //to check select query data;

        //List<Query> allqueryWithAttiributes = SelectQueryAnalysis.getAllqueryAttiributes(Configuration.SELECT_QUERY_TXT_FILE,false);
        List<Query> allqueryWithAttiributes=new ArrayList<>();
        Set<String> tableRequired=new HashSet<>();
        Set<Query> empty=new HashSet<>();
        tableRequired.add("1");
        tableRequired.add("2");
        tableRequired.add("3");
        tableRequired.add("4");
        Query q1=new Query("Q1","Query1",5.0,tableRequired,20,empty,0.10,0.10,false,0.0);
        Set<String> tableRequired1=new HashSet<>();
        tableRequired1.add("5");
        tableRequired1.add("6");
        tableRequired1.add("3");
        tableRequired1.add("4");

        Query q2=new Query("Q2","Query2",50.0,tableRequired1,3,empty,0.15,0.15,false,0.0);

        Set<String> tableRequired2=new HashSet<>();
        tableRequired2.add("7");
        tableRequired2.add("8");
        tableRequired2.add("9");

        Query q3=new Query("Q3","Query3",10.0,tableRequired2,25,empty,0.25,0.25,false,0.0);
        Set<String> tableRequired3=new HashSet<>();
        tableRequired3.add("8");
        tableRequired3.add("9");
        tableRequired3.add("10");

        Query q4=new Query("Q4","Query4",20.0,tableRequired3,10,empty,0.20,0.20,false,0.0);
        Set<String> tableRequired4=new HashSet<>();
        tableRequired4.add("1");

        Query q5=new Query("Q5","Query5",6.0,tableRequired4,50,empty,0.30,0.30,false,0.0);


        allqueryWithAttiributes.add(q1);
        allqueryWithAttiributes.add(q2);
        allqueryWithAttiributes.add(q3);
        allqueryWithAttiributes.add(q4);
        allqueryWithAttiributes.add(q5);



        System.out.println("Before sorting List of Query Information..........");
        SelectQueryAnalysis.viewQueryListInfo(allqueryWithAttiributes);


        List<Query> allSortedqueryWithAttiributes=SelectQueryAnalysis.sortQueryByweightAndTablesize(allqueryWithAttiributes);

        System.out.println("After sorting List of Query Information..........");
        SelectQueryAnalysis.viewQueryListInfo(allSortedqueryWithAttiributes);

        List<DatabaseNode> databaseNodeList=DatabaseNodeAnalysis.getAllDatabaseNodeInfo(Configuration.COMPARE_DATABASE_INFO);
        List<DatabaseNode> databaseNodeListWithFragment=GreedyAlgo.findAllocationOnDatabaseForQuery(allSortedqueryWithAttiributes,databaseNodeList);


//        System.out.println("Database Node Information before Allocation Starts..........");
//        DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeList);




        System.out.println(" \n----------------------------------------------Final Output of Algoithm--------------------------------------------------------------------------");
        Set<String> remaining=new HashSet<>();

        System.out.printf("%10s %30s %20s", "DATABASE NODE ", " QUERY ASSIGNED", "FRAGMENT ASSIGNED");
        System.out.println();
        for(DatabaseNode databaseNode: databaseNodeListWithFragment){
            Set<Query> queryList=databaseNode.getQueryList();
            List<String> qList=new ArrayList<>();
            if(queryList!=null){
                Iterator<Query> itr = queryList.iterator();


                while(itr.hasNext()){
                    String query=itr.next().getQueryId();
                    qList.add(query);
                    remaining.add(query);
                    // System.out.println(itr.next());
                }
            }

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

//        System.out.println("lasttttt sorting List of Query Information..........");
//        SelectQueryAnalysis.viewQueryListInfo(allqueryWithAttiributes);

    }
}
