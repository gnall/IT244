# Introduction to Git Version Control

- [Quick Reference: Common Git Commands](quick-ref.md)
- [Branching Strategy: Gitflow](branching-strategy.md)
- [Review Questions](review-questions.md)
- [Assignments](assignments.md)

## What is Git?

Git is a **distributed version control system** (DVCS) used to track changes in files and coordinate work among multiple people. Unlike simply saving files in a folder, Git keeps a history of what changed, when, and by whom—so you can revert mistakes, compare versions, and collaborate without overwriting each other’s work.

### Why Use Version Control?

- **History**: See every change, who made it, and why (via commit messages).
- **Recovery**: Restore previous versions or undo changes.
- **Collaboration**: Multiple people work on the same project; Git merges their changes.
- **Branching**: Try new ideas in separate branches without affecting the main code.
- **Backup**: Clone a repository to get a full copy of the project and its history.

### Key Concepts

| Term | Meaning |
|------|--------|
| **Repository** | A project folder tracked by Git (local on your machine or remote on a server like GitLab). |
| **Commit** | A saved snapshot of your project at a point in time, with a message describing the change. |
| **Branch** | A parallel line of development (e.g., `main` for stable code, `feature/login` for a new feature). |
| **Merge** | Combining the changes from one branch into another (e.g., bringing a feature branch into `main`). Git creates a merge commit that ties the two histories together. |
| **Merge request / Pull request** | A request to merge one branch into another, reviewed in the web UI. **Merge request** (MR) is GitLab’s term; **pull request** (PR) is GitHub’s. They are the same idea—the name depends on which Git hosting solution you use. |
| **Remote** | A copy of the repository hosted elsewhere (e.g., GitLab); you push and pull to sync. |
| **Clone** | Download a copy of a remote repository to your computer. |
| **Push** | Send your commits from your local repository to a remote. |
| **Pull** | Get the latest commits from the remote into your local repository. |
| **Tag** | A named pointer to a specific commit. Tags are often used to mark release versions (e.g., `v1.0`, `v2.1.3`) on the production branch so you can always refer to or checkout that exact release. |


---

## Your Course GitLab Setup

Each student has a **private Git repository** on GitLab for this course. You will use it for assignments and to practice Git.

### 1. Sign in to GitLab

- Go to **[https://gitlab.com/university-of-scranton](https://gitlab.com/university-of-scranton)**.
- Sign in with your **University of Scranton email address** (SAML single sign-on).

### 2. Find Your Private Repository

- After signing in, open the course student group:  
  **[https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students/](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students/)**
- Select **your name** from the list to open your private project.
- That project is your personal Git repository for IT 244. You will clone it, create branches, commit, and push your work there as directed in the [assignments](assignments.md).

---

## Installing Git

Install Git for your operating system using one of the options below.

### Windows

1. **Official installer (recommended)**  
   - Download from [https://git-scm.com/download/win](https://git-scm.com/download/win).  
   - Run the installer and accept the default options (or adjust if you prefer).  
   - Restart your terminal (e.g., Command Prompt or PowerShell) after installation.

2. **Optional: Git from Microsoft Store**  
   - Open Microsoft Store, search for “Git,” and install the Git package.

3. **Verify**  
   Open a new terminal and run:
   ```bash
   git --version
   ```
   You should see something like `git version 2.43.0`.

### macOS

1. **Xcode Command Line Tools (easiest)**  
   - Open Terminal and run:
     ```bash
     xcode-select --install
     ```
   - Follow the prompts to install. This includes Git.

2. **Homebrew (for a newer Git)**  
   - If you use [Homebrew](https://brew.sh/):
     ```bash
     brew install git
     ```

3. **Verify**  
   ```bash
   git --version
   ```

### Linux

1. **Debian / Ubuntu**  
   ```bash
   sudo apt update
   sudo apt install git
   ```

2. **Fedora / RHEL**  
   ```bash
   sudo dnf install git
   ```
   (On older Fedora/RHEL: `sudo yum install git`)

3. **Arch**  
   ```bash
   sudo pacman -S git
   ```

4. **Verify**  
   ```bash
   git --version
   ```

### First-Time Git Configuration

After installing, set your name and email (used in every commit):

```bash
git config --global user.name "Your Name"
git config --global user.email "your.university@scranton.edu"
```

Use the same email you use to sign in to GitLab so your commits are linked to your account.

---

## Next Steps

- Skim the [Quick Reference](quick-ref.md) for common commands.
- Read [Branching Strategy](branching-strategy.md) for an overview of Gitflow.
- Use [Review Questions](review-questions.md) to check your understanding.
- Complete the [Assignments](assignments.md) in your GitLab repository.
