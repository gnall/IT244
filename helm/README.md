# Helm — Package Manager for Kubernetes

- [Helm CLI quick reference](quick-ref.md)
- [Example chart](examples/minimal-web/) (minimal `Chart.yaml`, `values.yaml`, templates)
- [Assignments](assignments.md) — submit `.txt` deliverables to GitLab project **`helm-intro-assignment`**

## What is Helm?

**Helm** is the **package manager for Kubernetes**. It groups related Kubernetes manifests into a **Chart**—a versioned, parameterizable bundle you can install, upgrade, and remove as a **Release** with one command. Helm is a graduated CNCF project and is widely used to ship applications (databases, ingress controllers, monitoring stacks) and to template your own microservices.

Without Helm, you might maintain dozens of YAML files and manually edit image tags and replica counts in each. With Helm, you keep **templates** and drive differences through **`values.yaml`** (or `--set` flags).

---

## Why use Helm? Purpose and benefits

### Purpose

- **Package applications** — Distribute a complete app (Deployments, Services, ConfigMaps, RBAC, etc.) as one installable unit.
- **Parameterize configuration** — One chart for many environments; change behavior via values instead of copying YAML.
- **Lifecycle management** — **Install**, **upgrade**, **rollback**, and **uninstall** releases with predictable commands.
- **Share and reuse** — Publish charts to **Helm repositories** or **OCI registries**; consume charts others maintain (e.g. Bitnami, community charts).

### Benefits

| Benefit | What it means |
|--------|----------------|
| **Less duplication** | Template once; inject values for dev/staging/prod. |
| **Versioning** | Chart has a **chart version** (`Chart.yaml` `version`); track what was deployed. |
| **Rollbacks** | `helm rollback` returns to a previous release revision if an upgrade misbehaves. |
| **Discoverability** | `helm search` finds charts in configured repos. |
| **Testing before apply** | `helm template` and `helm install --dry-run` render Kubernetes YAML without applying it. |
| **Ecosystem** | Many vendors ship official charts; CI/CD often runs `helm upgrade --install`. |

Helm does **not** replace Kubernetes—it **generates** Kubernetes resources and submits them through the Kubernetes API (same as `kubectl apply`).

---

## Helm 2 vs Helm 3 (what you use today)

**Helm 3** removed **Tiller** (the in-cluster server from Helm 2). In Helm 3, the Helm CLI talks directly to the Kubernetes API using your **kubeconfig** (same as `kubectl`). Releases are stored as **Secrets** (default) or **ConfigMaps** in the release namespace.

This course uses **Helm 3** only.

---

## Core concepts

### Chart

A **Chart** is a directory (or packaged `.tgz`) that follows a layout Helm understands. At minimum:

| Path / file | Role |
|-------------|------|
| **`Chart.yaml`** | Metadata: chart name, `version` (chart version), `appVersion` (optional app version), `apiVersion: v2` for Helm 3. |
| **`values.yaml`** | Default configuration values; merged with user-supplied values at install/upgrade. |
| **`templates/`** | Go template files (`.yaml`, `.tpl`) that render to Kubernetes manifests when `helm install` / `helm template` runs. |
| **`charts/`** (optional) | **Subcharts**—dependencies bundled or referenced. |
| **`templates/_helpers.tpl`** (common) | Named template helpers (e.g. labels, fullnames). |

### Release

A **Release** is a **named installation** of a chart in a namespace, e.g. `helm install my-wordpress bitnami/wordpress` creates release **`my-wordpress`**. Each **upgrade** creates a new **revision** (1, 2, 3, …) so you can **rollback**.

### Repository

A **Helm repository** is an HTTP server exposing an **`index.yaml`** that lists chart packages. You **add** a repo URL, **update** to refresh the index, then **search** and **install** by `repo/chart` name.

Charts can also be stored as **OCI artifacts** in container registries (`helm push` / `helm install oci://...`)—common in modern pipelines.

### Values and templating

Templates use **Go templates** plus **Sprig** functions. Typical pattern:

```yaml
replicas: {{ .Values.replicaCount }}
image: {{ .Values.image.repository }}:{{ .Values.image.tag }}
```

At install time, Helm merges **default `values.yaml`** with **user values** (`-f myvalues.yaml`, `--set key=value`) and renders templates. The result is valid Kubernetes YAML sent to the cluster.

### Release name and namespace

- **Release name** must be unique per namespace (not globally across the cluster in all setups—namespace-scoped releases are the norm).
- **`--namespace`** (or `-n`) installs into a namespace; create it first if needed (`kubectl create ns training`).

---

## How people use Kubernetes with Helm

| Approach | Description |
|----------|-------------|
| **CLI (interactive / scripts)** | `helm install`, `helm upgrade`, `helm rollback` from a laptop or bastion—good for learning and small teams. |
| **CI/CD pipelines** | GitLab CI, GitHub Actions, Jenkins run `helm upgrade --install` with values from Git or secrets—repeatable deploys. |
| **GitOps** | Tools like **Flux** “HelmRelease” controllers or **Argo CD** sync Helm charts from Git or repos—cluster state tracks a repo. |
| **Application platforms** | Some internal platforms wrap Helm under a UI so developers never touch YAML. |

For this course, the **Helm CLI** against **Minikube** matches how many teams start before automating further.

---

## Example: `Chart.yaml`

```yaml
apiVersion: v2
name: my-app
description: A minimal example chart
type: application
version: 0.1.0      # chart version (SemVer)
appVersion: "1.0"   # optional: upstream app version string
```

## Example: `values.yaml` (fragment)

```yaml
replicaCount: 2

image:
  repository: nginx
  tag: "1.25-alpine"
  pullPolicy: IfNotPresent

service:
  type: ClusterIP
  port: 80
```

## Example: template snippet (`templates/deployment.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include "my-app.fullname" . }}
spec:
  replicas: {{ .Values.replicaCount }}
  template:
    spec:
      containers:
        - name: app
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
```

The [examples/minimal-web](examples/minimal-web/) chart in this repo is a **small working chart** (simplified helpers) you can `helm install` from a local path in assignments.

---

## Typical workflow (mental model)

1. **Find or author a chart** — Pull from a repo or use a folder on disk.
2. **Inspect** — `helm show chart`, `helm show values`, `helm template` to preview.
3. **Install** — `helm install RELEASE CHART` with optional `-f values.yaml` / `--set`.
4. **Operate** — `helm list`, `helm status`, `helm get values`.
5. **Change** — `helm upgrade RELEASE CHART` (same release name, new chart or values).
6. **Recover** — `helm rollback RELEASE REVISION` if needed.
7. **Remove** — `helm uninstall RELEASE`.

---

## Minikube and this course

Assume **Minikube** is running from the [Kubernetes intro](../kubernetes-intro/README.md) week. Install **Helm 3** on your machine (not usually inside the cluster). `helm` uses your current **kubectl context**—verify with `kubectl config current-context` before installing charts.

---

## Additional resources

- [Helm documentation](https://helm.sh/docs/)
- [Helm charts introduction](https://helm.sh/docs/topics/charts/)
- [Artifact Hub](https://artifacthub.io/) — search public charts
- [Bitnami Helm charts](https://github.com/bitnami/charts)
