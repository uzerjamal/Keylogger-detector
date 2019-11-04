package ProcessDetector;

public class ProcessData{
    public int processId;
    public String processName;
    public String processPath;
    public boolean popUpCreated;

    ProcessData(int processId, String processName, String processPath){
        this.processId = processId;
        this.processName = processName;
        this.processPath = processPath;
        this.popUpCreated = false;
    }
}