package ProcessDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessDetector {

    // Accumulates detected processes across scans so we don't re-warn about the same path.
    private final ArrayList<ProcessData> data = new ArrayList<>();

    public ArrayList<ProcessData> scanPorts() {
        // Match port numbers precisely: ":PORT " catches the port boundary and avoids
        // false positives (e.g., ":21 " won't match port 210 or 2100).
        String netstatCmd = "netstat -ano -p tcp | findstr /C:\":465 \" /C:\":587 \" /C:\":21 \"";
        Process cmd = exec(netstatCmd);
        if (cmd == null) return data;

        try (Scanner sc = new Scanner(cmd.getInputStream())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 5) continue;

                int pid;
                try {
                    pid = Integer.parseInt(parts[parts.length - 1]);
                } catch (NumberFormatException e) {
                    continue;
                }

                String name = resolveProcessName(pid);
                String path = resolveProcessPath(pid);

                if (!path.equals("INVALID") && !pathAlreadyTracked(path)) {
                    data.add(new ProcessData(pid, name, path));
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] scanPorts: " + e.getMessage());
        }
        return data;
    }

    private String resolveProcessName(int pid) {
        Process cmd = exec("tasklist /FI \"PID eq " + pid + "\" /NH /FO CSV");
        if (cmd == null) return "UNKNOWN";

        try (Scanner sc = new Scanner(cmd.getInputStream())) {
            if (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                // CSV format: "process.exe","PID","Session","#","Mem"
                if (line.startsWith("\"")) {
                    return line.substring(1, line.indexOf('"', 1));
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] resolveProcessName: " + e.getMessage());
        }
        return "UNKNOWN";
    }

    private String resolveProcessPath(int pid) {
        Process cmd = exec("wmic process where \"ProcessID=" + pid + "\" get ExecutablePath /VALUE");
        if (cmd == null) return "INVALID";

        try (Scanner sc = new Scanner(cmd.getInputStream())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("ExecutablePath=")) {
                    String path = line.substring("ExecutablePath=".length()).trim();
                    return path.isEmpty() ? "INVALID" : path;
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] resolveProcessPath: " + e.getMessage());
        }
        return "INVALID";
    }

    private boolean pathAlreadyTracked(String path) {
        for (ProcessData entry : data) {
            if (entry.processPath.equalsIgnoreCase(path)) return true;
        }
        return false;
    }

    private Process exec(String command) {
        try {
            return Runtime.getRuntime().exec(new String[]{"cmd", "/c", command});
        } catch (IOException e) {
            System.out.println("[ERROR] exec failed: " + e.getMessage());
            return null;
        }
    }
}
