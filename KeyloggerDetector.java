import ProcessDetector.*;
import Gui.*;

public class KeyloggerDetector{
    public static void main(String[] args){
        ProcessDetector p = new ProcessDetector();
        p.execCommand("cmd /c netstat -ano -p tcp |findstr /C:\"465\" /C:\"443\"");

        Kscreen k = new Kscreen();
    }
}