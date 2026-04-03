# Kubernetes + Minikube — Assignments

Complete these in order. You need a working **Minikube** cluster first (**Assignment 0**). Use the course [README](README.md) for concepts and the [kubectl quick reference](quick-ref.md) for commands. **Helm** is covered in a later week; do not use Helm here.

## How to submit

Submit all work in a GitLab project named **`kubernetes-intro-assignment`**.

1. In your [IT 244 student group on GitLab](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students), open **your** subgroup or project list (same place as your other course repos).
2. Create a **new project** named exactly **`kubernetes-intro-assignment`** (or use it if I have already created it). Set visibility to **Private** unless you are told otherwise. You may initialize with a README.
3. **Clone** the project to your computer. You will add only **plain-text** deliverable files (`.txt`) at the **repository root**—one file per assignment as listed below (Assignment 4 uses two `.txt` files).
4. For each assignment, paste your terminal output and answers into the correct file, then:
   ```bash
   git add k8s-*.txt
   git commit -m "Add Kubernetes assignment outputs"
   git push origin main
   ```
   Use clear commit messages if you prefer to commit **one assignment at a time** (e.g. `git commit -m "Assignment 1: first contact"`).
5. Confirm on GitLab that the files appear on **`main`**.

**File naming (all `.txt` at repo root):**

| File | Assignment |
|------|------------|
| `k8s-0-setup.txt` | 0 |
| `k8s-1.txt` | 1 |
| `k8s-2.txt` | 2 |
| `k8s-3.txt` | 3 |
| `k8s-4-manifest.txt` | 4 (your Deployment YAML pasted as plain text) |
| `k8s-4.txt` | 4 (proof / `grep` output) |
| `k8s-5.txt` | 5 |

I will grade from this repository.

---

## Assignment 0: Install and start Minikube

**Goal:** Install Minikube and `kubectl`, start a local cluster, and prove it is healthy.

### macOS

1. **Recommended:** Read the official **Get Started** flow:
   - [Minikube: Start](https://minikube.sigs.k8s.io/docs/start/)
2. Install **kubectl** if you do not have it:
   - [Install kubectl on macOS](https://kubernetes.io/docs/tasks/tools/install-kubectl-macos/)
3. Install Minikube (Homebrew is common on Mac):
   - `brew install minikube`  
   - Or follow [Minikube installation](https://minikube.sigs.k8s.io/docs/start/) for your chip (Intel vs Apple Silicon).
4. Start the cluster:
   - `minikube start`
5. **Verify:**
   - `kubectl get nodes`
   - `kubectl cluster-info`

### Windows

1. **Recommended:** Read the official **Get Started** flow:
   - [Minikube: Start](https://minikube.sigs.k8s.io/docs/start/)
2. Install **kubectl**:
   - [Install kubectl on Windows](https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/)
3. Install Minikube (installer or package manager):
   - See [Install Minikube / Windows](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download).
4. Ensure virtualization is available (Hyper-V, WSL2, or another supported driver per docs).
5. Start: `minikube start`
6. **Verify:** same as macOS (`kubectl get nodes`, `kubectl cluster-info`).

### Deliverable (Assignment 0)

Create a text file `k8s-0-setup.txt` containing:

1. Output of: `minikube version` and `kubectl version --client`
2. Output of: `kubectl get nodes`
3. One or two sentences on anything you had to fix (e.g. driver, permissions) or “No issues.”

**Submission:** Save as `k8s-0-setup.txt`, commit, and push to **`kubernetes-intro-assignment`** on GitLab.

---

## Assignment 1: First contact with the cluster

**Goal:** Practice reading cluster state and running a simple one-off Pod.

**Tasks:**

1. Run `kubectl get pods -A` (all namespaces). Note what runs in `kube-system`—you did not create those; they are cluster add-ons.
2. Create a **Pod** imperatively (quick test):
   - `kubectl run debug --image=busybox:1.36 --restart=Never -- sleep 3600`
3. Show the Pod: `kubectl get pods -o wide` and `kubectl describe pod debug`.
4. Run a command inside the Pod: `kubectl exec -it debug -- sh` then `hostname` and `exit`.
5. View logs (even if empty): `kubectl logs debug`.
6. Delete the Pod: `kubectl delete pod debug`.

**Deliverable:** `k8s-1.txt` — paste outputs for steps 1 (abbreviated if huge), 3 (`describe` summary), and 4 (`hostname` line). Commit and push to **`kubernetes-intro-assignment`**.

---

## Assignment 2: Deployment and scaling

**Goal:** Apply a Deployment from YAML, observe ReplicaSets and Pods, scale replica count.

**Tasks:**

1. Copy [examples/deployment-nginx.yaml](examples/deployment-nginx.yaml) to your machine (or paste into `deployment.yaml`).
2. Apply: `kubectl apply -f deployment.yaml`
3. Run:
   - `kubectl get deployments`
   - `kubectl get replicasets`
   - `kubectl get pods -l app=nginx-demo` (adjust label if you changed it)
4. Scale to **5** replicas: `kubectl scale deployment/nginx-demo --replicas=5` (adjust name if needed).
5. Watch until ready: `kubectl rollout status deployment/nginx-demo`
6. Scale back to **2** replicas.
7. Change the image to another patch of the same line (e.g. `nginx:1.26-alpine` if available, or bump `1.25` to another supported tag), using `kubectl set image deployment/nginx-demo nginx=NEWIMAGE` **or** by editing the YAML and `kubectl apply -f`.
8. Show rollout history: `kubectl rollout history deployment/nginx-demo`

**Deliverable:** `k8s-2.txt` — output of `get deployments`, `get pods` after scaling to 5, and `rollout history` after the image change. Commit and push to **`kubernetes-intro-assignment`**.

---

## Assignment 3: Expose the app with a Service

**Goal:** Understand how a Service selects Pods and how to reach nginx from outside the Pod network (NodePort on Minikube).

**Tasks:**

1. Ensure you still have a Deployment with label `app=nginx-demo` (or consistent labels you used in Assignment 2).
2. Apply [examples/service-nodeport.yaml](examples/service-nodeport.yaml) **or** create a Service with `type: NodePort` whose `selector` matches your Deployment’s Pod labels. Fix `metadata.name` if it conflicts with an existing Service.
3. `kubectl get svc` — note the **PORT(S)** column and the assigned `NodePort`.
4. Access the app:
   - **Minikube:** `minikube ip` then open `http://<that-ip>:<NodePort>` in a browser, **or** run `minikube service nginx-demo` (adjust name).
5. Optional: `kubectl port-forward service/nginx-demo 8080:80` and curl `http://127.0.0.1:8080` in another terminal.

**Deliverable:** `k8s-3.txt` — `kubectl get svc` output and one line showing how you reached the page (URL or `curl` response head). Commit and push to **`kubernetes-intro-assignment`**.

---

## Assignment 4: ConfigMap and environment variables

**Goal:** Inject configuration into Pods without rebuilding the image.

**Tasks:**

1. Apply [examples/configmap-env.yaml](examples/configmap-env.yaml).
2. Create a small Deployment (YAML file) named `hello-env` that runs **`busybox:1.36`** with command `sleep 3600` **or** an image that echoes env on start. The Pod must reference the ConfigMap `hello-config` so that `MESSAGE` and `TRAINING` appear as **environment variables** (use `envFrom` with `configMapRef`—see [Kubernetes docs: Configure Pod to use ConfigMap](https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/)).
3. Apply the Deployment, wait for the Pod to run.
4. Prove the variables: `kubectl exec -it deploy/hello-env -- env | grep -E 'MESSAGE|TRAINING'` (adjust deployment name if different).

**Deliverable:**
- `k8s-4-manifest.txt` — your complete Deployment manifest pasted as **plain text** (same content you would put in a `.yaml` file; using `.txt` keeps all submissions text-based).
- `k8s-4.txt` — the `grep` output lines proving `MESSAGE` and `TRAINING`.

Commit both files and push to **`kubernetes-intro-assignment`**.

---

## Assignment 5: Namespace and labels

**Goal:** Isolate workloads with a Namespace; practice label selectors.

**Tasks:**

1. Create a namespace: `kubectl create namespace training`
2. Deploy a second copy of your nginx Deployment into **`training`** only (same YAML as before but add `metadata.namespace: training` **or** use `-n training` with `kubectl apply`). Use a distinct label value, e.g. `app: nginx-training`.
3. From the default namespace, run: `kubectl get pods -n training`
4. Use label selectors to list pods: e.g. `kubectl get pods -A -l app=nginx-demo` and `kubectl get pods -n training -l app=nginx-training` (adjust label keys/values to match your YAML). At minimum show `kubectl get pods -A` where workloads in `default` and `training` are visible.
5. Delete the resources in `training` (`kubectl delete namespace training` cascades—confirm you understand this before running).

**Deliverable:** `k8s-5.txt` — output showing pods/deployments in `training` before deletion, and the command you used to delete the namespace (or resources). Commit and push to **`kubernetes-intro-assignment`**.

---

## Summary

| Assignment | Focus | GitLab file(s) |
|------------|--------|----------------|
| 0 | Install Minikube + kubectl; verify cluster | `k8s-0-setup.txt` |
| 1 | Pods, exec, logs, namespaces overview | `k8s-1.txt` |
| 2 | Deployment, ReplicaSet, scale, rollout / image change | `k8s-2.txt` |
| 3 | Service, NodePort, reach app from host | `k8s-3.txt` |
| 4 | ConfigMap → env in Pod | `k8s-4-manifest.txt`, `k8s-4.txt` |
| 5 | Namespace, labels, cleanup | `k8s-5.txt` |

---

[← Back to Introduction](README.md)
