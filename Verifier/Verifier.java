package Verifier;
import java.util.Scanner;

public class Verifier{
    public Boolean verify(String path){
        String output = "";
        try{
            Process process = Runtime.getRuntime().exec("cmd /c Verifier\\Signtool verify /pa \""+ path +"\"");
            Scanner sc = new Scanner(process.getInputStream());
            output = sc.nextLine();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println(output);
        if(output.contains("Successfully verified")){
            return true;
        }
        else{
            System.out.println("Could not verify" + path);
            return false;
        }
    }
}