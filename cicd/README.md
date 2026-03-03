# Continuous Integration and Continuous Delivery (CI/CD)

- [Assignments](assignments.md)

## What Is CI/CD?

**Continuous Integration (CI)** is the practice of automatically building and testing code whenever changes are committed to version control. Instead of waiting until the end of a project to integrate and test, the system integrates and validates every change as it happens.

**Continuous Delivery (CD)** extends that idea: once code is built and tested, the same pipeline can automatically (or with approval) deploy it to an environment—staging, production, or both. The goal is to have software that is always in a deployable state.

Together, **CI/CD** means: on every relevant change (e.g., push or merge), run a defined sequence of steps—build, test, scan, package, and optionally deploy—so that problems are caught early and releases are repeatable and traceable.

---

## Why CI/CD Matters

### 1. Fast feedback

Without automation, developers might not run tests or linters before pushing. With CI, every push triggers the same checks. If something breaks, the developer finds out within minutes instead of days or at release time.

### 2. Consistent quality gates

Manual steps vary by person and day. A pipeline runs the same commands every time: compile, lint, unit tests, integration tests, secret scans, container scans. That consistency makes it easier to trust that “green” means “ready.”

### 3. Fewer integration surprises

CI encourages small, frequent commits. Integrating often reduces the chance of huge, conflicting merges and makes it easier to pinpoint which change caused a failure.

### 4. Safer, repeatable deployments

CD uses the same built artifacts and scripts for every deployment. That reduces “works on my machine” and “we forgot a step” problems and makes rollbacks and audits straightforward.

### 5. Security in the loop

Pipelines can run secret detection, dependency checks, and container scans on every run. Security becomes part of the normal flow instead of a one-off audit.

---

## Core Concepts (Vendor-Neutral)

These ideas apply to any CI/CD system, not just one product.

| Concept | Meaning |
|--------|---------|
| **Pipeline** | A single run of the full workflow (e.g., “pipeline for commit abc123”). It is triggered by an event (push, merge, schedule, manual). |
| **Stage** | A phase in the pipeline (e.g., “build,” “test,” “deploy”). Stages run in order; the next stage usually starts only after the previous one succeeds. |
| **Job** | One unit of work inside a stage (e.g., “run unit tests,” “build Docker image”). A stage can have one or more jobs; jobs in the same stage can run in parallel. |
| **Runner / Agent** | The machine or container that executes jobs. It pulls code, runs the commands you define, and reports results back. |
| **Artifact** | Output produced by a job (e.g., compiled binary, JAR, Docker image) that is kept for later jobs or for deployment. |
| **Trigger** | The event that starts a pipeline: push, merge request, tag, schedule, or manual button. |
| **Environment** | A logical target for deployment (e.g., “staging,” “production”). Some systems tie deployments and approvals to environments. |

A typical flow:

1. **Trigger** — Developer pushes to a branch or opens a merge request.
2. **Build stage** — Compile code, build container images; jobs produce **artifacts**.
3. **Test stage** — Run unit tests, integration tests, linting; no deploy yet.
4. **Scan / quality stage** — Secret detection, dependency scan, container vulnerability scan.
5. **Deploy stage** (optional) — Push images to a registry; deploy to staging or production (often with manual approval for production).

---

## Common Pipeline Stages in Practice

### Build

- Compile source code (e.g., Java, Go, Node).
- Build Docker images from a Dockerfile.
- Produce artifacts: binaries, JARs, images.

### Test

- Unit tests, integration tests.
- Code coverage reporting.
- Linting (style, complexity, best practices).

### Security and quality

- **Secret detection** — Scan repo and history for leaked credentials (API keys, passwords).
- **Dependency scanning** — Known vulnerabilities in dependencies.
- **Container scanning** — CVEs in the built container image.

### Publish

- Push Docker images to a container registry.
- Publish packages to a package registry.

### Deploy

- Deploy to staging or production (automatically or after manual approval).
- Often separated into “deploy to staging” (auto) and “deploy to production” (manual or approval gate).

---

## Tying It All Together: GitLab CI/CD

The concepts above map directly onto **GitLab CI/CD**:

| General concept | In GitLab |
|-----------------|-----------|
| Pipeline | A **pipeline** — one execution for a commit/merge request. View under **Build → Pipelines**. |
| Stage | A **stage** — defined in `.gitlab-ci.yml` (e.g., `build`, `test`, `deploy`). Jobs in the same stage can run in parallel. |
| Job | A **job** — each top-level key under `jobs` (or the default job list) is a job with `script`, `stage`, etc. |
| Runner | A **GitLab Runner** — executes jobs; can be shared or project-specific, on your own host or GitLab’s. |
| Artifact | **Artifacts** — files or paths saved by a job; later jobs can download them. |
| Trigger | **Rules** and **when** — e.g., run on push, only on main, or **when: manual** for deploy. |
| Environment | **Environments** — e.g., “staging,” “production”; optional but useful for deployment tracking and approvals. |

Configuration lives in a file at the root of the repo: **`.gitlab-ci.yml`**. GitLab reads this file and runs the pipeline according to the stages and jobs you define.

---

## GitLab CI/CD Configuration Basics

### The config file: `.gitlab-ci.yml`

Everything is defined in YAML. Example minimal file:

```yaml
stages:
  - build
  - test

build-job:
  stage: build
  script:
    - echo "Building..."
    - make build

test-job:
  stage: test
  script:
    - echo "Testing..."
    - make test
```

- **`stages`** — List of stage names in order. Jobs declare a `stage`; if omitted, they use the default (first stage).
- **Job names** (`build-job`, `test-job`) — Arbitrary keys; they appear in the GitLab UI.
- **`script`** — List of shell commands that the runner executes. If any command exits non-zero, the job fails.

### Stages

Stages run in the order they appear under `stages`. Jobs in the same stage run in parallel (subject to runner availability). The next stage starts only when all jobs in the current stage succeed.

```yaml
stages:
  - lint
  - build
  - test
  - scan
  - deploy

# Lint and build can be separate; test runs after build; scan after test; deploy last.
```

### Using a different image (Docker)

By default, the runner may use a default image or the host. To run jobs in a specific container:

```yaml
java-lint:
  image: maven:3-eclipse-temurin-17
  stage: lint
  script:
    - mvn compile
    - mvn checkstyle:check
```

- **`image`** — Docker image used to run the job. GitLab runs the job inside a container based on this image.

For jobs that need Docker (e.g., to build images), you typically use **Docker-in-Docker (dind)** or Kaniko (Docker emulation):

```yaml
build-docker:
  image: docker:24
  stage: build
  services:
    - docker:24-dind
  variables:
    DOCKER_TLS_CERTDIR: "/certs"
  script:
    - docker build -t myapp .
```

- **`services`** — Sidecar containers; `docker:dind` provides a Docker daemon the job can use.
- **`variables`** — Environment variables for the job; `DOCKER_TLS_CERTDIR` is commonly set for dind.

### Rules: when and where jobs run

**`rules`** control whether a job is included in the pipeline and how (e.g., run automatically vs. manual only).

| Situation | Example |
|----------|--------|
| Run only on the default branch | `rules: - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH` |
| Run only for merge requests | `rules: - if: $CI_PIPELINE_SOURCE == "merge_request_event"` |
| Run only when a file changed | `rules: - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH` with `changes: ["**/*.java"]` |
| Manual trigger only | `when: manual` (job appears in UI; runs only when you click “Play”) |

Example: run a job only on `main` and only when the Dockerfile changed:

```yaml
build-image:
  stage: build
  image: docker:24
  rules:
    - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH
      changes:
        - Dockerfile
        - "**/*.java"
  script:
    - docker build -t myapp .
```

Example: deploy only when someone clicks “Run” in the UI:

```yaml
deploy-staging:
  stage: deploy
  script:
    - echo "Would deploy to staging..."
  when: manual
```

- **`when: manual`** — Job is not run automatically; it appears in the pipeline with a “Play” button. The pipeline can be configured to be “blocked” until this job runs, or the job can be optional (`allow_failure: true`).

### Variables and secrets

- **`variables`** in `.gitlab-ci.yml` — Non-sensitive config (e.g., `MAVEN_OPTS`).
- **CI/CD variables** (Settings → CI/CD → Variables) — For secrets (tokens, passwords). Mark as “masked” and “protected” as needed. In the job they appear as environment variables.

Example:

```yaml
push-image:
  script:
    - echo $CI_REGISTRY_PASSWORD | docker login -u $CI_REGISTRY_USER --password-stdin $CI_REGISTRY
    - docker push $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA
```

`CI_REGISTRY_*` and `CI_REGISTRY_IMAGE` are predefined by GitLab when the runner is configured for the registry. For other registries (e.g., Docker Hub), you’d define variables in the UI and reference them in the job.

### Artifacts and passing work between jobs

Jobs can produce **artifacts** (files/directories). Later jobs in the same pipeline can download them.

```yaml
build:
  stage: build
  script:
    - mvn package -DskipTests
  artifacts:
    paths:
      - target/*.jar
    expire_in: 1 day

test:
  stage: test
  script:
    - java -jar target/*.jar --version
  dependencies:
    - build
```

- **`artifacts.paths`** — What to save.
- **`dependencies`** — Which jobs’ artifacts to download (default is all from earlier stages).

---

## Example: End-to-End Pipeline Sketch

This sketch shows the *structure* of a pipeline: stages, using different images, rules, `needs`, and a manual job. It does **not** show the specific tools or commands you will implement; the [assignments](assignments.md) walk you through building each job yourself.

```yaml
stages:
  - validate
  - build
  - scan
  - deploy

# Example: a job that runs in a custom image, only on merge requests
run-checks:
  stage: validate
  image: some-tool-image:latest
  script:
    - echo "Run your validation here (e.g. lint, secret scan)."
  rules:
    - if: $CI_PIPELINE_SOURCE == "merge_request_event"

# Example: a job that produces something the next stage needs
build-artifact:
  stage: build
  image: docker:24
  services:
    - docker:24-dind
  script:
    - echo "Build and publish your artifact here."
  rules:
    - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH

# Example: a job that runs only after build-artifact (needs)
security-scan:
  stage: scan
  image: some-scan-image:latest
  script:
    - echo "Scan the artifact from the previous stage."
  needs:
    - build-artifact
  rules:
    - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH

# Example: manual job — does not run unless you click "Play" in the UI
release:
  stage: deploy
  script:
    - echo "Deploy only when triggered manually."
  when: manual
  rules:
    - if: $CI_COMMIT_BRANCH == $CI_DEFAULT_BRANCH
```

- **Stages** run in order; jobs in the same stage can run in parallel.
- **`image`** and **`services`** define the environment for each job.
- **`rules`** control when a job is included (e.g. only on default branch or only on merge requests).
- **`needs`** makes a job wait for specific previous jobs instead of the whole stage.
- **`when: manual`** means the job appears in the pipeline but runs only when a user clicks “Play” in the GitLab UI.

---

## Summary

- **CI/CD** automates build, test, scan, and deploy so you get fast feedback and repeatable releases.
- **Pipelines** are made of **stages** and **jobs**; **runners** execute jobs; **artifacts** pass outputs between jobs; **rules** and **when: manual** control when jobs run.
- In **GitLab**, you configure this in **`.gitlab-ci.yml`** using **stages**, **image**, **script**, **rules**, **artifacts**, and **when: manual** for manual deploy steps.
