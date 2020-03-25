import java.util.Comparator;
import java.util.List;

public class Query implements Comparator<Query> {
    private String queryId;
    private String query;
    private Double queryCost;
    private List<String> tableUsed;
    private Integer frequency;
    private List<Query> updates;
    private Double weight;
    private Double restWeight;
    private boolean transcationalQuery;
    private Double sortingParameter;

    public Query() {
    }

    public Query(String queryId, String query, Double queryCost, List<String> tableUsed, Integer frequency, List<Query> updates, Double weight, Double restWeight, boolean transcationalQuery, Double sortingParameter) {
        this.queryId = queryId;
        this.query = query;
        this.queryCost = queryCost;
        this.tableUsed = tableUsed;
        this.frequency = frequency;
        this.updates = updates;
        this.weight = weight;
        this.restWeight = restWeight;
        this.transcationalQuery = transcationalQuery;
        this.sortingParameter = sortingParameter;
    }

    public Double getRestWeight() {
        return restWeight;
    }

    public void setRestWeight(Double restWeight) {
        this.restWeight = restWeight;
    }

    public Double getSortingParameter() {
        return sortingParameter;
    }

    public void setSortingParameter(Double sortingParameter) {
        this.sortingParameter = sortingParameter;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public List<Query> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Query> updates) {
        this.updates = updates;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isTranscationalQuery() {
        return transcationalQuery;
    }

    public void setTranscationalQuery(boolean transcationalQuery) {
        this.transcationalQuery = transcationalQuery;
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

    @Override
    public String toString() {
        return "Query{" +
                "queryId='" + queryId + '\'' +
                ", queryCost=" + queryCost +
                ", tableUsed=" + tableUsed +
                ", frequency=" + frequency +
                ", updates=" + updates +
                ", weight=" + weight +
                ", restWeight=" + restWeight +
                ", transcationalQuery=" + transcationalQuery +
                ", sortingParameter=" + sortingParameter +
                ", query='" + query + '\'' +
                '}';
    }

    public List<String> getTableUsed() {
        return tableUsed;
    }

    public void setTableUsed(List<String> tableUsed) {
        this.tableUsed = tableUsed;
    }



    @Override
    public int compare(Query query, Query t1) {
        return query.getSortingParameter().compareTo(t1.getSortingParameter());

    }
}
