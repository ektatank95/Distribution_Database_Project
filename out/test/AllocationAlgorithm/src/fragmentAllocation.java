import java.util.ArrayList;
import java.util.Collections;
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
        List<Query> tempQueryList = allqueryAttiributes;

        for(int i=0;i<tempQueryList.size();i++){
            for(int j=0;j<allNodeAttributes.size();j++){
                if(allNodeAttributes.get(j).getCurrentLoad()==allNodeAttributes.get(j).getScaledLoad()){
                    allNodeAttributes.get(j).setDifference(1000.0);
                }else if(allNodeAttributes.get(j).getCurrentLoad()==0){
                    allNodeAttributes.get(j).setDifference(0);
                }else{
                    double diff = tempQueryList.get(i).getWeight();
                    allNodeAttributes.get(j).setDifference(diff);
                }
            }



            for(int k=0;k<allNodeAttributes.size();k++){
                System.out.println(allNodeAttributes.get(k).getServerID());
                System.out.println(allNodeAttributes.get(k).getDifference());
            }

            break;
        }
        System.out.println(d);
        for(int i=0;i<allNodeAttributes.size();i++){
            System.out.println(allNodeAttributes.get(i).getServerID());
            System.out.println(allNodeAttributes.get(i).getCurrentLoad());
            System.out.println(allNodeAttributes.get(i).getScaledLoad());
        }

    }

}
