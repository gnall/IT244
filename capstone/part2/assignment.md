# Capstone — Part 2 (Weighted 2× a normal assignment)

## Goal
Package and deploy your containerized REST API (from Part 1) to a local Kubernetes cluster using **Helm** and **Minikube**.

This part ties together concepts from earlier in the semester: **Kubernetes**, **Helm**, **containers/registries**, and **CI/CD artifacts**.

## What you must deliver (requirements)

### Task 1 — Create a Helm chart
Create a Helm chart in the repository you used for part1 (capstone-assignment) that deploys your application image from the GitLab Container Registry.

Your chart must include, at minimum:
- A `Deployment` that runs your app container.
- A `Service` that exposes your app inside the cluster.
- Configuration via `values.yaml` for:
  - Image repository (registry path)
  - Image tag
  - Image pull policy
  - Service type/ports

### Task 2 — Publish the Helm chart to the GitLab registry (CI/CD)
Create a new GitLab CI/CD job that:
- **Packages** your Helm chart.
- **Publishes** the packaged chart to the GitLab registry for the `capstone-assignment` project.

Requirements:
- The job must run in GitLab CI (defined in `.gitlab-ci.yml`).
- The chart must be retrievable from the registry after the pipeline completes.
- The job must authenticate using GitLab CI variables / built-in variables (no hard-coded credentials).

### Task 3 — Pull the image from the GitLab Container Registry
Your Helm chart must be able to pull the image you published in Part 1.

If your GitLab registry project is private (typical), Kubernetes will need credentials. You will do this by creating a Kubernetes **imagePullSecret** and having your Helm chart reference it.

#### Create GitLab registry credentials (do this once)
Use **one** of these approaches:
- **Project Access Token (recommended)**: Create a Project Access Token with `read_registry`.
- **Personal Access Token**: Create a PAT with `read_registry`.

You will need:
- **Registry server**: `registry.gitlab.com`
- **Username**: gitlab-token if using project access tokens (or your GitLab username for a personal access token)
- **Password**: project access token value (or the personal access token value)

#### Create the Kubernetes image pull secret
Choose the namespace you will deploy into (example below uses `default`).

1. Create a docker-registry secret:

```bash
kubectl create secret docker-registry gitlab-registry \
  --docker-server="registry.gitlab.com" \
  --docker-username="<YOUR_USERNAME>" \
  --docker-password="<YOUR_PASSWORD_OR_TOKEN>" \
  --docker-email="<YOUR_EMAIL>" \
  --namespace default
```

2. Verify the secret exists:

```bash
kubectl get secret gitlab-registry --namespace default
```

#### Reference the secret from your Helm chart
In your chart values, represent the image pull secret as a list. Example expectation:
- Your chart templates must add `imagePullSecrets` to the Pod spec when a value is set.
- Your `values.yaml` must allow setting the secret name (for example: `gitlab-registry`).

### Task 4 — Deploy to Minikube
Deploy your application to a **local Minikube cluster** by installing the Helm chart **directly from the GitLab registry** (the packaged chart you published in Task 3), not from a local chart directory.

You can run
`helm registry login registry.gitlab.com -u "gitlab-token" -p "<Access_Token>"`
Then
`helm install capstone oci://registry.gitlab.com/university-of-scranton/computing-sciences/courses/it-244/Students/<Your_Gitlab_Group>/capstone-assignment/container_registry/<Name_Of_Helm_Chart_Artifact> --version <Version_In_Your_Registry>`

Requirements:
- Pods become `Running` and stay healthy.
- The application is reachable (you decide how: port-forward, NodePort, or other approach).

### Submission
- Submit **all code** by pushing it to GitLab under the project named **`capstone-assignment`**.
- Run `kubectl get pods -A`, then `kubectl describe` one of your pods installed with your helm chart, Submit a file called `part2-submission.txt` with the output

## Grading (Part 2)
For full credit:
- Your install the Helm chart directly from Gitlab Registry instead of locally. (Partial credit if you just install the local Helm chart)
- Your Helm chart pulls the image you built and pushed in Part 1 (GitLab Container Registry).
- The app deploys successfully to Minikube and is reachable.
- Your pipeline publishes your Helm chart to the GitLab registry.

Part 2 is graded separately and weighted **double** a normal assignment.
