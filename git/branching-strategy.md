# Branching Strategy: Gitflow

A **branching strategy** defines how your team uses branches (e.g., for features, releases, and hotfixes). One widely used model is **Gitflow**, popularized by Vincent Driessen (nvie).

## What is Gitflow?

Gitflow uses two long-lived branches and several short-lived branches:

| Branch | Purpose |
|--------|--------|
| **main** (or `master`) | Production-ready code. Only stable, released code lives here. |
| **develop** | Integration branch for the next release. Feature branches merge here. |
| **feature/*** | New work (e.g., `feature/login`). Branch from `develop`, merge back into `develop`. |
| **release/*** | Preparation for a release (version bump, small fixes). Branch from `develop`, merge into `main` and `develop`. |
| **hotfix/*** | Urgent fixes for production. Branch from `main`, merge into `main` and `develop`. |

### Gitflow diagram

![Gitflow branching model](assets/gitflow-diagram.png)

*Gitflow diagram by [Chris Kirby](https://chriskirby.net/streamline-your-tfs-to-git-migration-with-gitflow/). Source: [Streamline your TFS to Git migration with Gitflow](https://chriskirby.net/streamline-your-tfs-to-git-migration-with-gitflow/).*

The diagram shows time flowing left to right: **Master** holds production-ready releases (e.g., tagged v0.1, v0.2, v1.0). **Feature** branches branch from **Develop**, add commits, then merge back into Develop. A **Release** branch is created from Develop for stabilization and version bumping, then merges into both Master and Develop. A **Hotfix** branch is created from Master for an urgent production fix, then merges into both Master and Develop so the fix is in production and in future development.

### Flow in short

1. **Features** are built on `feature/xyz` branches off `develop`; when done, they are merged into `develop`.
2. When it’s time to release, a **release** branch is created from `develop`; when ready, it is merged into `main` (and usually back into `develop`).
3. **Hotfixes** for production are done on `hotfix/xyz` from `main`, then merged into both `main` and `develop`.

This keeps `main` stable and gives a clear place (`develop`) for integrating the next set of features.

### Tags on the production branch

A **tag** in Git is a named pointer to a single commit. Unlike a branch (which moves as you add commits), a tag stays fixed on that commit forever. Tags are ideal for marking **release points** on the production branch.

**Concept:** Think of a tag as a bookmark. When you release version 1.0, you “cut” (create) a tag on the commit that represents that release. Later you can always check out `v1.0` or deploy from that tag, and the name documents what that commit is (e.g., “release 1.0” or “hotfix 1.0.1”).

**How tags are cut on the production branch (Gitflow):**

1. **When a release is merged into `main`**  
   After merging the release branch into `main`, you create a tag on that merge commit so the release has a version name (e.g., `v1.0`, `v1.2`).  
   Example: `git tag -a v1.0 -m "Release 1.0"` (run while on `main` at that commit), then `git push origin v1.0`.

2. **When a hotfix is merged into `main`**  
   After merging the hotfix branch into `main`, you tag that commit with the hotfix version (e.g., `v1.0.1`).  
   Example: `git tag -a v1.0.1 -m "Hotfix 1.0.1"`, then `git push origin v1.0.1`.

So in Gitflow, **tags are cut on `main`** (the production branch) at the exact commit that becomes a deployable release. Every tag on `main` corresponds to a version you could ship or roll back to. Common commands: `git tag` (list tags), `git tag -a <name> -m "<message>"` (create an annotated tag), `git push origin <tagname>` (push the tag to the remote).

## Why it matters

- **main** stays deployable; only tested, release-ready code is merged there.
- **develop** is where everyone integrates; you avoid mixing half-finished work with production.
- **Feature branches** keep work isolated so multiple people can work in parallel.
- **Release and hotfix** branches give a clear process for versioning and emergency fixes.

## Supplementary reading

The original post that introduced and popularized this model:

- **[A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/)** — Vincent Driessen (nvie)

It includes diagrams and step-by-step workflows. The author has since noted that Gitflow can be more than many teams need for continuously delivered web apps; simpler models (e.g., GitHub Flow) are often enough. For this course, understanding Gitflow helps you see how branches can be used in a structured way; you can adapt the idea to simpler workflows (e.g., `main` + feature branches) as needed.

## Setup: cloning your repository (SSH or HTTPS)

To clone your course repository from GitLab, you can use either **SSH** or **HTTPS**. You only need one of the two.

### Option 1: Clone with SSH

If you want to clone (and push) using SSH, you may need to **generate an SSH key on your local machine** and add the public key to GitLab.

1. **Generate an SSH key** (if you don’t already have one):
   ```bash
   ssh-keygen -t ed25519 -C "your.university@scranton.edu"
   ```
   Press Enter to accept the default file location, and optionally set a passphrase.

2. **Copy your public key** to the clipboard:
   - **macOS:** `pbcopy < ~/.ssh/id_ed25519.pub`
   - **Linux (X11):** `xclip -selection clipboard < ~/.ssh/id_ed25519.pub`
   - **Windows (PowerShell):** `Get-Content $env:USERPROFILE\.ssh\id_ed25519.pub | Set-Clipboard`
   - Or open `~/.ssh/id_ed25519.pub` in a text editor and copy its contents.

3. **Add the key to GitLab:** Sign in at [gitlab.com/university-of-scranton](https://gitlab.com/university-of-scranton) → click your avatar (top right) → **Preferences** → **SSH Keys** (or go to [SSH Keys](https://gitlab.com/-/user_settings/ssh_keys)). Paste the key, give it a title (e.g., “My laptop”), and click **Add key**.

4. **Clone using the SSH URL** from your project page (e.g. `git@gitlab.com:university-of-scranton/computing-sciences/courses/it-244/students/your-username/your-project.git`):
   ```bash
   git clone git@gitlab.com:university-of-scranton/.../your-project.git
   ```

### Option 2: Clone with HTTPS and a personal access token

If you prefer HTTPS, use a **personal access token (PAT)** instead of your password. GitLab recommends this for HTTPS clone and push.

1. **Create a personal access token:** Sign in at [gitlab.com/university-of-scranton](https://gitlab.com/university-of-scranton) → click your avatar → **Preferences** → **Access Tokens** (or go to [Access Tokens](https://gitlab.com/-/user_settings/personal_access_tokens)). Create a token with a name (e.g., “IT244”), an expiration date if you want one, and scopes **read_repository** and **write_repository**. Click **Create personal access token**.

2. **Copy the token** and store it somewhere safe; GitLab will not show it again.

3. **Clone using the HTTPS URL** from your project page (e.g. `https://gitlab.com/university-of-scranton/.../your-project.git`):
   ```bash
   git clone https://gitlab.com/university-of-scranton/.../your-project.git
   ```
   When prompted for a password, **paste the personal access token** (not your GitLab account password).

4. **Pushing:** When you `git push`, use the same token as the password when prompted. You can also save the token in your system’s credential helper so you don’t have to enter it every time.

For your IT 244 assignments, you will use simple branches (e.g., one branch per assignment) and push them to your GitLab repo; see [Assignments](assignments.md) for details.
