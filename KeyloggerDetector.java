import ProcessDetector.*;
import Gui.*;
import Verifier.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KeyloggerDetector {

    private static final String WHITELIST_PATH = "Whitelist.txt";
    private static final int SCAN_INTERVAL_MS = 3000;

    private static boolean isWhitelisted(String path) {
        File file = new File(WHITELIST_PATH);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String entry = scanner.nextLine().trim();
                if (!entry.isEmpty() && path.equalsIgnoreCase(entry)) {
                    System.out.println("[INFO] Whitelisted: " + path);
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // Whitelist file not found — treat everything as non-whitelisted
        } catch (Exception e) {
            System.out.println("[WARN] Could not read whitelist: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("[INFO] Keylogger Detector started.");
        System.out.println("[INFO] Monitoring SMTP ports 465, 587 and FTP port 21...");
        System.out.println("[INFO] Press Ctrl+C to stop.");

        ProcessDetector detector = new ProcessDetector();
        Verifier verifier = new Verifier();

        while (true) {
            ArrayList<ProcessData> processes = detector.scanPorts();
            for (ProcessData process : processes) {
                if (!process.popUpCreated) {
                    process.popUpCreated = true;
                    if (!isWhitelisted(process.processPath) && !verifier.verify(process.processPath)) {
                        System.out.println("[ALERT] Suspicious process detected: "
                                + process.processName + " (PID " + process.processId + ") at "
                                + process.processPath);
                        new Kscreen(process.processId, process.processName, process.processPath);
                    }
                }
            }
            try {
                Thread.sleep(SCAN_INTERVAL_MS);
            } catch (InterruptedException e) {
                System.out.println("[INFO] Keylogger Detector stopped.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
