# Quick Reference: Common Git Commands

Use this page as a cheat sheet for the Git commands you will use most often.

```bash
# === Setup & configuration ===
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
git config --list                    # Show your Git configuration

# === Create / clone repositories ===
git init                            # Turn current folder into a Git repo
git clone <url>                     # Clone a remote repo (e.g., from GitLab)

# === Daily workflow ===
git status                          # See what’s changed and what’s staged
git add <file>                      # Stage a file for commit
git add .                           # Stage all changes in current directory
git commit -m "Your message"        # Commit staged changes with a message
git push                            # Send commits to remote (e.g., GitLab)
git pull                            # Get latest commits from remote

# === Branches ===
git branch                          # List local branches
git branch <name>                   # Create a new branch
git checkout <branch>               # Switch to a branch
git checkout -b <name>              # Create and switch to a new branch
git switch <branch>                 # Switch branch (newer command)
git switch -c <name>                # Create and switch (newer command)
git push -u origin <branch>         # Push branch and set upstream

# === Viewing history ===
git log                             # Commit history (full)
git log --oneline                   # Short one-line-per-commit
git log -n 5                        # Last 5 commits
git diff                            # Unstaged changes
git diff --staged                   # Staged changes

# === Remote ===
git remote -v                       # List remotes (e.g., origin)
git remote add origin <url>         # Add remote (usually once after git init)
git fetch                           # Download refs/objects from remote
git pull                            # Fetch and merge (same as fetch + merge)

# === Undo / fix ===
git restore <file>                  # Discard unstaged changes in file
git restore --staged <file>         # Unstage file (keep changes)
git reset --soft HEAD~1             # Undo last commit, keep changes staged
git reset --hard HEAD~1             # Undo last commit and discard changes (careful!)
```

## Typical workflow

1. **Clone** (first time): `git clone <your-gitlab-repo-url>`
2. **Create a branch** for your work: `git checkout -b assignment-1`
3. **Edit files**, then **stage** and **commit**:
   ```bash
   git add .
   git commit -m "Complete assignment 1"
   ```
4. **Push** your branch to GitLab: `git push -u origin assignment-1`
5. For the next assignment, switch to `main`, pull, then create a new branch and repeat.

For more detail on branching and workflows, see [Branching Strategy](branching-strategy.md).
