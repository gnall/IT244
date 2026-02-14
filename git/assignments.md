# Git Assignments

These assignments are designed for first-time Git users. You will do all work in your **private GitLab repository** for IT 244.

**Setup (do once):**

1. Sign in at [https://gitlab.com/university-of-scranton](https://gitlab.com/university-of-scranton) with your University email.
2. Open [https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students/](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students/) and select **your name** to open your private project.
3. Clone the repo to your machine (use the “Clone” button on the project page for the URL):
   ```bash
   git clone <your-repo-url>
   cd <repo-folder>
   ```

Submit your work by **pushing to the branch specified for each assignment**. Your instructor will check those branches.

---

## Assignment 1: First commit on `main`

**Branch to use:** `main`  
**Goal:** Make one commit and push it.

**Tasks:**

1. Open your cloned repo in a terminal. Run `git status`. Note what it says (e.g., “nothing to commit, working tree clean” or list of files).
2. Create a new text file named `git-1.txt`. Inside it, write one or two sentences about what you found most useful in the Git introduction (e.g., from the [Git Branching Strategy Write-up](branching-strategy.md)).
3. Stage the file: `git add git-1.txt`
4. Commit with a clear message: `git commit -m "Add git-1.txt with intro reflection"`
5. Push to GitLab: `git push origin main`

**Submission:** Your work is submitted when `git-1.txt` is on the `main` branch and pushed to your GitLab project.

---

## Assignment 2: Branch and commit on a feature branch

**Branch to use:** Create and push a branch named `assignment-2`  
**Goal:** Create a branch, add a file, commit, and push the branch.

**Tasks:**

1. In your repo, make sure you’re on `main` and up to date: `git checkout main` then `git pull origin main`.
2. Create and switch to a new branch: `git checkout -b assignment-2`
3. Create a file named `git-2.txt`. In it, list **three** Git commands you use often and one sentence for each explaining what it does.
4. Stage and commit: `git add git-2.txt` and `git commit -m "Add git-2.txt with three Git commands"`
5. Push the branch to GitLab: `git push -u origin assignment-2`

**Submission:** Your work is submitted when the `assignment-2` branch exists on GitLab and contains `git-2.txt`.

---

## Assignment 3: Edit, commit, and push again

**Branch to use:** Create and push a branch named `assignment-3`  
**Goal:** Make multiple commits on one branch.

**Tasks:**

1. Update `main` and create a new branch: `git checkout main`, `git pull origin main`, then `git checkout -b assignment-3`.
2. Create a file `git-3.txt`. In the first line, write: `My favorite Git command so far is: ` and then the command. Commit with message: `Add git-3.txt with favorite command`.
3. Add a second line to `git-3.txt` explaining in one sentence why you like that command. Commit with message: `Explain favorite Git command`.
4. Push the branch: `git push -u origin assignment-3`.

**Submission:** Your work is submitted when the `assignment-3` branch is on GitLab with two commits that each change `git-3.txt`.

---

## Assignment 4: Summary and branching

**Branch to use:** Create and push a branch named `assignment-4`  
**Goal:** Summarize branching and push to a branch.

**Tasks:**

1. From an up-to-date `main`, create a branch: `git checkout -b assignment-4`.
2. Create `git-4.txt`. In it, write 2–3 sentences explaining what a branch is and why you might use one (you can use the [Quick Reference](quick-ref.md) or [Branching Strategy](branching-strategy.md) as reference).
3. Commit and push: `git add git-4.txt`, `git commit -m "Add git-4.txt branching summary"`, `git push -u origin assignment-4`.

**Submission:** Your work is submitted when the `assignment-4` branch is on GitLab and contains `git-4.txt`.

---

## Assignment 5: Merge request with a reviewer

**Branch to use:** Create and push a branch named `assignment-5`  
**Goal:** Create a branch, make a file change, open a merge request in GitLab, and add Joseph Gnall as a reviewer.

**Tasks:**

1. From an up-to-date `main`, create a branch: `git checkout main`, `git pull origin main`, then `git checkout -b assignment-5`.
2. Create a new file named `git-5.txt`. In it, write one or two sentences describing what a merge request (or pull request) is and when you would use one.
3. Stage and commit: `git add git-5.txt`, `git commit -m "Add git-5.txt merge request summary"`.
4. Push the branch to GitLab: `git push -u origin assignment-5`.
5. **Open a merge request in GitLab:** Go to your project on GitLab. You should see a prompt to create a merge request for `assignment-5`, or use **Merge requests** → **New merge request**. Select `assignment-5` as the source branch and `main` as the target branch. Add a title (e.g., “Assignment 5: Merge request”) and optional description, then create the merge request.
6. **Add Joseph Gnall as a reviewer:** On the merge request page, use the **Reviewers** (or **Assignees**) section and add **Joseph Gnall** as a reviewer.

**Submission:** Your work is submitted when the merge request exists for `assignment-5` → `main`, contains `git-5.txt`, and has Joseph Gnall added as a reviewer.

---

## Quick reference

| Assignment   | Branch        | Main deliverable   |
|-------------|----------------|--------------------|
| 1           | `main`         | `git-1.txt`        |
| 2           | `assignment-2` | `git-2.txt`        |
| 3           | `assignment-3` | `git-3.txt` (2 commits) |
| 4           | `assignment-4` | `git-4.txt`        |
| 5           | `assignment-5` | Merge request (with Joseph Gnall as reviewer) |

If you’re unsure about any command, see the [Quick Reference](quick-ref.md). For review, use the [Review Questions](review-questions.md).
