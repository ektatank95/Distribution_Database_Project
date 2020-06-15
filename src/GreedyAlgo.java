import com.google.common.collect.Sets;

import java.util.*;

public class GreedyAlgo {
    public static List<DatabaseNode> findAllocationOnDatabaseForQuery(List<Query> allSortedqueryWithAttiributes, List<DatabaseNode> databaseNodeList) {

        List<DatabaseNode> databaseNodeListWithAllocation = new ArrayList<>();
        //algo line 6 to 9
        while (!allSortedqueryWithAttiributes.isEmpty()) {
            //  for (Query query : allSortedqueryWithAttiributes) {
            Query query = allSortedqueryWithAttiributes.get(allSortedqueryWithAttiributes.size() - 1);
//            SelectQueryAnalysis.viewQueryListInfo(allSortedqueryWithAttiributes);
//            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
//            DatabaseNodeAnalysis.viewDatabaseListInfo(databaseNodeList);
//            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            List<DatabaseNode> databaseNodes = databaseNodeList;
            databaseNodeList = checkAllBackEndAreFull(databaseNodeList, query);
            if (databaseNodeList == null) {

                System.out.println("Algorithm terminated.....Becuase all database Node are full");
                databaseNodeList = databaseNodes;
                break;
            }

            //algo line 10 to 19
            DatabaseNode databaseNode = calcutateDifference(databaseNodeList, query);
            if (databaseNode==null){
                allSortedqueryWithAttiributes.remove(query);
                continue;
            }
            if (query.getRestWeight() > (databaseNode.getScaledLoad() - databaseNode.getCurrentLoad())) {
                query.setRestWeight(query.getRestWeight() - (databaseNode.getScaledLoad() - databaseNode.getCurrentLoad()));
                databaseNode.setCurrentLoad(databaseNode.getScaledLoad());
            } else {
                databaseNode.setCurrentLoad(databaseNode.getCurrentLoad() + query.getRestWeight());
                query.setRestWeight(0.0);
                allSortedqueryWithAttiributes.remove(query);

            }
            SelectQueryAnalysis.sortQueryByweightAndTablesize(allSortedqueryWithAttiributes);
            // }
        }

        if (allSortedqueryWithAttiributes.size() > 0) {
            System.out.println("\nQuery that are still remaining to schedule are...");
            for (int i = 0; i < allSortedqueryWithAttiributes.size(); i++) {
                allSortedqueryWithAttiributes.get(i).toString();
            }
            System.out.println();
        }
        return databaseNodeList;
    }

    private static DatabaseNode calcutateDifference(List<DatabaseNode> databaseNodeList, Query query) {
        //Integer shows difference of database

        SortedMap<Integer, ArrayList<DatabaseNode>> difference = new TreeMap<Integer, ArrayList<DatabaseNode>>();
        Set<String> tableUsedByqueryAndItsUpdate = new HashSet<>();
        tableUsedByqueryAndItsUpdate.addAll(query.getTableUsed());
        double totalUpdateWeights = 0.0;
        //  System.out.println("restweight of query" +query.getQueryId()+"restweight"+   );
        for (Query updateQuery : query.getUpdates()) {
            tableUsedByqueryAndItsUpdate.addAll(updateQuery.getTableUsed());
            totalUpdateWeights = totalUpdateWeights + updateQuery.getWeight();
         //   System.out.println("update query rest weight of query " + updateQuery.getQueryId() + " " + updateQuery.getWeight());
        }
        //System.out.println("total update weight" + totalUpdateWeights);
        for (DatabaseNode databaseNode : databaseNodeList) {
            // algo line 11-12
            if (databaseNode.getScaledLoad() == databaseNode.getCurrentLoad()) {
                if (difference.get(Integer.MAX_VALUE)== null) {
                    ArrayList<DatabaseNode> databaseNodeArrayList = new ArrayList<>();
                    databaseNodeArrayList.add(databaseNode);
                    difference.put(Integer.MAX_VALUE, databaseNodeArrayList);
                } else {
                    ArrayList<DatabaseNode> databaseNodeArrayList = difference.get(Integer.MAX_VALUE);
                    databaseNodeArrayList.add(databaseNode);
                    difference.replace(Integer.MAX_VALUE, databaseNodeArrayList);
                }

            } else if (databaseNode.getCurrentLoad() == 0) {
                if (difference.get(0) == null) {
                    ArrayList<DatabaseNode> databaseNodeArrayList = new ArrayList<>();
                    databaseNodeArrayList.add(databaseNode);
                    difference.put(0, databaseNodeArrayList);
                } else {
                    ArrayList<DatabaseNode> databaseNodeArrayList = difference.get(0);
                    databaseNodeArrayList.add(databaseNode);
                    difference.replace(0, databaseNodeArrayList);
                }
            } else {
                Set<String> tableAvailableOnDatabaseNode = new HashSet<>();
                tableAvailableOnDatabaseNode.addAll(databaseNode.getFragmentList());
                Set<String> diff = Sets.difference(tableUsedByqueryAndItsUpdate, tableAvailableOnDatabaseNode);
                //  difference.put(diff.size(), databaseNode);
                int d = diff.size();
                if (difference.get(d)== null) {
                    ArrayList<DatabaseNode> databaseNodeArrayList = new ArrayList<>();
                    databaseNodeArrayList.add(databaseNode);
                    difference.put(d, databaseNodeArrayList);
                } else {
                    ArrayList<DatabaseNode> databaseNodeArrayList = difference.get(d);
                    databaseNodeArrayList.add(databaseNode);
                    difference.replace(d, databaseNodeArrayList);
                }
            }
        }
        int count = 0;
        DatabaseNode databaseNode=null;
        Double currentLoad=0.0;
        boolean flag = false;
        for (Map.Entry m : difference.entrySet()) {
            ArrayList<DatabaseNode> databaseNodeArrayList = (ArrayList<DatabaseNode>) m.getValue();
            for (int j = 0; j < databaseNodeArrayList.size(); j++) {
                 currentLoad = databaseNodeArrayList.get(j).getCurrentLoad() + totalUpdateWeights;
                if (currentLoad < 1) {
                    flag = true;
                    databaseNode = databaseNodeArrayList.get(j);
                    break;
                }
            }
            if (flag == true) {
                break;
            }
        }
            // algo line 18
        if(databaseNode==null){
            System.out.println("Query is not able to schudle on any node because on node as capacity ..."+query.getQueryId());
            return null;
        }
            Set<String> updatedFragmentList = new HashSet<>();
            updatedFragmentList.addAll(tableUsedByqueryAndItsUpdate);
            if (databaseNode.getFragmentList() != null) {
                updatedFragmentList.addAll(databaseNode.getFragmentList());
            }
            databaseNode.setFragmentList(updatedFragmentList);
            //algo line 19

        //System.out.println("currenload is  of database Node" + databaseNode.getServerID() + " " + currentLoad);
        //check assumption point no 4
        databaseNode.setCurrentLoad(currentLoad);

        //database node is full if equals and if greater it is overloaded so scale database node
        if (databaseNode.getCurrentLoad() >= databaseNode.getScaledLoad()) {
            calcuateNewScaleload(query, databaseNode);
        }
        Set<Query> queryList = new HashSet<>();
        if (databaseNode.getQueryList() != null) {
            queryList.addAll(databaseNode.getQueryList());
        }
       // new Query(queryId, query, queryCost, tableRequired, frequency, updateQuerySet, weightOfQuery, weightOfQuery, false, 0.0));

        Query q1=new Query(query.getQueryId(),query.getQuery(),query.getQueryCost(),query.getTableUsed(),
                query.getFrequency(),query.getUpdates(),query.getWeight(),query.getRestWeight(),query.isTranscationalQuery(),query.getSortingParameter());
        queryList.add(q1);
        databaseNode.setQueryList(queryList);
        return databaseNode;
    }

    private static List<DatabaseNode> checkAllBackEndAreFull(List<DatabaseNode> databaseNodeList, Query query) {

        int count = 0;
        for (DatabaseNode databaseNode : databaseNodeList) {
            if (databaseNode.getScaledLoad() == 1) {
                count++;

            }
        }

        if (count == databaseNodeList.size()) {
            return null;
        }

        boolean allFull = true;
        for (DatabaseNode databaseNode : databaseNodeList) {
            if (databaseNode.getScaledLoad() != databaseNode.getCurrentLoad()) {
                allFull = false;
                break;
            }
        }
        if (allFull == true) {
            for (DatabaseNode databaseNode : databaseNodeList) {
                calcuateNewScaleload(query, databaseNode);
            }
        }
        return databaseNodeList;
    }

    private static void calcuateNewScaleload(Query query, DatabaseNode databaseNode) {
        //  double newScaleLoad = databaseNode.getCurrentLoad() + (databaseNode.getScaledLoad() * query.getWeight());
        //  databaseNode.setScaledLoad(newScaleLoad);

        if (databaseNode.getScaledLoad() >= 1) {
           // System.out.println("\ndatabaseNode with ID " + databaseNode.getServerID() + "is full , \n" +
              //      "no more scaling can be done..Now on more query can be schedule");
        } else {
            double newScaleLoad = databaseNode.getCurrentLoad() + (databaseNode.getScaledLoad() * query.getWeight());

            if (newScaleLoad > 1) {
             //   System.out.println("\ndatabaseNode with ID " + databaseNode.getServerID() + "is full , \n" +
                   //     "no more scaling can be done..Now on more query can be schedule");
                newScaleLoad = 1;
            }
            databaseNode.setScaledLoad(newScaleLoad);
        }
    }
}
