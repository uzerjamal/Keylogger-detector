package ProcessDetector;
import java.io.IOException;
import java.util.Scanner;

public class ProcessDetector{
    int processId;
    String processName;
    String processPath;
    public void execCommand(String command){
        String scan = "";
        int processId;
        Process nmap;
        try{
            nmap = Runtime.getRuntime().exec(command);
            Scanner sc = new Scanner(nmap.getInputStream());
            while(sc.hasNext()){
                scan += sc.nextLine() + "\n";
                String processIdString = scan.substring(scan.lastIndexOf(' ')+1);
                processId = Integer.parseInt(processIdString.substring(0, processIdString.length()-1));
                this.processId = processId;
                processName = processName(processId);
                processPath = processPath(processId);
                System.out.println(processId + " " + processName + " " + processPath);
            }
            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    String processName(int id){
        Process cmd;
        int line = 0;
        String processName;
        try{
            cmd = Runtime.getRuntime().exec("cmd /c tasklist /FI \"PID eq " + id + "\"");
            Scanner sc = new Scanner(cmd.getInputStream());
            while(sc.hasNext()){
                if(line == 3){
                    processName = sc.nextLine();
                    processName = processName.substring(0, processName.indexOf(' '));
                    return processName;
                }
                sc.nextLine();
                line++;
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        return "INVALID";
    }

    String processPath(int id){
        Process cmd;
        int line = 0;
        String processPath;
        try{
            cmd = Runtime.getRuntime().exec("cmd /c wmic process where \"ProcessID=" + id + "\" get ExecutablePath");
            Scanner sc = new Scanner(cmd.getInputStream());
            while(sc.hasNext()){
                if(line == 2){
                    processPath = sc.nextLine();
                    return processPath;
                }
                sc.nextLine();
                line++;
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        return "INVALID";
    }
}