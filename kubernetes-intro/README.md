# Introduction to Kubernetes and Minikube

- [kubectl quick reference](quick-ref.md)
- [Example manifests](examples/) (reference YAML for assignments)
- [Assignments](assignments.md) — submit `.txt` deliverables to GitLab project **`kubernetes-intro-assignment`**

## What is Kubernetes?

**Kubernetes** (often abbreviated **K8s**) is an open-source **container orchestration** platform. It automates deploying, scaling, and operating application containers across a cluster of machines. Google donated the project to the Cloud Native Computing Foundation (CNCF); it is now the de facto standard for running containerized workloads in production.

If **Docker** answers “how do I package and run *one* container?”, Kubernetes answers “how do I run *many* containers reliably across *many* machines, restart them when they fail, route traffic to them, and roll out updates safely?”

---

## Why use Kubernetes? Purpose and benefits

### Purpose

- **Declarative operations** — You describe *desired state* (e.g. “I want three replicas of this app”) in YAML; Kubernetes works to make the cluster match that state.
- **High availability** — If a node or container dies, the scheduler can start replacements elsewhere (within limits of your cluster).
- **Scalability** — Scale deployments up or down horizontally (more Pods) or integrate with cluster autoscalers in the cloud.
- **Service discovery and load balancing** — **Services** give your workloads stable names and IPs (or DNS) so other parts of the system can find them without hard-coding instance addresses.
- **Rolling updates and rollbacks** — Change container images or configuration with controlled rollouts and the ability to roll back to a previous revision.
- **Portable** — The same API and resource model work on your laptop (Minikube), in a data center, and on major cloud providers (EKS, AKS, GKE, etc.).

### Benefits (summary)

| Benefit | What it means for you |
|--------|------------------------|
| **Automation** | Less manual SSH, fewer ad-hoc scripts to restart failed processes. |
| **Consistency** | Same deployment model from dev-like clusters to production. |
| **Efficiency** | Pack many workloads onto shared nodes; bin-pack containers with limits/requests. |
| **Ecosystem** | Rich tooling: monitoring, ingress controllers, service meshes, GitOps operators. |

Kubernetes adds **complexity**; it is not always the right choice for a single small app. It shines when you need resilience, scale, and many moving parts—exactly the “integrative” environments this course discusses.

---

## Core architecture (how a cluster is built)

At a high level:

- **Cluster** — One or more **worker nodes** (machines that run your workloads) plus **control plane** components (on managed clouds, the control plane is often hidden from you).

**Control plane** (brains of the cluster):

| Component | Role |
|-----------|------|
| **kube-apiserver** | Exposes the Kubernetes API; all `kubectl` and controllers talk here. |
| **etcd** | Distributed key-value store for cluster state (what should exist, what is running). |
| **kube-scheduler** | Assigns new Pods to nodes based on resources and rules. |
| **kube-controller-manager** | Runs controllers (e.g. ReplicaSet—maintain desired replica count). |
| **cloud-controller-manager** | Integrates with cloud providers (load balancers, routes) when applicable. |

**Worker node:**

| Component | Role |
|-----------|------|
| **kubelet** | Agent that ensures containers in Pods run on this node as instructed. |
| **kube-proxy** | Network rules so Services can reach Pod endpoints. |
| **Container runtime** | e.g. **containerd** — actually starts/stops containers (Kubernetes uses the CRI). |

**Minikube** runs a **single-node** cluster on your laptop: the control plane and your workloads share one VM or container host. That is enough to learn almost all core concepts.

---

## Core concepts (objects you will use every day)

### Namespace

A **Namespace** partitions resources inside a cluster (e.g. `default`, `kube-system`). Names of resources need only be unique *within* a namespace. Use namespaces to separate teams or environments on the same cluster.

### Pod

A **Pod** is the smallest deployable unit. It usually runs **one** main container (sometimes a **sidecar** for logging or proxying). Pods share network namespace (localhost between containers in the same Pod) and optional storage volumes.

Pods are **ephemeral**—Kubernetes may delete and recreate them. You rarely create Pods directly in production; you use controllers like Deployments.

### ReplicaSet

A **ReplicaSet** keeps a stable set of replica Pods running. You almost always manage ReplicaSets indirectly through a **Deployment**.

### Deployment

A **Deployment** declares an application (container image, replica count, update strategy). It owns ReplicaSets and gives you **rolling updates** and **rollbacks**. This is the usual way to run stateless apps.

### Service

A **Service** is a stable **virtual IP** and **DNS name** (e.g. `my-service.default.svc.cluster.local`) that load-balances traffic to a set of Pods selected by **labels**. Types include:

- **ClusterIP** — Only inside the cluster (default).
- **NodePort** — Exposes a port on each node (handy with Minikube).
- **LoadBalancer** — Requests a cloud load balancer (cloud clusters).

### ConfigMap and Secret

- **ConfigMap** — Non-sensitive configuration (files, key-value data) injected as env vars or mounted files.
- **Secret** — Same idea for sensitive data (base64-encoded at rest; **not** encryption at rest by default—use external secret stores in production).

### Ingress (conceptual)

An **Ingress** defines HTTP/HTTPS routing from outside the cluster to Services (paths, hostnames). You need an **Ingress controller** installed (not included by default in every cluster). You may touch Ingress later; Minikube can enable one with an addon.

### Other names you will hear

- **StatefulSet** — For stateful apps with stable network identity and storage order.
- **DaemonSet** — Exactly one Pod per node (e.g. log collectors).
- **Job / CronJob** — Run-to-completion or scheduled tasks.
- **PersistentVolume (PV) / PersistentVolumeClaim (PVC)** — How Pods request durable storage.

---

## Ways people interact with Kubernetes

| Method | Description |
|--------|-------------|
| **kubectl** | Official CLI; apply YAML, get/describe/logs/exec. Most learning and day-to-day admin starts here. |
| **Kubernetes API** | REST API; used by tools, controllers, and custom automation. |
| **Web UI** | **Dashboard** (optional); cloud consoles (EKS, AKS, GKE) also expose UI. |
| **GitOps** | Tools like **Argo CD** or **Flux** continuously sync cluster state from Git—declarative, auditable. (You may see this after Helm in the course.) |
| **Client libraries** | Go, Python, etc. to build operators and integrations. |

For this course, **kubectl** + YAML manifests is the focus.

---

## Hosting Kubernetes in the cloud

Organizations usually run production clusters on a **managed** service so the provider operates the control plane and upgrades:

| Provider | Service | Notes |
|----------|---------|--------|
| **Amazon** | **Amazon EKS** | Integrated with AWS networking (VPC), IAM, load balancers. |
| **Microsoft** | **Azure AKS** | Integrates with Azure AD, Azure networking. |
| **Google** | **Google GKE** | Mature GKE Autopilot / Standard modes. |
| **Others** | **DigitalOcean Kubernetes**, **Linode LKE**, **Oracle OKE**, etc. | Often simpler, smaller scale. |

You can also install **self-managed** Kubernetes (e.g. **kubeadm**) on VMs—full control, more operational burden.

**Minikube** and **kind** (Kubernetes in Docker) are for **local development**, not production hosting.

---

## Declarative manifests (YAML)

Kubernetes expects resources defined in YAML (or JSON). Common fields:

- **`apiVersion`** — Which API group/version (e.g. `apps/v1` for Deployments).
- **`kind`** — Resource type (`Pod`, `Deployment`, `Service`, …).
- **`metadata`** — `name`, `namespace`, `labels`.
- **`spec`** — Desired configuration for that kind.

### Example: Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-demo
  labels:
    app: nginx-demo
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nginx-demo
  template:
    metadata:
      labels:
        app: nginx-demo
    spec:
      containers:
        - name: nginx
          image: nginx:1.25-alpine
          ports:
            - containerPort: 80
```

### Example: Service (ClusterIP)

```yaml
apiVersion: v1
kind: Service
metadata:
  name: nginx-demo
spec:
  selector:
    app: nginx-demo
  ports:
    - port: 80
      targetPort: 80
  type: ClusterIP
```

### Example: ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  APP_ENV: "training"
  LOG_LEVEL: "info"
```

Files in the [examples](examples/) folder mirror simple cases you can use in assignments.

---

## Labels and selectors

**Labels** are key-value pairs on objects (`app: my-api`, `tier: frontend`). **Selectors** tie objects together: a Service’s `selector` must match Pod labels to send traffic there. Consistent labeling is essential for clean operations.

---

## Useful mental model

1. **Deployment** → ensures N copies of your **Pod template** run.
2. **Service** → stable entry to those Pods by label.
3. **ConfigMap / Secret** → configuration without baking everything into the image.

---

## Minikube and this course

**Minikube** runs a local Kubernetes cluster. Complete **[Assignment 0](assignments.md#assignment-0-install-and-start-minikube)** before the rest. Later weeks may use the same cluster for **Helm** and **Terraform**.

---

## Additional resources

- [Kubernetes official documentation](https://kubernetes.io/docs/home/)
- [Kubernetes concepts overview](https://kubernetes.io/docs/concepts/)
- [Minikube documentation](https://minikube.sigs.k8s.io/docs/)
- [kubectl cheat sheet](https://kubernetes.io/docs/reference/kubectl/quick-reference/) (also see our [quick reference](quick-ref.md))
