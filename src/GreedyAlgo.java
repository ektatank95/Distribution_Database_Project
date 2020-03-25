import java.util.ArrayList;
import java.util.List;

public class GreedyAlgo {
    public static List<DatabaseNode> findAllocationOnDatabaseForQuery(List<Query> allSortedqueryWithAttiributes, List<DatabaseNode> databaseNodeList) {

        List<DatabaseNode> databaseNodeListWithAllocation = new ArrayList<>();
        //algo line 6 to 9
        for(Query query:allSortedqueryWithAttiributes){
        List<DatabaseNode> scaledDatabaseNodeList= checkAllBackEndAreFull(databaseNodeList,query);
        }
        return databaseNodeListWithAllocation;
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
