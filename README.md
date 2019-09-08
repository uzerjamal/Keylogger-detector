
# Keylogger Detector
Warns the user when a malicious process tries to send data from your machine.

## Description
Anti-viruses act as a first line of defence against keyloggers, but often they are unable to detect the viruses and the computer ends up being infected.
Keylogger Detector aims to provide another layer of defence by providing a monitoring tool that monitors the common ways keyloggers send information from the computers to the hacker and warns the users for the same.

## E-mail monitoring
Keyloggers work by logging all the keystores and storing the file locally for a fixed period of time, after which they send the log file to the hacker through a email.
Keylogger Detector monitors most used SMTP ports to detect the processes trying to communicate using 
### Popular SMTP servers monitored by Keylogger Detector
```smtp.gmail.com      SSL         465
smtp.gmail.com      StartTLS    587

smtp.live.com	    SSL         465
smtp.live.com       StartTLS    587

smtp.office365.com  StartTLS	587

smtp.mail.yahoo.com SSL         465

smtp.aol.com	    StartTLS	587

smtp.att.yahoo.com  SSL         465
...
```

### Sample Warning
![Sample Warning](https://i.gyazo.com/5deebbb4592215879ead984888c3dea5.png)
