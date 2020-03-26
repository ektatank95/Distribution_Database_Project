import java.util.Set;

public class DatabaseNode {
    private String serverID;
    private double currentLoad;
    private double scaledLoad;
    private Set<Query> queryList;
    private Set<String> fragmentList;
    //if database currentload and scaleload become 100% we can't scale...make capacity paramter
    private boolean capacity;

    public DatabaseNode(String serverID, double currentLoad, double scaledLoad, Set<Query> queryList, Set<String> fragmentList, boolean capacity) {
        this.serverID = serverID;
        this.currentLoad = currentLoad;
        this.scaledLoad = scaledLoad;
        this.queryList = queryList;
        this.fragmentList = fragmentList;
        this.capacity = capacity;
    }


    public Set<String> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(Set<String> fragmentList) {
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

    public Set<Query> getQueryList() {
        return queryList;
    }

    public void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    public void setScaledLoad(double scaledLoad) {
        this.scaledLoad = scaledLoad;
    }

    public void setQueryList(Set<Query> queryList) {
        this.queryList = queryList;
    }


    public boolean isCapacity() {
        return capacity;
    }

    public void setCapacity(boolean capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "DatabaseNode{" +
                "serverID='" + serverID + '\'' +
                ", currentLoad=" + currentLoad +
                ", scaledLoad=" + scaledLoad +
                ", queryList=" + queryList +
                ", fragmentList=" + fragmentList +
                ", capacity=" + capacity +
                '}';
    }
}
