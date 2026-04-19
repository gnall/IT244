# Terraform — Progressive Assignment (Minikube)

This assignment **introduces Terraform in small steps**. Complete each part **in order**. Each part should be **working** before you move on: you should be able to run `terraform validate` and `terraform plan` without errors (unless the instructions intentionally ask you to observe an error).

---

## How to submit

Push your Terraform project to a GitLab repository named **`terraform-assignment`**.

1. In your [IT 244 student group on GitLab](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students), create a new project named **`terraform-assignment`** (empty project / no template is fine).
2. On your computer, clone that project. You will add Terraform files at the **repository root** (same folder as `.git`).
3. After you finish each part (or when you complete everything), commit and push:

   ```bash
   git add .
   git commit -m "Describe which part you completed"
   git push origin main
   ```

**`kubectl` output files (required)**

Several parts ask you to prove your work with **`kubectl`**. For each row below, run the command in your terminal, copy **everything the command printed** (command line + output), and save it in a **plain-text file** at the **repository root** with the exact name shown. Use your real namespace name and deployment name where the placeholders say so.

| Submit as… | Run this command and capture the full output |
|------------|-----------------------------------------------|
| **`terraform-3.txt`** | `kubectl get namespace tf-it244-YOURIDENTIFIER` — after Part 3 apply, output should show your namespace row (Status **Active**). |
| **`terraform-4.txt`** | `kubectl -n tf-it244-YOURIDENTIFIER get deploy,svc,pods` — after Part 4 apply, should list Deployment, Service, and Pod(s). |
| **`terraform-5.txt`** | `kubectl -n tf-it244-YOURIDENTIFIER rollout status deployment/YOUR_DEPLOYMENT_NAME` — after Part 5 apply, should end with **successfully rolled out**. |
| **`terraform-6.txt`** | `kubectl get namespace tf-it244-YOURIDENTIFIER` — run **after** `terraform destroy` in Part 6; output should show **NotFound** / **Error from server (NotFound)** (that is expected and correct). |

**What must be in the repo for full credit**

| Path | Purpose |
|------|---------|
| `README.md` | Your name or identifier, how to run Minikube, how to run `terraform init/plan/apply`, and any assumptions (OS, Terraform version). Narrative for each part (including Part 1–2 and Part 5’s `terraform plan` note) still belongs here unless the instructions say to use a `.txt` file. |
| `main.tf` | Main configuration (you may split into more `.tf` files if you prefer). |
| `versions.tf` (optional) | Some students put the `terraform {}` block here; either layout is acceptable if it is valid. |
| `terraform-3.txt` … `terraform-6.txt` | **`kubectl` proofs** for Parts 3–6 (see table above). |

**Instructor note:** If your course uses a different default branch name, push to that branch and mention it in `README.md`.

---

## Setup (do this before Part 1)

### A. Tools on your workstation

Install the following and confirm they run:

| Tool | Why you need it |
|------|-----------------|
| **Terraform CLI** (1.6+ recommended) | The assignment is Terraform-driven. Official install: [Install Terraform](https://developer.hashicorp.com/terraform/install). |
| **Docker** (or another Minikube driver you use) | Minikube normally uses a driver to run the local cluster. |
| **Minikube** | Provides a local Kubernetes API for Terraform to talk to. |
| **`kubectl`** | Verifies the cluster and helps you debug. |

**Sanity checks:**

```bash
terraform version
minikube version
kubectl version --client
```

Start Minikube (if it is not already running):

```bash
minikube start
kubectl get nodes
```

You should see a **Ready** node.

### B. Kubeconfig context

Terraform’s Kubernetes provider will use your **kubeconfig** (default `~/.kube/config`) and the **current context**. Verify:

```bash
kubectl config current-context
```

It should point at your **Minikube** context (often named `minikube`). If not:

```bash
kubectl config use-context minikube
```

### C. GitLab project

Create **`terraform-assignment`** in the student GitLab group, clone it empty, and keep all assignment work in that repository.

### D. How you will work in each part

From your repo root (where `.tf` files live):

```bash
terraform fmt
terraform init
terraform validate
terraform plan
# When you intend to make real changes:
terraform apply
```

Read **`terraform plan`** output carefully before **`apply`**. For learning, **`apply`** is encouraged when the plan matches your intent. Use **`terraform destroy`** when you are done for the day if you want a clean cluster—**or** keep resources and document what exists; just be consistent in your `README.md`.

---

## Part 0: Repository scaffold (no Terraform yet)

**Goal:** Ensure GitLab and local clone work.

**Tasks:**

1. Create **`terraform-assignment`** on GitLab and clone it.
2. Add a **`README.md`** with:
   - Course identifier (IT 244), your name
   - Versions you installed for Terraform / Minikube / `kubectl` (paste output of `terraform version` and one line each for minikube/kubectl if you like)
3. Commit and push.

**Deliverable:** `README.md` is on the `main` branch in **`terraform-assignment`**.

---

## Part 1: `terraform` block + `terraform init`

**Goal:** Create a valid root module that pins Terraform and the Kubernetes provider **without** creating resources yet.

**Tasks:**

1. In the repo root, create a file (name suggestion: `main.tf` or `versions.tf`) containing:

   - A `terraform { ... }` block with:
     - `required_version` set to something compatible with your installed Terraform (for example `>= 1.6.0`—adjust if your machine has an older 1.x).
     - `required_providers` for **`hashicorp/kubernetes`** with version constraint `~> 2.23` (patch updates allowed; adjust minor if your `init` suggests a conflict—document what you chose in `README.md`).
2. Run:

   ```bash
   terraform fmt
   terraform init
   ```

**Success criteria:**

- `terraform init` completes and downloads the Kubernetes provider.
- No provider configuration is required yet for **init** to succeed (you will add `provider "kubernetes"` in Part 2).

**Deliverable:** committed `.tf` file(s) and a short note in `README.md` under a heading **Part 1** describing what `terraform init` does in your own words (3–5 sentences).

---

## Part 2: Configure the Kubernetes provider

**Goal:** Tell Terraform how to reach **Minikube** using your kubeconfig.

**Tasks:**

1. Add a `provider "kubernetes" { ... }` block with:

   ```hcl
   provider "kubernetes" {
     config_path = "~/.kube/config"
   }
   ```

   If your kubeconfig lives elsewhere, set `config_path` accordingly and **document** it in `README.md`.

2. Run:

   ```bash
   terraform fmt
   terraform init
   terraform validate
   terraform plan
   ```

**Expected result:** `plan` should report **no changes** (you have not declared resources yet). That is correct.

**Deliverable:** committed provider block; `README.md` **Part 2** lists the exact commands you ran and says what **no changes** means here.

---

## Part 3: First resource — a Namespace

**Goal:** Create a Kubernetes **Namespace** with a **unique name** (include your initials or username so it cannot collide with classmates if you ever share a cluster).

**Tasks:**

1. Add:

   ```hcl
   resource "kubernetes_namespace" "assignment" {
     metadata {
       name = "tf-it244-YOURIDENTIFIER"
     }
   }
   ```

   Replace `YOURIDENTIFIER` with something unique.

2. Run `terraform plan` and confirm Terraform proposes **one resource to add**.
3. Run `terraform apply` and approve.
4. Verify outside Terraform:

   ```bash
   kubectl get namespace tf-it244-YOURIDENTIFIER
   ```

**Deliverable:** committed configuration; save **`kubectl` output** as **`terraform-3.txt`** (see [kubectl output files](#how-to-submit) above). In `README.md` **Part 3**, name the namespace you used.

---

## Part 4: A Deployment + Service inside your Namespace

**Goal:** Manage a tiny **nginx** Deployment and a **ClusterIP** Service with Terraform in **your** namespace from Part 3.

**Tasks:**

1. Add Terraform resources (names are suggestions—you may rename, but stay consistent):

   - `kubernetes_deployment` running **`nginx:latest`** (or a specific patch tag you prefer—document it).
   - **1 replica**, standard labels on the pod template (`app = "tf-nginx"` or similar).
   - `kubernetes_service` type **`ClusterIP`** exposing port **80** targeting your pods.

   The Kubernetes provider’s `kubernetes_deployment` schema is **verbose** on purpose—it mirrors the Kubernetes API. You may start from this **reference** (adapt names, labels, and image tag as needed):

   ```hcl
   resource "kubernetes_deployment" "nginx" {
     metadata {
       name      = "nginx"
       namespace = kubernetes_namespace.assignment.metadata[0].name
       labels = {
         app = "tf-nginx"
       }
     }

     spec {
       replicas = 1

       selector {
         match_labels = {
           app = "tf-nginx"
         }
       }

       template {
         metadata {
           labels = {
             app = "tf-nginx"
           }
         }

         spec {
           container {
             name  = "nginx"
             image = "nginx:latest"

             port {
               container_port = 80
             }
           }
         }
       }
     }
   }

   resource "kubernetes_service" "nginx" {
     metadata {
       name      = "nginx"
       namespace = kubernetes_namespace.assignment.metadata[0].name
     }

     spec {
       selector = {
         app = "tf-nginx"
       }

       port {
         port        = 80
         target_port = 80
       }

       type = "ClusterIP"
     }
   }
   ```

2. Important: wire **namespace** correctly. Use **either**:

   - `namespace = kubernetes_namespace.assignment.metadata[0].name` on the Deployment and Service, **or**
   - embed `metadata { namespace = ... }` where the resource type supports it.

   Pick one approach and stick to it.

3. `terraform plan` → `terraform apply`.

4. Verify:

   ```bash
   kubectl -n tf-it244-YOURIDENTIFIER get deploy,svc,pods
   ```

**Deliverable:** committed `.tf`; save **`kubectl` output** as **`terraform-4.txt`**. In **Part 4** of `README.md`, note your Deployment name if it is not `nginx`.

---

## Part 5: Change something safely (rolling update)

**Goal:** Experience **plan → apply** for an **in-place update** (not destroy/recreate of the whole stack).

**Tasks:**

1. Change the Deployment’s **pod label** or **annotation** (small, harmless change), or bump the **image tag** from `nginx:latest` to a **pinned** version like `nginx:1.25` (document which you chose).
2. Run `terraform plan` and read whether Terraform proposes **update in-place** vs **replace**.
3. `terraform apply`.

4. Show rollout status:

   ```bash
   kubectl -n tf-it244-YOURIDENTIFIER rollout status deployment/YOUR_DEPLOYMENT_NAME
   ```

**Deliverable:** save **`kubectl rollout status`** output as **`terraform-5.txt`**. In `README.md` **Part 5**, explain **what** you changed and paste **one** `terraform plan` snippet line showing the update (or summarize the plan in your own words if the paste is huge).

---

## Part 6: Tear-down with `terraform destroy`

**Goal:** Practice **controlled** removal and confirm state is empty afterward.

**Tasks:**

1. From the same directory, run:

   ```bash
   terraform destroy
   ```

2. Confirm Kubernetes objects are gone:

   ```bash
   kubectl get namespace tf-it244-YOURIDENTIFIER
   ```

   You should see **NotFound** / **Error from server (NotFound)**—that is success **after destroy**.

**Deliverable:** save **`kubectl get namespace …`** output (NotFound) as **`terraform-6.txt`**. In `README.md` **Part 6**, write **one lesson** you learned from reading the destroy plan (complete sentence) and note that the namespace no longer exists.

**Optional (recommended) after grading:** If you still want the repo to be “runnable,” you can re-`apply` once after destroy and push a final “restored” state—or keep the repo ending at destroy and say so in the README. Pick one and be explicit.

---

## Requirements checklist (grading-oriented)

- **GitLab project name** is exactly **`terraform-assignment`**.
- **`terraform-3.txt`–`terraform-6.txt`** at repo root contain the **`kubectl`** captures described in the [table](#how-to-submit) (correct commands for your namespace and deployment name).
- **`README.md`** documents setup, unique namespace naming, and narrative for each part (including Part 5 plan note and Part 6 lesson).
- **Terraform** files are valid: another student could run `terraform init && terraform validate` on a fresh clone (they would still need their own Minikube and kubeconfig).
- **No secrets committed** (no cloud access keys, no kubeconfig file contents pasted into `.tf` files).
- **Parts completed in order** (your Git commit history is a reasonable proof).

---

[← Back to Terraform module](README.md)
