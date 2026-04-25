# Capstone — Part 1 (Weighted 2× a normal assignment)

## Goal
Take an existing working Java REST API, containerize it, and build a GitLab CI/CD pipeline that scans, lints, and publishes the image to the GitLab Container Registry.

This part ties together concepts from earlier in the semester: **Git**, **Docker**, **CI/CD**, **security scanning**, and **APIs (Java)**.

## What you are given
- A working Java REST API example application in `example/`.
- The application intentionally contains **at least one style/lint issue** so your lint job produces findings.

## What you must deliver (requirements)

### Task 1 — Verify the example app works (baseline)
- The app must run locally from source.
- You must be able to hit at least one HTTP endpoint and get a successful response.

### Task 2 — Dockerize the example app
Create a `Dockerfile` (and any supporting files like `.dockerignore`) so that:
- The image builds successfully.
- A container started from the image runs the REST API.
- The app listens on a well-defined port and is reachable from the host.
- The build is **repeatable** (no manual steps during image build).

### Task 3 — Create a GitLab CI/CD pipeline
Create a `.gitlab-ci.yml` at the repo root that defines a pipeline with **at least** the following jobs.

#### Job A — Secret scanning (repository)
Your pipeline must include a job that performs **secret scanning** on the repository content.
- The job must run in CI.
- If secrets are found, the job should fail (quality gate).

You may use GitLab’s built-in capabilities (Security templates) or an industry tool (e.g., gitleaks) as long as it runs in CI and meets the requirements above.

#### Job B — Java linting (style/static analysis)
Your pipeline must include a job that performs **Java linting** / static style analysis against `Capstone/part1/example/`.
- The job must run in CI.
- The job must detect at least one issue (the example contains an intentional issue).
- The job must produce output in the job logs.

Tool choice is up to you (I suggest: `mvn checkstyle:check`), but it must be runnable as part of a GitLab CI job.

#### Job C — Build a Docker image in CI
Your pipeline must include a job that builds the Docker image for the example app in CI.
- The job must build from your `Dockerfile`.
- The job must tag the image appropriately for the GitLab Container Registry.

#### Job D — Push the Docker image to the GitLab Container Registry
Your pipeline must include a job that pushes the built image to the **GitLab Container Registry** for your project.
- The pushed image must be retrievable later by name/tag.
- The pipeline must authenticate to the registry using GitLab CI variables / built-in variables.

### Submission
- Submit **all code** by pushing it to GitLab under the project named **`capstone-assignment`**.

## Grading (Part 1)
For full credit:
- Your `.gitlab-ci.yml` contains all required, functioning jobs (secret scanning, Java linting, image build, image push).
- The Docker image runs the Java REST API correctly.
- The published image exists in the GitLab Container Registry and can be referenced later.

Part 1 is graded separately and weighted **double** a normal assignment.

