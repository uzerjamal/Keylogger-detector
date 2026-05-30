package ProcessDetector;

public class ProcessData {

    public final int processId;
    public final String processName;
    public final String processPath;
    public boolean popUpCreated;

    public ProcessData(int processId, String processName, String processPath) {
        this.processId = processId;
        this.processName = processName;
        this.processPath = processPath;
        this.popUpCreated = false;
    }

    @Override
    public String toString() {
        return processName + " (PID " + processId + ") @ " + processPath;
    }
}
