package Gui;

/**
 * Standalone test launcher — shows a sample warning dialog without starting
 * the full detector. Useful for verifying the GUI looks correct.
 *
 * Run from the project root:
 *   javac -d . Gui\Kscreen.java Gui\Klook.java
 *   java Gui.Klook
 */
public class Klook {

    public static void main(String[] args) {
        new Kscreen(8948, "Virus.exe", "C:\\Users\\Public\\Virus.exe");
    }
}
