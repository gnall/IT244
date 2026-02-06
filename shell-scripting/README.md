# Shell Scripting Introduction

Shell scripting is a way to automate tasks in Unix-like operating systems by writing a series of commands in a text file, known as a script. These scripts can simplify repetitive tasks, manage system operations, and streamline workflows.

## What is a Shell Script?

A shell script is a file containing a sequence of commands that the shell interprets and executes. Common shells include Bash, Zsh, and Sh.

## Why Use Shell Scripts?

- Automate repetitive tasks
- Manage files and directories
- Schedule jobs
- System administration

## Valuable Resources

- [Free Online Tutorial](https://www.youtube.com/watch?v=PNhq_4d-5ek)
- [vi/vim Editor Docs](https://www.redhat.com/en/blog/introduction-vi-editor)
- [vi/vim Free Online Tutorial](https://youtu.be/wACD8WEnImo?si=BPZX11Q4Kjs0TzBY&t=451)
- [GNU Bash Manual](https://www.gnu.org/software/bash/manual/bash.html)
- [Bash Guide for Beginners](https://tldp.org/LDP/Bash-Beginners-Guide/html/)
- [Linux Shell Scripting Tutorial](https://linuxconfig.org/bash-scripting-tutorial-for-beginners)

## Basic Example

Below is a simple shell script that prints "Hello, World!" and lists files in the current directory:

```bash
#!/bin/bash

echo "Hello, World!"
ls -l
```

To run this script:

1. Save it as `example.sh`.
2. Make it executable:  
    `chmod +x example.sh`
3. Execute:  
    `./example.sh`




## Lab/Assignments
### Lab 0 - Setting up a Linux based Virtual Machine Locally

#### 1. Installing VirtualBox & Launching a Virtual Machine

- Go to the [VirtualBox Downloads page](https://www.virtualbox.org/wiki/Downloads).
- Download the Windows installer.
- Run the installer and follow the prompts to complete installation.

#### 2. Download Alpine Linux ISO

- Visit the [Alpine Linux Downloads page](https://alpinelinux.org/downloads/).
- Download the latest "Standard" ISO file.
- **For most Windows PCs, choose the `x86_64` Standard ISO, as it is intended for 64-bit Intel/AMD processors.**  
    Other options like `aarch64` and `armv7` are for ARM-based devices.

#### 3. Create a Virtual Machine in VirtualBox

1. Open VirtualBox and click **New**.
2. Name your VM (e.g., "Alpine Linux").
3. Set **Type** to `Linux` and **Version** to `Other Linux (64-bit)`.
4. Allocate memory (e.g., 512MB).
5. Create a virtual hard disk (e.g., 2GB, VDI, dynamically allocated).
6. Select your VM and click **Settings** > **Storage**.
7. Under **Controller: IDE**, click the empty disk icon and choose the Alpine Linux ISO as the optical drive.
8. Click **OK** and start the VM.

#### 4. Install Alpine Linux

- Follow the on-screen instructions to install Alpine Linux.
- Refer to the [Alpine Linux Installation Guide](https://wiki.alpinelinux.org/wiki/Installation) for detailed steps.


### Lab 1 - Getting Familiar with File permissions
1. Run the following commands to create the initial directories and files:
```
# Make the directory
mkdir -p lab1

# Change directory
cd lab1

# Create files
touch README.txt
touch deploy.sh
touch config.conf
touch team_notes.txt

# Create logs directory and files
mkdir logs
touch logs/app.log
touch logs/error.log
```

2. Run the following commmand: `ls -la`. Your output should look something like this: (The file owner may differ)
```
total 12
drwxr-xr-x 3 user user 4096 Feb  5 10:00 .
drwxr-xr-x 5 user user 4096 Feb  5 10:00 ..
-rw-r--r-- 1 user user    0 Feb  5 10:00 README.txt
-rw-r--r-- 1 user user    0 Feb  5 10:00 config.conf
-rw-r--r-- 1 user user    0 Feb  5 10:00 deploy.sh
drwxr-xr-x 2 user user 4096 Feb  5 10:00 logs
-rw-r--r-- 1 user user    0 Feb  5 10:00 team_notes.txt
```

3. Run the following command: `ls -la logs/`. Your output should look something like this: (The file owner may differ)
```
total 8
drwxr-xr-x 2 user user 4096 Feb  5 10:00 .
drwxr-xr-x 3 user user 4096 Feb  5 10:00 ..
-rw-r--r-- 1 user user    0 Feb  5 10:00 app.log
-rw-r--r-- 1 user user    0 Feb  5 10:00 error.log
```


4. Using the linux `chmod` [commands](https://en.wikipedia.org/wiki/Chmod), make the changes necessary to change the permissions of the directory and files to look like this:
```
$ ls -la
total 12
drwxr-xr-x 3 user user 4096 Feb  5 10:00 .
drwxr-xr-x 5 user user 4096 Feb  5 10:00 ..
-rw-r--r-- 1 user user    0 Feb  5 10:00 README.txt
-rw-r----- 1 user user    0 Feb  5 10:00 config.conf
-rwx------ 1 user user    0 Feb  5 10:00 deploy.sh
drwxr-x--- 2 user user 4096 Feb  5 10:00 logs
-rw-rw-r-- 1 user user    0 Feb  5 10:00 team_notes.txt
```
```
$ ls -la logs/
total 8
drwxr-x--- 2 user user 4096 Feb  5 10:00 .
drwxr-xr-x 3 user user 4096 Feb  5 10:00 ..
-rw-r----- 1 user user    0 Feb  5 10:00 app.log
-rw-r----- 1 user user    0 Feb  5 10:00 error.log
```




### Lab 2 - System information Reporter
Create a script that displays useful system information in a formatted report including: current user, hostname, current date/time, disk usage, memory usage, and number of running processes. This teaches basic commands like `whoami`, `hostname`, `date`, `df`, and `ps`, plus output formatting.

**Example Output:**
```
==============================
    System Information Report
==============================

Current User      : joe
Hostname          : alpine-vm
Date & Time       : 2026-02-05 12:34:56

Disk Usage:
Filesystem      Size  Used Avail Use% Mounted on
/dev/sda1       2.0G  1.1G  900M  55% /

Memory Usage:
                            total        used        free      shared  buff/cache   available
Mem:           1024         512         256          32         256         768

Running Processes: 45
```

Bonus learning opportunity to learn how to installing a cron scheduler package like `cronie`, and set-up a cron scheduled job to execute this shell script once every minute and log the results to a log file.

### Lab 3 - Log File Analysis
Create a bash script that analyzes server log files and generates a comprehensive report with statistics and insights.

Your script should:
- Accept a log file path as a command-line argument
- Validate that the file exists and is readable
- Generate a formatted report containing:
    - Total number of log entries (hint: `wc`)
    - Count of each log level: INFO, WARNING, ERROR, CRITICAL (hint: `grep` or `awk`)
    - Timestamp of first and last error (hint: `grep`, `head`, `tail`, `awk`)


#### Sample Log File (server.log):
```
2024-01-15 08:23:11 192.168.1.100 INFO Server started successfully
2024-01-15 08:23:45 192.168.1.105 INFO User login: john_doe
2024-01-15 08:24:12 192.168.1.105 WARNING Slow database query detected
2024-01-15 08:25:33 192.168.1.107 ERROR Failed to connect to database
2024-01-15 08:26:01 192.168.1.100 INFO Background job completed
2024-01-15 08:27:18 192.168.1.105 ERROR Failed to connect to database
2024-01-15 08:28:45 192.168.1.108 CRITICAL Disk space below 5%
2024-01-15 08:29:12 192.168.1.107 ERROR Authentication failed for user admin
2024-01-15 08:30:22 192.168.1.105 WARNING High memory usage detected
2024-01-15 08:31:45 192.168.1.109 INFO User logout: jane_smith
2024-01-15 08:32:11 192.168.1.107 ERROR Failed to connect to database
2024-01-15 08:33:28 192.168.1.110 INFO Cache cleared successfully
2024-01-15 08:34:55 192.168.1.108 ERROR File not found: /var/data/config.xml
2024-01-15 08:35:19 192.168.1.105 WARNING API rate limit approaching
2024-01-15 08:36:42 192.168.1.107 ERROR Permission denied: /etc/secrets
2024-01-15 09:15:33 192.168.1.111 INFO Scheduled backup started
2024-01-15 09:16:05 192.168.1.107 ERROR Failed to connect to database
2024-01-15 09:17:22 192.168.1.105 INFO Data export completed
2024-01-15 09:18:44 192.168.1.112 WARNING SSL certificate expires in 7 days
2024-01-15 09:19:11 192.168.1.108 CRITICAL System temperature exceeds threshold
```
