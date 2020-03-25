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
<<<<<<< HEAD
            databaseInfo.add(new DatabaseNode(databaseId,0.0,scaleLload,null,null,0));
=======
            databaseInfo.add(new DatabaseNode(databaseId,0.0,scaleLload,null,null,true));
>>>>>>> 2d7ee3c1333574a76391cd4992c5cf3e2f1dd85d
        }
            return databaseInfo;
    }

    public static void viewDatabaseListInfo(List<DatabaseNode> databaseNodeList) {
        for (int i = 0; i < databaseNodeList.size(); i++) {
            System.out.println(databaseNodeList.get(i).toString());
        }
    }
}

