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
