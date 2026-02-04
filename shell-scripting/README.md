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
