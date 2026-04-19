# Terraform — Infrastructure as Code

- [Terraform CLI quick reference](quick-ref.md)
- [Assignments](assignments.md) — submit your work to the GitLab project **`terraform-assignment`**

This document is **course reading material**. It explains what Terraform is, why teams use it, how its core ideas fit together, and how those ideas connect to the rest of your IT 244 topics (containers, Kubernetes, CI/CD, and cloud APIs).

---

## 1. What is Terraform?

**Terraform** is an open-source tool from **HashiCorp** for **Infrastructure as Code (IaC)**. You write **declarative configuration** (usually in **HCL**—HashiCorp Configuration Language) that describes **what infrastructure should exist**. Terraform then compares that description to **what it believes is already deployed** and produces a **plan** of create/update/delete actions. When you approve, it **applies** those changes through **provider plugins** that call real APIs (cloud APIs, Kubernetes API, DNS providers, and many others).

In one sentence: **Terraform is a stateful engine that reconciles a desired configuration with reality using provider APIs.**

Terraform is **not** a container runtime (that is Docker), **not** a cluster orchestrator (that is Kubernetes), and **not** a pipeline runner (that is GitLab CI, Jenkins, and similar). It **complements** those tools by **provisioning and wiring** the resources they depend on: networks, clusters, databases, IAM roles, DNS records, namespaces, and more.

---

## 2. Why “infrastructure as code” matters

Before IaC, teams often built environments by **clicking in consoles** or running **one-off scripts**. That approach is hard to **repeat**, hard to **review**, and hard to **audit**. IaC treats infrastructure definition like application source code:

| Idea | What it gives you |
|------|-------------------|
| **Version control** | Every change is a commit; you can diff, tag releases, and roll back configuration. |
| **Review** | Pull requests let peers check security, naming, regions, and cost implications before apply. |
| **Consistency** | Dev, staging, and prod can share the same modules with different parameters. |
| **Automation** | CI/CD or scheduled jobs can run `terraform plan` and (with care) `terraform apply`. |

Terraform is one popular IaC option. Others you may hear about include **AWS CloudFormation**, **Azure Bicep/ARM**, **Pulumi** (general-purpose languages), and **Ansible** (often described as *desired-state* automation; overlap with IaC but different execution model). This course uses Terraform because it is **multi-cloud**, has a **large provider ecosystem**, and appears frequently in **platform** and **DevOps** roles.

---

## 3. What problem Terraform is trying to solve

Real integration work rarely stops at “my app runs locally.” You need:

- **Accounts and permissions** so automation and humans can act safely.
- **Networks** so services can talk to each other without exposing everything to the internet.
- **Compute** (VMs, Kubernetes clusters, serverless) to run workloads.
- **Data stores** (managed databases, caches, object storage).
- **Observability** (log sinks, metrics, alerts).

All of these are **API-driven**. Terraform’s job is to let you **declare** those resources in files, **collaborate** on them, and **apply** them in a controlled way—while keeping a clear record of **what Terraform created** so future runs stay accurate.

---

## 4. Core concepts (mental model)

### 4.1 Configuration (`.tf` files)

You organize **`.tf`** files in a **working directory** (often called a **root module**). Terraform loads all `.tf` files in that directory together as one configuration. Common filenames (`main.tf`, `variables.tf`, `outputs.tf`) are conventions only; Terraform does not care about the names.

### 4.2 Providers

A **provider** is a plugin that implements **resource types** for a platform—for example `aws_instance`, `google_compute_network`, or `kubernetes_namespace`. You configure a **provider block** so Terraform knows **how to authenticate** and **which endpoint** to use (for example, which AWS region or which kubeconfig context).

Providers are distributed from the **Terraform Registry**. In your configuration you declare **required providers** so `terraform init` downloads the correct plugins.

### 4.3 Resources

A **resource** is a managed object with a **type** and a **local name**:

```hcl
resource "kubernetes_namespace" "app" {
  metadata {
    name = "my-app"
  }
}
```

Here the type is `kubernetes_namespace` and the local name is `app`. Terraform addresses this object as `kubernetes_namespace.app`.

**Declarative** means you describe the **desired end state**. You generally do not say “run these 47 imperative steps”; you say “this namespace should exist with these labels,” and Terraform figures out the API calls.

### 4.4 State

Terraform keeps a **state file** that maps your configuration’s resources to **real-world IDs**. On the next run, Terraform reads state, **refreshes** current attributes from the APIs when possible, and computes **drift** (manual changes outside Terraform) versus your configuration.

**State is sensitive** (it may contain secrets or IDs) and **precious** (losing it without recourse can make management painful). For solo learning on a laptop, **local state** (a `terraform.tfstate` file) is fine. On teams, state is usually stored **remotely** (e.g. S3 + DynamoDB locking, Terraform Cloud, GCS backend) with locking so two people do not apply at once.

You will use **local state** in this course’s assignment unless your instructor specifies otherwise.

### 4.5 Plan and apply

The everyday workflow:

1. **Write** or change `.tf` files.
2. **`terraform fmt`** — format files to canonical style (kindness to reviewers).
3. **`terraform validate`** — quick static check of configuration consistency.
4. **`terraform plan`** — show what would change **without** changing it.
5. **`terraform apply`** — show the same plan, then execute after confirmation (unless using `-auto-approve` in automation).

**`plan`** is your safety net: read it carefully. It summarizes **adds**, **changes in-place**, and **destroys**. Some attribute changes force **replace** (destroy + recreate), which can cause brief outages for stateful systems—another reason plan review matters.

### 4.6 Destroy

**`terraform destroy`** removes resources that are tracked in state **in dependency order**. It is useful for labs and for tearing down sandboxes. **It is dangerous** if pointed at production. Always confirm **workspace**, **provider credentials**, and **state backend** before destroy.

---

## 5. HCL basics (just enough to read and write simple configs)

### 5.1 Blocks

Configurations are built from **blocks**:

- `terraform { ... }` — settings for Terraform itself (required version, backend, required providers).
- `provider "name" { ... }` — plugin configuration.
- `resource "type" "name" { ... }` — desired infrastructure object.
- `variable "name" { ... }` — input parameter.
- `output "name" { ... }` — exported value after apply.
- `module "name" { ... }` — reuse another directory of configuration as a child module.

### 5.2 Arguments and expressions

Inside a block you set **arguments**:

```hcl
variable "namespace_name" {
  type        = string
  description = "Kubernetes namespace for the demo app"
  default     = "tf-demo"
}
```

You can reference values with dots: `var.namespace_name`, `kubernetes_namespace.app.metadata[0].name`, etc.

### 5.3 Types and constraints

Using **types** and **descriptions** on variables makes modules safer and easier for teammates to use. `terraform validate` catches some mistakes early; the rest surface during `plan`/`apply` when the provider validates API payloads.

---

## 6. How Terraform fits with Docker, Kubernetes, Helm, and CI/CD

Think in **layers**:

- **Docker** packages **processes** as images.
- **Kubernetes** schedules and runs **workloads** from YAML (or from objects created by controllers).
- **Helm** **templates** Kubernetes YAML and manages **releases** as a package.
- **Terraform** can **create the cluster**, **VPC**, **node pools**, **IAM**, and also **apply Kubernetes objects** via the **Kubernetes provider**—or install **Helm releases** via the **Helm provider**.

**CI/CD** often runs `terraform plan` on every merge request and restricted `apply` from a protected branch or manual job. That mirrors how you treat application builds: **automate verification**, **gate production changes**.

For **this course’s Terraform assignment**, you will target **Minikube** on your laptop. Minikube already gives you a Kubernetes API endpoint; Terraform uses your **kubeconfig** (same as `kubectl`) to create **namespaces** and simple **workloads**. That keeps cost at zero while still teaching the real workflow: **init → plan → apply → destroy**.

---

## 7. Use cases where Terraform shines

These are typical situations where teams reach for Terraform (or a similar IaC tool):

1. **Reproducible environments** — Spin up a full “ephemeral” dev stack, run tests, tear it down with `destroy`.
2. **Multi-region or multi-cloud footguns reduction** — One workflow language across vendors; still not magic—you must understand each cloud’s primitives.
3. **Platform engineering** — Golden paths: approved modules for “standard VPC,” “standard EKS,” “standard Postgres,” so product teams do not each reinvent networking.
4. **Least-privilege IAM** — Define roles and policies as code, attach to CI principals, review in Git.
5. **Kubernetes bootstrap** — Namespaces, quotas, RBAC, CRDs, Argo CD, ingress controllers—especially when GitOps is not yet adopted or is mixed with Terraform.
6. **DNS and certificates** — Route53 records + ACM validation is a classic Terraform pairing.
7. **Cost and inventory visibility** — Tags and labels applied consistently via modules make chargeback easier.

---

## 8. When Terraform is helpful vs when to be cautious

**Helpful when:**

- You have **many** resources or **frequent** environment churn.
- You want **reviewable** infrastructure changes and **repeatable** applies.
- You need a **single tool** that can touch **cloud + SaaS + Kubernetes** APIs.

**Use caution when:**

- **Stateful one-off data** — Accidental destroy can delete databases. Protect with `lifecycle { prevent_destroy = true }` where appropriate, backups, and separate workspaces.
- **Drift** — Console edits outside Terraform confuse state; establish team rules: “if it exists in prod, it lives in Terraform.”
- **Secrets in plain text** — Do not commit long-lived secrets in `.tf` files. Prefer environment variables, secret managers, or CI-injected variables.
- **Highly dynamic app config** — Application feature flags and day-to-day rollouts often belong in CI/CD or GitOps, not always in Terraform. Boundaries vary by organization.

---

## 9. A minimal conceptual example (read-only illustration)

The following example is **illustrative** (it is not the full assignment). It shows the shape of a configuration that would manage an S3 bucket in AWS:

```hcl
terraform {
  required_version = ">= 1.6.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "course_demo" {
  bucket = "it244-demo-YOUR_UNIQUE_SUFFIX"
}

resource "aws_s3_bucket_versioning" "course_demo" {
  bucket = aws_s3_bucket.course_demo.id
  versioning_configuration {
    status = "Enabled"
  }
}
```

**What happens when you run commands:**

- **`terraform init`** downloads the AWS provider and prepares the backend.
- **`terraform plan`** might say: create bucket, create versioning resource.
- **`terraform apply`** executes those creates through AWS APIs and records IDs in **state**.
- If you later change `versioning_configuration` and plan again, Terraform proposes an **update** to the versioning resource rather than recreating the bucket—depending on provider behavior and which arguments changed.

Your **assignment** uses the **Kubernetes** provider against **Minikube**, but the **plan/apply/state** rhythm is the same.

---

## 10. Modules, workspaces, and scaling up (preview)

**Modules** let you package reusable Terraform for others to call with `module "vpc" { source = "..." }`. **Workspaces** (or separate state files per environment) isolate prod from dev state. **Remote backends** enable collaboration. You are not required to master these in the first assignment, but you should recognize the names because job postings and real repos use them constantly.

---

## 11. Command cheat sheet (local learning)

Use the **[Terraform CLI quick reference](quick-ref.md)** for a full list of common commands (`init`, `plan`, `apply`, `destroy`, `state`, workspaces, `import`, `-target`, `-replace`, and more).

**Minimal loop:** `terraform fmt` → `terraform init` (once per clone / provider change) → `terraform validate` → `terraform plan` → `terraform apply`.

---

## 12. Further reading (official)

- [Terraform documentation](https://developer.hashicorp.com/terraform/docs)
- [Language: configuration syntax](https://developer.hashicorp.com/terraform/language/syntax)
- [Kubernetes provider](https://registry.terraform.io/providers/hashicorp/kubernetes/latest/docs)

When in doubt, prefer the **official provider docs** for argument names and examples; cloud APIs evolve quickly.

---

[← Back to course schedule](../README.md)
