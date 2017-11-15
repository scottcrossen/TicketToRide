# Ticket To Ride

An Android version of the classic game *Ticket To Ride*

### Description

This repository is maintained in Java and designed as an android-port of the multiplayer game *Ticket To Ride*.
There are two major components to this project: The frontend -- composed for the android framework -- and the backend -- composed with a generic java platform.

### Getting Started


#### Common Instructions

The java package directory is teamseth.cs340.tickettoride and the server main class is teamseth.cs340.tickettoride.server.main.Main

To package all the files into a handy script, a gradle-task has been provided in the root directory called "zipFiles".
Once the archive has been created you can then access the files and use the following instructions:

#### Server Instructions

To run the server use the following command:
```bash
java -jar server.jar
```
To run the server on a specific port, add the ```-p [port]``` modifier. The default is port 8081
```bash
java -jar server.jar -p 8080
```

#### Android Instructions

This project has been developed on the Nexus 5X. It is suggested that you run it on that as well.

List the names of the devices and emulators that are currently running:
```bash
adb devices
```
Install the app to a specific device:
```bash
adb -s [device-name] install app-debug.apk
```
Uninstall app from a specific device:
```bash
adb -s [device-name] uninstall teamseth.cs340.tickettoride
```

### Helpful Links

[Game Rules](http://cdn0.daysofwonder.com/tickettoride/fr/img/tt_rules_2013_en.pdf)

[Project Assignment](https://students.cs.byu.edu/~cs340ta/fall2017/group_project/)

### Contributors

1. Scott Leland Crossen  
<http://scottcrossen42.com>  
<scottcrossen42@gmail.com>

2. Seth Johnson

3. Andrew Olson

4. Mike Crowther
