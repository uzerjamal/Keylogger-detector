import ProcessDetector.*;
import Gui.*;
import Verifier.*;
import java.util.ArrayList;

public class KeyloggerDetector{
    public static void main(String[] args){
        ArrayList<ProcessData> data = new ArrayList<ProcessData>();
        ProcessDetector p = new ProcessDetector();
        Verifier verifier = new Verifier();

        while(true){
            data = p.scanPorts();
            for(int i=0; i<data.size(); i++){
                if(!data.get(i).popUpCreated){
                    data.get(i).popUpCreated = true;
                    if(!verifier.verify(data.get(i).processPath)){
                        new Kscreen(data.get(i).processId, data.get(i).processName, data.get(i).processPath);
                    }
                }
            }
        }
    }
}