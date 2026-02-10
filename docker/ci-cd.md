# Docker in CI/CD Pipelines

Docker has become essential in modern DevOps practices, particularly in Continuous Integration and Continuous Deployment (CI/CD) pipelines.

### What is CI/CD?

**Continuous Integration (CI)**: Automatically building and testing code changes as developers commit them to version control.

**Continuous Deployment (CD)**: Automatically deploying tested code changes to production.

### How Docker Fits Into CI/CD

#### 1. Consistent Build Environments

**Problem**: "It builds on my machine but fails in CI."

**Docker Solution**: The CI server builds inside the same container developers use locally.

```yaml
# Example: GitHub Actions workflow
name: CI Pipeline
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Build Docker image
        run: docker build -t myapp:latest .
      - name: Run tests in container
        run: docker run myapp:latest npm test
```

#### 2. Parallel Testing

Run multiple test suites simultaneously in separate containers:

```yaml
strategy:
  matrix:
    node-version: [14, 16, 18, 20]
steps:
  - name: Test on Node ${{ matrix.node-version }}
    run: docker run node:${{ matrix.node-version }} npm test
```

#### 3. Artifact Generation

Build once, deploy anywhere—the Docker image becomes your deployable artifact:

```bash
# Build stage
docker build -t myapp:$VERSION .

# Test stage
docker run myapp:$VERSION npm test

# Push to registry
docker push registry.example.com/myapp:$VERSION

# Deploy stage
docker pull registry.example.com/myapp:$VERSION
docker run -d registry.example.com/myapp:$VERSION
```

### Common CI/CD Pipeline Pattern

```
1. Developer pushes code
   ↓
2. CI server detects change
   ↓
3. Build Docker image
   ↓
4. Run automated tests in container
   ↓
5. Run security scans
   ↓
6. Push image to registry (if tests pass)
   ↓
7. Deploy to staging environment
   ↓
8. Run integration tests
   ↓
9. Deploy to production (manual approval or automatic)
```

### Popular CI/CD Tools with Docker

1. **GitHub Actions**
   ```yaml
   - name: Build and push
     uses: docker/build-push-action@v3
     with:
       push: true
       tags: user/app:latest
   ```

2. **GitLab CI/CD**
   ```yaml
   build:
     image: docker:latest
     script:
       - docker build -t myapp .
   ```

3. **Jenkins**
   ```groovy
   pipeline {
     agent { docker { image 'node:18' } }
     stages {
       stage('Build') {
         steps {
           sh 'npm install'
         }
       }
     }
   }
   ```

4. **CircleCI**
   ```yaml
   jobs:
     build:
       docker:
         - image: cimg/node:18.0
   ```

### Best Practices for Docker in CI/CD

1. **Tag images with commit SHA**: `myapp:$GIT_COMMIT_SHA`
2. **Use multi-stage builds**: Separate build and runtime dependencies
3. **Cache layers**: Speed up builds by caching unchanged layers
4. **Scan for vulnerabilities**: Use tools like Trivy or Snyk
5. **Keep images small**: Reduces transfer time and attack surface
6. **Use specific image tags**: Avoid `latest` in production
7. **Implement health checks**: Ensure containers are truly ready

### Example: Complete CI/CD Workflow

```yaml
name: Complete CI/CD Pipeline

on:
  push:
    branches: [main]

env:
  REGISTRY: ghcr.io
  IMAGE_NAME: ${{ github.repository }}

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Build Docker image
        run: docker build -t $IMAGE_NAME:test .

      - name: Run unit tests
        run: docker run $IMAGE_NAME:test npm test

      - name: Run security scan
        uses: aquasecurity/trivy-action@master
        with:
          image-ref: $IMAGE_NAME:test

  push-to-registry:
    needs: build-and-test
    runs-on: ubuntu-latest
    steps:
      - name: Log in to registry
        run: echo ${{ secrets.GITHUB_TOKEN }} | docker login $REGISTRY -u ${{ github.actor }} --password-stdin

      - name: Build and push
        run: |
          docker build -t $REGISTRY/$IMAGE_NAME:${{ github.sha }} .
          docker push $REGISTRY/$IMAGE_NAME:${{ github.sha }}

  deploy:
    needs: push-to-registry
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to production
        run: |
          ssh user@production-server "
            docker pull $REGISTRY/$IMAGE_NAME:${{ github.sha }}
            docker stop myapp || true
            docker run -d --name myapp $REGISTRY/$IMAGE_NAME:${{ github.sha }}
          "
```
