import java.util.List;

public class Query {
    private String queryId;
    private String query;
    private Double queryCost;
    private List<String> tableUsed;

    public Query(String queryId, String query, Double queryCost, List<String> tableUsed) {
        this.queryId = queryId;
        this.query = query;
        this.queryCost = queryCost;
        this.tableUsed = tableUsed;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Double getQueryCost() {
        return queryCost;
    }

    public void setQueryCost(Double queryCost) {
        this.queryCost = queryCost;
    }

    public List<String> getTableUsed() {
        return tableUsed;
    }

    public void setTableUsed(List<String> tableUsed) {
        this.tableUsed = tableUsed;
    }

    @Override
    public String toString() {
        return "Query{" +
                "queryId='" + queryId + '\'' +
                ", query='" + query + '\'' +
                ", queryCost=" + queryCost +
                ", tableUsed=" + tableUsed +
                '}';
    }
}
