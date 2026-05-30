package Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class Kscreen implements ActionListener {

    private static final String WHITELIST_PATH = "Whitelist.txt";
    private static final String WARNING_IMAGE_PATH = "Gui/warn_klog.png";

    private final int pid;
    private final String processName;
    private final String filePath;
    private JFrame frame;

    private JButton openLocationButton;
    private JButton killButton;
    private JButton deleteButton;
    private JButton whitelistButton;

    public Kscreen(int pid, String processName, String filePath) {
        this.pid = pid;
        this.processName = processName;
        this.filePath = filePath;
        buildAndShow();
    }

    private void buildAndShow() {
        frame = new JFrame("Keylogger Detector — Suspicious Process");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Warning image
        try {
            BufferedImage img = ImageIO.read(new File(WARNING_IMAGE_PATH));
            JPanel imgPanel = new JPanel();
            imgPanel.add(new JLabel(new ImageIcon(img)));
            frame.add(imgPanel, BorderLayout.NORTH);
        } catch (IOException e) {
            // Image not critical — continue without it
        }

        // Info label
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.YELLOW);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        JLabel infoLabel = new JLabel(buildInfoText());
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.BOLD));
        infoPanel.add(infoLabel);
        frame.add(infoPanel, BorderLayout.CENTER);

        // Action buttons
        openLocationButton = new JButton("Open File Location");
        openLocationButton.setBackground(Color.LIGHT_GRAY);

        killButton = new JButton("Kill Process");
        killButton.setBackground(new Color(220, 80, 80));
        killButton.setForeground(Color.WHITE);

        deleteButton = new JButton("Delete File");
        deleteButton.setBackground(new Color(230, 140, 40));
        deleteButton.setForeground(Color.WHITE);

        whitelistButton = new JButton("Whitelist File");
        whitelistButton.setBackground(new Color(60, 160, 60));
        whitelistButton.setForeground(Color.WHITE);

        for (JButton btn : new JButton[]{openLocationButton, killButton, deleteButton, whitelistButton}) {
            btn.addActionListener(this);
            btn.setFocusPainted(false);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonPanel.add(openLocationButton);
        buttonPanel.add(killButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(whitelistButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private String buildInfoText() {
        return "<html><b>PID " + pid + "</b> &mdash; <b>" + processName
                + "</b><br>Location: " + filePath
                + "<br>This process is communicating on an email or FTP port.</html>";
    }

    private String parentDirectory(String path) {
        int last = path.lastIndexOf('\\');
        return last > 0 ? path.substring(0, last) : path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == openLocationButton) {
            try {
                Runtime.getRuntime().exec(new String[]{"explorer", parentDirectory(filePath)});
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Could not open file location:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == killButton) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Kill process " + processName + " (PID " + pid + ")?",
                    "Confirm Kill", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Runtime.getRuntime().exec(new String[]{"taskkill", "/F", "/PID", String.valueOf(pid)});
                    JOptionPane.showMessageDialog(frame, "Process killed.", "Done", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Could not kill process:\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (src == deleteButton) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Permanently delete:\n" + filePath + "\n\nThis cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                File file = new File(filePath);
                if (file.delete()) {
                    JOptionPane.showMessageDialog(frame, "File deleted.", "Done", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not delete file. It may be in use or require elevated permissions.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (src == whitelistButton) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(WHITELIST_PATH, true))) {
                writer.write(filePath);
                writer.newLine();
                JOptionPane.showMessageDialog(frame, "Added to whitelist:\n" + filePath,
                        "Whitelisted", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Could not update whitelist:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
