import java.util.ArrayList;
import java.util.List;

public class fragmentAllocation {


//    public static List<DatabaseNode> setNodeAttributes(String fileName){
//        List<DatabaseNode> nodeInfo = new ArrayList<>();
//        int serverNo=0;
//
//        List<String> queryList = UtlityClass.getInputFromFile(fileName);
//
//        for(int i=0;i<queryList.size();i++){
//            String node = "N"+(++serverNo);
//            double capacity = Integer.parseInt(queryList.get(i));
//
//            nodeInfo.add(new DatabaseNode(node, 0.0,capacity,null));
//        }
//        return nodeInfo;
//    }

    public static void fragmentAllocation(List<Query> allqueryAttiributes,List<DatabaseNode> allNodeAttributes){
        double d=0;
        for(int i=0;i<allqueryAttiributes.size();i++){
//            System.out.println(allqueryAttiributes.get(i).getQueryId());
//            System.out.println(allqueryAttiributes.get(i).getWeight());
//            System.out.println(allqueryAttiributes.get(i).getSortingParameter());
            d=d+allqueryAttiributes.get(i).getWeight();
        }
        System.out.println(d);
        for(int i=0;i<allNodeAttributes.size();i++){
            System.out.println(allNodeAttributes.get(i).getServerID());
            System.out.println(allNodeAttributes.get(i).getCurrentLoad());
            System.out.println(allNodeAttributes.get(i).getScaledLoad());
        }

    }

}
