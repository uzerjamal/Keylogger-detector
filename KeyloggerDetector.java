import ProcessDetector.*;
import Gui.*;

public class KeyloggerDetector{
    public static void main(String[] args){
        int processId;
        String processName, processPath;
        ProcessDetector p = new ProcessDetector();
        p.execCommand("cmd /c netstat -ano -p tcp |findstr /C:\"465\" /C:\"587\"");
        processId = p.getProcessId();
        processName = p.getProcessName();
        processPath = p.getProcessPath();

        if(processId != 0){
            Kscreen k = new Kscreen(processId, processName, processPath);
        }
    }
}