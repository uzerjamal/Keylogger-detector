import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class JavaNmap{

    public static void execCommand(String command){
        String scan = "";
        Process nmap;
        try{
            nmap = Runtime.getRuntime().exec(command);
            Scanner sc = new Scanner(nmap.getInputStream());
            while(sc.hasNext()){
                scan += sc.nextLine() + "\n";
            }
            System.out.println(scan);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public static void main(String[] args){
        execCommand("cmd /c netstat -ano -p tcp |findstr /C:\"465\" /C:\"443\"");
    }
}