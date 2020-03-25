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

        }
        return databaseNodeListWithAllocation;
    }

    private static DatabaseNode calcutateDifference(List<DatabaseNode> databaseNodeList, Query query) {
        SortedMap<Integer, DatabaseNode> difference = new TreeMap<Integer, DatabaseNode>();
        for(DatabaseNode databaseNode:databaseNodeList){
            // algo line 11-12
            if(databaseNode.getScaledLoad()==databaseNode.getCurrentLoad()){
                difference.put(Integer.MAX_VALUE,databaseNode);
            }else if(databaseNode.getCurrentLoad()==0){
                difference.put(0,databaseNode);
            }else{
                Set<String> tableUsedByqueryAndItsUpdate= new HashSet<>();
                tableUsedByqueryAndItsUpdate.addAll(query.getTableUsed());
                for(Query updateQuery:query.getUpdates()){
                    tableUsedByqueryAndItsUpdate.addAll(updateQuery.getTableUsed());
                }
                Set<String> tableAvailableOnDatabaseNode= new HashSet<>();
                tableAvailableOnDatabaseNode.addAll(databaseNode.getFragmentList());
                Set<String> diff = Sets.difference(tableUsedByqueryAndItsUpdate, tableAvailableOnDatabaseNode);
                difference.put(diff.size(),databaseNode);
            }

        }
        DatabaseNode databaseNode=(DatabaseNode) difference.values().toArray()[0];
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
                double newScaleLoad=databaseNode.getCurrentLoad()+(databaseNode.getScaledLoad()*query.getWeight());
                databaseNode.setScaledLoad(newScaleLoad);
            }
        }
        return databaseNodeList;
    }
}
