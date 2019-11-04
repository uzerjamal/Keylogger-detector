/* import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

public class Klook {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Kscreen k = new Kscreen();
		

	}
	
	

} */


//import Gui.*;

public class Klook{
    public static void main(String[] args){
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
            }*/
		new Kscreen(8948, "Virus.exe", "C:\\Program Files\\HelloWorld.txt");
        }
    }



