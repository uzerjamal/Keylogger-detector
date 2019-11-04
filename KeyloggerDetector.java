import ProcessDetector.*;
import Gui.*;
import java.util.ArrayList;

public class KeyloggerDetector{
    public static void main(String[] args){
        ArrayList<ProcessData> data = new ArrayList<ProcessData>();
        ProcessDetector p = new ProcessDetector();
        /*int processId;
        String processName, processPath;
        boolean windowOpened = false;
        ProcessDetector p = new ProcessDetector();
        while(true){
            p.execCommand("cmd /c netstat -ano -p tcp |findstr /C:\"465\" /C:\"587\"");
            processId = p.getProcessId();
            processName = p.getProcessName();
            processPath = p.getProcessPath();
            if(processId != 0 && windowOpened==false){
                Kscreen k = new Kscreen(processId, processName, processPath);
                windowOpened = true;
            }
        }*/
        while(true){
            data = p.scanPorts();
            for(int i=0; i<data.size(); i++){
                if(!data.get(i).popUpCreated){
                    data.get(i).popUpCreated = true;
                    new Kscreen(data.get(i).processId, data.get(i).processName, data.get(i).processPath);
                }
            }
        }
    }
}