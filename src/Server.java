import java.util.List;

public class Server {
    private String serverID;
    private double currentLoad;
    private double scaledLoad;
    private List<String> queryList;

    public Server(String serverID, double currentLoad, double scaledLoad, List<String> queryList) {
        this.serverID=serverID;
        this.currentLoad=currentLoad;
        this.scaledLoad=scaledLoad;
        this.queryList=queryList;
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
}
