package Verifier;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Verifier {

    private static final String SIGNTOOL_PATH = "Verifier\\signtool.exe";

    /**
     * Returns true if the file at the given path has a valid digital signature,
     * meaning it is a trusted, signed executable (e.g., Outlook, Thunderbird).
     * Signed processes are allowed through without a warning.
     */
    public boolean verify(String path) {
        if (!new File(SIGNTOOL_PATH).exists()) {
            System.out.println("[WARN] signtool.exe not found at " + SIGNTOOL_PATH + " — skipping signature check.");
            return false;
        }

        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{SIGNTOOL_PATH, "verify", "/pa", path});

            try (Scanner sc = new Scanner(process.getInputStream())) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.contains("Successfully verified")) {
                        System.out.println("[INFO] Signature OK: " + path);
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Signature check failed for " + path + ": " + e.getMessage());
        }

        System.out.println("[INFO] Not signed or unverifiable: " + path);
        return false;
    }
}
