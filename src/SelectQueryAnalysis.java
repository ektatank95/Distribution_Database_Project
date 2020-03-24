import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectQueryAnalysis {

    public static List<String> findAllTableOfDatabase() {
        List<String> tableList = new ArrayList<>();
        ResultSet rs;
        try {
            rs = UtlityClass.getConnection().executeQuery(QueryConstant.TABLE_REQUIRED);
            while (rs.next()) {
                tableList.add(rs.getString("table_name").toLowerCase());
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
            rs = UtlityClass.getConnection().executeQuery(QueryConstant.EXPLAIN + " " + query);
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

    public static List<Query> getAllqueryAttiributes(String fileName) {
        List<Query> queryInfo = new ArrayList<>();
        int queryNo = 0;
        List<String> queryList = UtlityClass.getInputFromFile(fileName);
        Double totalFrequencyCost=0.0;
        for (int i = 0; i < queryList.size(); i++) {
            String queryId = "Q" + (++queryNo);
            String[] split = queryList.get(i).split("#");
            String query = null;
            Integer frequency = 1;

            /*
            Validation for query format
            */

            try {
                 query = split[0].toLowerCase();
                frequency = Integer.valueOf(split[1]);
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println(queryId + "is not specified in format... format is query#frequencyof Query");
                System.out.println("This query will be disgarded");
                e.printStackTrace();
                continue;
            }
            Double queryCost = findQueryCost(query);
            List<String> allTables = findAllTableOfDatabase();
            List<String> tableRequiredByQuery = findTableRequiredByquery(query, allTables);
            List<Query> updateQueryList = findUpdateQueryReleted(tableRequiredByQuery);
            Double weightOfQuery = findWeightOfQuery(frequency, queryCost);
            totalFrequencyCost=totalFrequencyCost+weightOfQuery;
            queryInfo.add(new Query(queryId, query, queryCost, tableRequiredByQuery, frequency, updateQueryList, weightOfQuery, false));
        }
        for(int i=0;i<queryInfo.size();i++){
            Query query=queryInfo.get(i);
            queryInfo.get(i).setQueryCost(query.getWeight()/totalFrequencyCost);
        }
        return queryInfo;
    }

    private static Double findWeightOfQuery(  Integer frequency, Double queryCost) {
        // if needed improvement let me know
        return frequency*queryCost;
    }

    private static List<Query> findUpdateQueryReleted(List<String> tableRequiredByQuery) {
        //yet to implement
        return null;
    }

    private static List<String> findTableRequiredByquery(String query, List<String> allTables) {
        List<String> tableList = new ArrayList<>();
        for (int i = 0; i < allTables.size(); i++) {
            if (query.contains(allTables.get(i))) {
                tableList.add(allTables.get(i));
            }
        }
        return tableList;
    }

    public static void viewQueryListInfo(List<Query> queryList) {
        for (int i = 0; i < queryList.size(); i++) {
            System.out.println(queryList.get(i).toString());
        }
    }
}