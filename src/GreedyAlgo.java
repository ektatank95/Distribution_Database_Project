import com.google.common.collect.Sets;

import java.util.*;

public class GreedyAlgo {
    public static List<DatabaseNode> findAllocationOnDatabaseForQuery(List<Query> allSortedqueryWithAttiributes, List<DatabaseNode> databaseNodeList) {

        List<DatabaseNode> databaseNodeListWithAllocation = new ArrayList<>();
        //algo line 6 to 9
        while (!allSortedqueryWithAttiributes.isEmpty()) {
            //  for (Query query : allSortedqueryWithAttiributes) {
            Query query = allSortedqueryWithAttiributes.get(allSortedqueryWithAttiributes.size()-1);
//            List<Query> temp = new ArrayList<>();
//            temp.add(query);
            SelectQueryAnalysis.viewQueryListInfo(allSortedqueryWithAttiributes);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeList);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            databaseNodeListWithAllocation=databaseNodeList;
            databaseNodeList = checkAllBackEndAreFull(databaseNodeList, query);
            //algo line 10 to 19
            if(databaseNodeList==null){
                System.out.println("Algorithm terminated");
                for(int j=0;j<allSortedqueryWithAttiributes.size();j++){
                    System.out.println(allSortedqueryWithAttiributes.get(j).getQueryId());
                }
                databaseNodeList=databaseNodeListWithAllocation;
                break;
            }
            DatabaseNode databaseNode = calcutateDifference(databaseNodeList, query);
            if (query.getRestWeight() > (databaseNode.getScaledLoad() - databaseNode.getCurrentLoad())) {
                query.setRestWeight(query.getRestWeight() - (databaseNode.getScaledLoad() - databaseNode.getCurrentLoad()));
                databaseNode.setCurrentLoad(databaseNode.getScaledLoad());
            } else {
                databaseNode.setCurrentLoad(databaseNode.getCurrentLoad() + query.getRestWeight());
                allSortedqueryWithAttiributes.remove(query);

            }
            SelectQueryAnalysis.sortQueryByweightAndTablesize(allSortedqueryWithAttiributes);
            // }
        }
        return databaseNodeList;
    }

    private static DatabaseNode calcutateDifference(List<DatabaseNode> databaseNodeList, Query query) {
        //Integer shows difference of database
        SortedMap<Integer, DatabaseNode> difference = new TreeMap<Integer, DatabaseNode>();
        Set<String> tableUsedByqueryAndItsUpdate = new HashSet<>();
        tableUsedByqueryAndItsUpdate.addAll(query.getTableUsed());
        double totalUpdateWeights = 0.0;
        for (Query updateQuery : query.getUpdates()) {
            tableUsedByqueryAndItsUpdate.addAll(updateQuery.getTableUsed());
            totalUpdateWeights = totalUpdateWeights + updateQuery.getWeight();
            System.out.println(updateQuery.getWeight());
        }
        for (DatabaseNode databaseNode : databaseNodeList) {
            // algo line 11-12
            if (databaseNode.getScaledLoad() == databaseNode.getCurrentLoad()) {
                difference.put(Integer.MAX_VALUE, databaseNode);
            } else if (databaseNode.getCurrentLoad() == 0) {
                difference.put(0, databaseNode);
            } else {
                Set<String> tableAvailableOnDatabaseNode = new HashSet<>();
                tableAvailableOnDatabaseNode.addAll(databaseNode.getFragmentList());
                Set<String> diff = Sets.difference(tableUsedByqueryAndItsUpdate, tableAvailableOnDatabaseNode);

                difference.put(diff.size(), databaseNode);
            }
        }
        DatabaseNode databaseNode = (DatabaseNode) difference.values().toArray()[0];
        System.out.println("difference size"+difference.size());
        for(int i=0;i<difference.size();i++){
            System.out.println(difference.get(i)+"----"+difference.values());
        }
        // algo line 18
        Set<String> updatedFragmentList = new HashSet<>();
        updatedFragmentList.addAll(tableUsedByqueryAndItsUpdate);
        if(databaseNode.getFragmentList()!=null){
            updatedFragmentList.addAll(databaseNode.getFragmentList());}
        databaseNode.setFragmentList(updatedFragmentList);
        //algo line 19
        Double currentLoad = databaseNode.getCurrentLoad() + totalUpdateWeights;
        System.out.println("total update-"+totalUpdateWeights);
        System.out.println("currentload"+currentLoad);
        //check assumption point no 4
        databaseNode.setCurrentLoad(currentLoad);

        //database node is full if equals and if greater it is overloaded so scale database node
        if (databaseNode.getCurrentLoad() >= databaseNode.getScaledLoad()) {
            System.out.println("from main method");
            calcuateNewScaleload(query, databaseNode);
        }
        Set<Query> queryList = new HashSet<>();
        if(databaseNode.getQueryList()!=null){
            queryList.addAll(databaseNode.getQueryList());
        }
        queryList.add(query);
        databaseNode.setQueryList(queryList);
        return databaseNode;
    }

    private static List<DatabaseNode> checkAllBackEndAreFull(List<DatabaseNode> databaseNodeList, Query query) {
        boolean allFull = true;
        int count=0, limit=databaseNodeList.size();
        for (DatabaseNode databaseNode : databaseNodeList) {
            if (databaseNode.getScaledLoad()==1) {
                count++;
            }
        }
        if(count==limit){
            return null;
        }
        for (DatabaseNode databaseNode : databaseNodeList) {
            if (databaseNode.getScaledLoad() != databaseNode.getCurrentLoad()) {
                allFull = false;
               break;
            }
        }
        System.out.println(count+"-"+limit);
        //allFull == true
        if (allFull == true) {
            for (DatabaseNode databaseNode : databaseNodeList) {
                calcuateNewScaleload(query, databaseNode);
            }
        }
        return databaseNodeList;
    }

    private static void calcuateNewScaleload(Query query, DatabaseNode databaseNode) {
        System.out.println("###############################-----Scaled load###############################");
//        double newScaleLoad = databaseNode.getCurrentLoad() + (databaseNode.getScaledLoad() * query.getWeight());
//        databaseNode.setScaledLoad(newScaleLoad);
        if(databaseNode.getScaledLoad()>=1){
            System.out.println("databaseNode with ID "+databaseNode.getServerID()+"is full , \n" +
                    "no more scaling can be done..Now on more query can be schedule");
        }else{
        double newScaleLoad = databaseNode.getCurrentLoad() + (databaseNode.getScaledLoad() * query.getWeight());
        if(newScaleLoad>1){
            newScaleLoad=1;
        }
        databaseNode.setScaledLoad(newScaleLoad);
        }
    }
}
