import java.util.Comparator;
import java.util.List;

public class DatabaseNode implements Comparator<DatabaseNode> {
    private String serverID;
    private double currentLoad;
    private double scaledLoad;
    private List<String> queryList;
    private List<String> fragmentList;
    private double difference;


    public DatabaseNode(String serverID, double currentLoad, double scaledLoad, List<String> queryList, List<String> fragmentList,double difference) {
        this.serverID = serverID;
        this.currentLoad = currentLoad;
        this.scaledLoad = scaledLoad;
        this.queryList = queryList;
        this.fragmentList = fragmentList;
        this.difference = difference;
    }

    public DatabaseNode() {

    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public List<String> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<String> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public double getScaledLoad() {
        return scaledLoad;
    }

    public List<String> getQueryList() {
        return queryList;
    }

    public void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    public void setScaledLoad(double scaledLoad) {
        this.scaledLoad = scaledLoad;
    }

    public void setQueryList(List<String> queryList) {
        this.queryList = queryList;
    }

    @Override
    public String toString() {
        return "DatabaseNode{" +
                "serverID='" + serverID + '\'' +
                ", currentLoad=" + currentLoad +
                ", scaledLoad=" + scaledLoad +
                ", queryList=" + queryList +
                ", fragmentList=" + fragmentList +
                '}';
    }

    @Override
    public int compare(DatabaseNode node, DatabaseNode t1) {
        return node.getDifference().compareTo(t1.getDifference());
    }
}
