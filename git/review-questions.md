# Review Questions: Git

Test your understanding with these questions. Try to answer before checking the solution.

### Concepts

1. **What is the difference between Git and a normal folder where you save files?**
   <details>
   <summary>Answer</summary>
   Git keeps a full history of changes (who, when, what). You can revert to any past state, compare versions, and work on parallel branches. A normal folder only has the current files with no built-in history or merge support.
   </details>

2. **What is a “commit,” and why is the commit message important?**
   <details>
   <summary>Answer</summary>
   A commit is a saved snapshot of your project at a point in time. The message describes what changed and why, so you and others can understand the history later. Good messages make it easier to find and revert changes.
   </details>

3. **What is the difference between `git add` and `git commit`?**
   <details>
   <summary>Answer</summary>
   `git add` stages changes (selects which files/changes go into the next commit). `git commit` creates the snapshot from whatever is currently staged. So you can add several files and then make one commit that includes all of them.
   </details>

4. **What is a “remote,” and what is “origin” usually?**
   <details>
   <summary>Answer</summary>
   A remote is a copy of the repository hosted somewhere else (e.g., GitLab). “origin” is the default name Git gives to the remote you cloned from. You push and pull to sync your local repo with the remote.
   </details>

5. **What does `git pull` do? How is it related to `git fetch`?**
   <details>
   <summary>Answer</summary>
   `git pull` fetches the latest commits from the remote and merges them into your current branch. It is effectively `git fetch` followed by `git merge` (or rebase, depending on config) for the current branch.
   </details>

### Branches

6. **What is a branch, and why use one instead of working directly on `main`?**
   <details>
   <summary>Answer</summary>
   A branch is a parallel line of development. Using a branch (e.g., for a feature or assignment) keeps `main` stable while you work. You can switch branches, merge when ready, and avoid mixing unfinished work with the main line.
   </details>

7. **How do you create a new branch and switch to it in one step?**
   <details>
   <summary>Answer</summary>
   ```bash
   git checkout -b my-branch
   ```
   or, with newer Git:
   ```bash
   git switch -c my-branch
   ```
   </details>

8. **After you create a branch locally, how do you push it to GitLab so it appears on the remote?**
   <details>
   <summary>Answer</summary>
   ```bash
   git push -u origin my-branch
   ```
   The `-u` sets the upstream so future `git push` and `git pull` on this branch use that remote branch.
   </details>

### Daily use

9. **How do you see which files you’ve changed but not yet staged?**
   <details>
   <summary>Answer</summary>
   ```bash
   git status
   ```
   It shows untracked, modified, and staged files. For a line-by-line view of unstaged changes, use `git diff`.
   </details>

10. **You staged a file with `git add` but want to unstage it and keep your edits. What do you run?**
    <details>
    <summary>Answer</summary>
    ```bash
    git restore --staged <filename>
    ```
    (Older Git: `git reset HEAD <filename>`)
    </details>

11. **How do you discard local changes in a file and go back to the last committed version?**
    <details>
    <summary>Answer</summary>
    ```bash
    git restore <filename>
    ```
    (Older Git: `git checkout -- <filename>`). Warning: this permanently discards uncommitted changes in that file.
    </details>

12. **What is the difference between `git log` and `git log --oneline`?**
    <details>
    <summary>Answer</summary>
    `git log` shows full commit messages and metadata. `git log --oneline` shows one short line per commit (abbreviated hash + message), which is easier to scan.
    </details>

### Gitflow

13. **In Gitflow, which branch is meant to always be production-ready?**
    <details>
    <summary>Answer</summary>
    The `main` (or `master`) branch. Only tested, release-ready code is merged there.
    </details>

14. **Where do feature branches usually branch from and merge into in Gitflow?**
    <details>
    <summary>Answer</summary>
    Feature branches are created from `develop` and merged back into `develop` when the feature is done. They do not merge directly into `main`.
    </details>

15. **When would you use a hotfix branch instead of a feature branch?**
    <details>
    <summary>Answer</summary>
    Hotfix branches are for urgent fixes to production. You branch from `main`, make the fix, then merge into both `main` and `develop`. Feature branches are for new work and branch from `develop`.
    </details>
