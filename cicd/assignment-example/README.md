# CI/CD Assignment Starter Project

This is a **starter project** for the [CI/CD assignments](../assignments.md). Copy these files into your `cicd-assignment` GitLab project so you have a minimal Java app and Dockerfile ready. You will add and extend **`.gitlab-ci.yml`** in the assignments; this starter does not include a full pipeline.

## What's included

- **Maven project** — `pom.xml` with Java 17, a main class, and the Checkstyle plugin for linting.
- **Java app** — `src/main/java/com/example/App.java` (prints a greeting).
- **Dockerfile** — Multi-stage: build with Maven, run with Eclipse Temurin JRE.

## Quick start (local)

```bash
# Build and run tests
mvn verify

# Run the app
mvn exec:java -Dexec.mainClass="com.example.App"

# Build Docker image
docker build -t cicd-assignment-app .

# Run container
docker run --rm cicd-assignment-app
```

## Using this as your assignment repo

1. Create a new project **`cicd-assignment`** in your [student group](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students) on GitLab.
2. Copy the contents of this starter into that project (clone this repo or drag-and-drop these files into your empty `cicd-assignment` repo).
3. For **Assignment 1**, add **`.gitlab-ci.yml`** at the root and push to `main`.
4. For **Assignments 2–5**, create a branch from `main`, add your pipeline changes, then open a merge request into `main` and merge it.

See the [CI/CD assignments](../assignments.md) for full instructions.
