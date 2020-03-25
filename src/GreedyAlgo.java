import java.util.*;
import com.google.common.collect.Sets;
public class GreedyAlgo {
    public static List<DatabaseNode> findAllocationOnDatabaseForQuery(List<Query> allSortedqueryWithAttiributes, List<DatabaseNode> databaseNodeList) {

        List<DatabaseNode> databaseNodeListWithAllocation = new ArrayList<>();
        //algo line 6 to 9
        for(Query query:allSortedqueryWithAttiributes){
        databaseNodeList= checkAllBackEndAreFull(databaseNodeList,query);
        //algo line 10 to 17
        DatabaseNode databaseNode= calcutateDifference(databaseNodeList,query);
        double currentLoad=databaseNode.getCurrentLoad();
        double scaleLoad=databaseNode.getScaledLoad();
        double restWeight=query.getRestWeight();
        if(query.getRestWeight()>(databaseNode.getScaledLoad()-databaseNode.getCurrentLoad())){
         query.setRestWeight(query.getRestWeight()-(databaseNode.getScaledLoad()-databaseNode.getCurrentLoad()));
         databaseNode.setCurrentLoad(databaseNode.getScaledLoad());
        }else{
            databaseNode.setCurrentLoad(databaseNode.getScaledLoad()+query.getRestWeight());

        }

        }
        return databaseNodeListWithAllocation;
    }

    private static DatabaseNode calcutateDifference(List<DatabaseNode> databaseNodeList, Query query) {
        SortedMap<Integer, DatabaseNode> difference = new TreeMap<Integer, DatabaseNode>();
        Set<String> tableUsedByqueryAndItsUpdate= new HashSet<>();
        tableUsedByqueryAndItsUpdate.addAll(query.getTableUsed());
        double totalUpdateWeights=0.0;
        for(Query updateQuery:query.getUpdates()){
            tableUsedByqueryAndItsUpdate.addAll(updateQuery.getTableUsed());
            totalUpdateWeights=totalUpdateWeights+updateQuery.getWeight();
        }
        for(DatabaseNode databaseNode:databaseNodeList){
            // algo line 11-12
            if(databaseNode.getScaledLoad()==databaseNode.getCurrentLoad()){
                difference.put(Integer.MAX_VALUE,databaseNode);
            }else if(databaseNode.getCurrentLoad()==0){
                difference.put(0,databaseNode);
            }else{
                Set<String> tableAvailableOnDatabaseNode= new HashSet<>();
                tableAvailableOnDatabaseNode.addAll(databaseNode.getFragmentList());
                Set<String> diff = Sets.difference(tableUsedByqueryAndItsUpdate, tableAvailableOnDatabaseNode);
                difference.put(diff.size(),databaseNode);
            }
        }
        DatabaseNode databaseNode=(DatabaseNode) difference.values().toArray()[0];
        // algo line 18
        List<String> updatedFragmentList=new ArrayList<>();
        updatedFragmentList.addAll(tableUsedByqueryAndItsUpdate);
        updatedFragmentList.addAll(databaseNode.getFragmentList());
        databaseNode.setFragmentList(updatedFragmentList);
        //algo line 19
        Double currentLoad=databaseNode.getCurrentLoad()+totalUpdateWeights;
        //check assumption point no 4
        databaseNode.setCurrentLoad(currentLoad);

        //database node is full if equals and if greater it is overloaded so scale database node
        if(databaseNode.getCurrentLoad()>=databaseNode.getScaledLoad()){
            calcuateNewScaleload(query, databaseNode);
        }
        return databaseNode;
    }

    private static List<DatabaseNode> checkAllBackEndAreFull(List<DatabaseNode> databaseNodeList, Query query) {
        boolean allFull=true;
        for(DatabaseNode databaseNode:databaseNodeList){
            if (databaseNode.getScaledLoad()!=databaseNode.getCurrentLoad()){
                allFull=false;
            }
        }
        if(allFull==true) {
            for (DatabaseNode databaseNode : databaseNodeList) {
                calcuateNewScaleload(query, databaseNode);
            }
        }
        return databaseNodeList;
    }

    private static void calcuateNewScaleload(Query query, DatabaseNode databaseNode) {
        double newScaleLoad=databaseNode.getCurrentLoad()+(databaseNode.getScaledLoad()*query.getWeight());
        databaseNode.setScaledLoad(newScaleLoad);
    }
}
