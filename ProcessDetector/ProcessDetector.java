package ProcessDetector;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class ProcessDetector{
    ArrayList<ProcessData> data = new ArrayList<ProcessData>();
    int processId;
    String processName;
    String processPath;
	
	private Process execCommand(String command){
		Process process;
		try{
            process = Runtime.getRuntime().exec(command);
            return process;
		}
		catch(Exception e){
            System.out.println(e);
            return null;
		}
	}
	
    public ArrayList<ProcessData> scanPorts(){
        String scan = "";
        Process cmd = execCommand("cmd /c netstat -ano -p tcp |findstr /C:\"465\" /C:\"587\" /C:\"21\"");
        try{
            Scanner sc = new Scanner(cmd.getInputStream());
            while(sc.hasNext()){
                scan = sc.nextLine();
                processId = Integer.parseInt(scan.substring(scan.lastIndexOf(' ')+1));
                processName = processName(processId);
                processPath = processPath(processId);
                if(!processPath.equals("INVALID") && !pathExists(processPath)){
                    data.add(new ProcessData(processId, processName, processPath));
                }
            }
            sc.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return this.data;
    }

    String processName(int id){
        int line = 0;
        String processName;
		Process cmd = execCommand("cmd /c tasklist /FI \"PID eq " + id + "\"");
        try{
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
        catch(Exception e){
            System.out.println(e);
        }
        return "INVALID";
    }

    String processPath(int id){
        int line = 0;
        String processPath;
		Process cmd = execCommand("cmd /c wmic process where \"ProcessID=" + id + "\" get ExecutablePath");
        try{
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
        catch(Exception e){
            System.out.println(e);
        }
        return "INVALID";
    }

    Boolean pathExists(String path){
        for(int i=0; i<data.size(); i++){
            if(data.get(i).processPath.equals(path))
                return true;
        }
        return false;
    }
}