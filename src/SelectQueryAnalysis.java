import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectQueryAnalysis {

    public static List<String> findTableRequired(String query) {
        List<String> tableList = new ArrayList<>();
        ResultSet rs;
        try {
            rs = UtlityClass.getConnection().executeQuery(QueryConstant.TABLE_REQUIRED);
            while (rs.next()) {
                tableList.add(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    //bhautik's code
    public static Double findQueryCost(String query) {
        ResultSet rs;
        double queryCost = 0;
        try {
            rs = UtlityClass.getConnection().executeQuery(QueryConstant.EXPLAIN+" " + query);
            while (rs.next()) {
                String name = rs.getString("QUERY PLAN");
              //  System.out.println(name);
                String[] splitCost = name.split("\\.\\.");
                String[] cost1 = splitCost[1].split(" ");
                String finalCost = cost1[0];
                queryCost = Double.parseDouble(finalCost);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryCost;
    }

    public static List<Query> getAllqueryAttiributes(String fileName){
        List<Query> queryInfo=new ArrayList<>();
        int queryNo=0;
        List<String> queryList=UtlityClass.getInputFromFile(Configuration.SELECT_QUERY_TXT_FILE);
        for (int i=0;i<queryList.size();i++){
            String queryId="Q"+ (++queryNo);
            String query=queryList.get(i);
            Double queryCost=findQueryCost(query);
            List<String> tableRequired = findTableRequired(query);
            queryInfo.add(new Query(queryId,query,queryCost,tableRequired));
        }
        return queryInfo;
    }
    public static void viewQueryListInfo(List<Query> queryList){
        for (int i=0;i<queryList.size();i++){
            System.out.println(queryList.get(i).toString());
        }
    }
}