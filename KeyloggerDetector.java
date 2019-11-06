import ProcessDetector.*;
import Gui.*;
import Verifier.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class KeyloggerDetector{
    static Boolean isWhitelisted(String path){
        File file = new File("Whitelist.txt");
        String line = "";
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                if(line.contains(path)){
                    System.out.println(path + " is whitelisted");
                    return false;
                }
            }
        }
        catch(Exception e){

        }
        return true;
    }

    public static void main(String[] args){
        ArrayList<ProcessData> data = new ArrayList<ProcessData>();
        ProcessDetector p = new ProcessDetector();
        Verifier verifier = new Verifier();

        while(true){
            data = p.scanPorts();
            for(int i=0; i<data.size(); i++){
                if(!data.get(i).popUpCreated){
                    data.get(i).popUpCreated = true;
                    if(isWhitelisted(data.get(i).processPath)){
                        if(!verifier.verify(data.get(i).processPath)){
                            new Kscreen(data.get(i).processId, data.get(i).processName, data.get(i).processPath);
                        }
                    }
                }
            }
        }
    }
}