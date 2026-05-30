# Keylogger Detector

A Windows security tool that monitors SMTP and FTP ports for suspicious processes and alerts you before they can exfiltrate data.

Antivirus software acts as a first line of defence against keyloggers, but infections still slip through. Keylogger Detector provides a second layer: it watches the network ports that keyloggers use to send stolen data and warns you the moment anything unexpected tries to connect.

![Sample Warning](https://i.gyazo.com/5deebbb4592215879ead984888c3dea5.png)

---

## How it works

1. **Port scan** — every 3 seconds, `netstat` checks for any process holding an active connection on ports **465** (SMTP/SSL), **587** (SMTP/STARTTLS), or **21** (FTP).
2. **Whitelist check** — known-safe applications listed in `Whitelist.txt` are silently ignored.
3. **Signature check** — digitally-signed executables (e.g., Outlook, Thunderbird) pass automatically via Windows SignTool.
4. **Alert** — anything that clears neither check gets a warning dialog with four actions: open its folder, kill the process, delete the file, or whitelist it.

---

## Prerequisites

| Requirement | Notes |
|---|---|
| **Windows 10 / 11** | Uses `netstat`, `tasklist`, `wmic`, `taskkill`, and `explorer` |
| **Java 11 or later** | [Download from Adoptium](https://adoptium.net/) |
| **Administrator privileges** | Required to kill processes |

---

## Quick start

```bat
git clone https://github.com/uzerjamal/Keylogger-detector.git
cd Keylogger-detector
run.bat
```

`run.bat` compiles all Java source files and launches the detector in one step. You should see:

```
[INFO] Keylogger Detector started.
[INFO] Monitoring SMTP ports 465, 587 and FTP port 21...
[INFO] Press Ctrl+C to stop.
```

Leave it running in the background. A warning dialog appears automatically when a suspicious process is detected.

---

## Manual compilation

If you prefer to compile manually (or use an IDE):

```bat
javac -d . ProcessDetector\ProcessData.java ProcessDetector\ProcessDetector.java Gui\Kscreen.java Verifier\Verifier.java KeyloggerDetector.java
java KeyloggerDetector
```

> **Note:** Run all commands from the project root directory so that relative paths (`Whitelist.txt`, `Gui\warn_klog.png`, `Verifier\signtool.exe`) resolve correctly.

---

## Configuration

### Whitelist

Add trusted application paths to `Whitelist.txt` — one full path per line:

```
C:\Program Files\Mozilla Thunderbird\thunderbird.exe
C:\Program Files\Microsoft Office\root\Office16\OUTLOOK.EXE
```

You can also click **Whitelist File** in the warning dialog and the path is added automatically.

### Monitored ports

| Port | Protocol | Used by |
|---|---|---|
| 465 | SMTP over SSL | Gmail, Yahoo, AOL, AT&T, … |
| 587 | SMTP with STARTTLS | Gmail, Outlook, Office 365, … |
| 21 | FTP control | Generic FTP clients |

---

## Warning dialog actions

| Button | What it does |
|---|---|
| **Open File Location** | Opens Windows Explorer at the folder containing the executable |
| **Kill Process** | Forcefully terminates the process (`taskkill /F /PID`) |
| **Delete File** | Permanently deletes the executable (asks for confirmation) |
| **Whitelist File** | Adds the path to `Whitelist.txt` so it is never flagged again |

---

## Testing with the demo keylogger

A Python keylogger is included in `Keylogger/` so you can verify the detector is working.

> **This script is for educational and testing purposes only. Do not run it against systems you do not own.**

### Setup

```bat
cd Keylogger
pip install pynput python-dotenv
copy .env.example .env
```

Edit `.env` and fill in your credentials:

```env
KEYLOGGER_EMAIL=you@gmail.com
KEYLOGGER_PASSWORD=your_app_password   # https://myaccount.google.com/apppasswords
KEYLOGGER_RECIPIENT=recipient@example.com
```

> Gmail requires an **App Password** (not your regular login password). Generate one at [myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords) with 2FA enabled.

### Run

```bat
python keylogger.py
```

Type a few words and press **Enter**. The keylogger opens an SMTP connection to send the captured text, and the detector should show a warning within about 3 seconds.

Press **Esc** in the keylogger window to stop it.

---

## Testing the GUI in isolation

To check what the warning dialog looks like without running the full detector:

```bat
javac -d . Gui\Kscreen.java Gui\Klook.java
java Gui.Klook
```

---

## Project structure

```
keylogger-detector/
├── KeyloggerDetector.java       # Main entry point — orchestrates the detection loop
├── Whitelist.txt                # Trusted application paths (one per line)
├── run.bat                      # One-click build and run script
│
├── ProcessDetector/
│   ├── ProcessDetector.java     # Port scanner — queries netstat and resolves PIDs
│   └── ProcessData.java         # Data model for a detected process
│
├── Gui/
│   ├── Kscreen.java             # Warning dialog (Swing)
│   ├── Klook.java               # Standalone GUI test launcher
│   └── warn_klog.png            # Warning icon
│
├── Verifier/
│   ├── Verifier.java            # Digital signature checker
│   └── signtool.exe             # Windows SDK SignTool (bundled)
│
└── Keylogger/
    ├── keylogger.py             # Demo keylogger for testing
    └── .env.example             # Credential template
```

---

## Known limitations

- **Signed malware** — malware with a valid code-signing certificate will pass the signature check. The whitelist is your fallback.
- **Encrypted FTP / non-standard ports** — FTPS (port 990) and custom SMTP ports are not monitored.
- **`wmic` deprecation** — `wmic` is deprecated in Windows 11 but still functional. A future version will migrate to `Get-CimInstance`.
- **Same executable, new instance** — if a process is killed and immediately restarts from the same path, the detector will not re-alert (tracked by path). Restart the detector to reset.
