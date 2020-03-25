import java.util.ArrayList;
import java.util.List;

public class DatabaseNodeAnalysis {
    public static List<DatabaseNode> getAllDatabaseNodeInfo(String fileName){
        List<DatabaseNode> databaseInfo = new ArrayList<>();
        int databaseNo = 0;
        List<String> scaleLoadList = UtlityClass.getInputFromFile(fileName);
        for (int i = 0; i < scaleLoadList.size(); i++) {
            String databaseId="D"+(++databaseNo);
            Double scaleLload= Double.valueOf(scaleLoadList.get(i));

            databaseInfo.add(new DatabaseNode(databaseId,0.0,scaleLload,null,null,0));

            databaseInfo.add(new DatabaseNode(databaseId,0.0,scaleLload,null,null,0));

        }
            return databaseInfo;
    }

    public static void viewDatabaseListInfo(List<DatabaseNode> databaseNodeList) {
        for (int i = 0; i < databaseNodeList.size(); i++) {
            System.out.println(databaseNodeList.get(i).toString());
        }
    }
}

