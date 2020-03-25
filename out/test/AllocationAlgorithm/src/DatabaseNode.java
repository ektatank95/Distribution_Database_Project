import java.util.List;

public class DatabaseNode {
    private String serverID;
    private double currentLoad;
    private double scaledLoad;
    private List<String> queryList;
    private List<String> fragmentList;
    //if database currentload and scaleload become 100% we can't scale...make capacity paramter
    private boolean capacity;

    public DatabaseNode(String serverID, double currentLoad, double scaledLoad, List<String> queryList, List<String> fragmentList, boolean capacity) {
        this.serverID = serverID;
        this.currentLoad = currentLoad;
        this.scaledLoad = scaledLoad;
        this.queryList = queryList;
        this.fragmentList = fragmentList;
        this.capacity = capacity;
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