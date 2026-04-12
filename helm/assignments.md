# Helm — Assignments

Complete these in order after **Minikube** is running and **`kubectl`** works. Install **Helm 3** on your workstation (not inside the cluster). Use the course [README](README.md) and [Helm quick reference](quick-ref.md).

## How to submit

Submit deliverables in a GitLab project named **`helm-intro-assignment`**.

1. In your [IT 244 student group on GitLab](https://gitlab.com/university-of-scranton/computing-sciences/courses/it-244/students), create (or use) a project named **`helm-intro-assignment`**.
2. Clone it to your computer. Add **only `.txt` files** at the **repository root** as listed below.
3. After each assignment (or when all are done):
   ```bash
   git add helm-*.txt
   git commit -m "Describe what you added"
   git push origin main
   ```

| File | Assignment |
|------|------------|
| `helm-1.txt` | 1 |
| `helm-2.txt` | 2 |
| `helm-3.txt` | 3 |
| `helm-4.txt` | 4 |
| `helm-5.txt` | 5 |
| `helm-6.txt` | 6 |
| `helm-7.txt` | 7 |
| `helm-8.txt` | 8 |

---

## Assignment 1: Install Helm and work with repositories

**Goal:** Install Helm 3, add a public chart repository, search for charts, and show your environment.

**Tasks:**

1. Install **Helm 3** following [Helm install docs](https://helm.sh/docs/intro/install/) for your OS (macOS, Windows, or Linux).
2. Run `helm version` and confirm **SemVer: v3.x.x** with no Tiller references.
3. Add the Bitnami repo: `helm repo add bitnami https://charts.bitnami.com/bitnami`
4. Run `helm repo update`
5. Search: `helm search repo nginx --max-col-width 80` (or a shorter keyword if many results).
6. Run `helm repo list`.

**Deliverable (`helm-1.txt`):** Paste outputs for steps 2, 5 (first ~15 lines is fine if long), and 6.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 2: Inspect a chart without installing

**Goal:** Use `helm show` and `helm template` to understand a chart before you trust it on the cluster.

**Tasks:**

1. Ensure the **bitnami** repo from Assignment 1 is still configured (`helm repo update` if needed).
2. Show chart metadata: `helm show chart bitnami/nginx`
3. Show default values (first part is enough): `helm show values bitnami/nginx` — capture at least the first **40 lines** in your file.
4. Render templates locally **without** installing:  
   `helm template demo bitnami/nginx --set service.type=NodePort`  
   (Release name `demo` is arbitrary for rendering.)

**Deliverable (`helm-2.txt`):** Paste output of step 2, first 40 lines of step 3, and the **first 50 lines** of step 4 (so the file stays readable).

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 3: Install and list a release on Minikube

**Goal:** Install a chart from a repository, verify workloads with `kubectl`, and inspect the release with Helm.

**Prerequisites:** `minikube start` and `kubectl get nodes` succeed.

**Tasks:**

1. Install nginx from Bitnami with a **unique release name** (e.g. `helm install mynginx-YOURINITIALS bitnami/nginx -n default --set service.type=NodePort` — adjust name if taken).
2. `helm list`
3. `helm status YOUR_RELEASE_NAME`
4. `kubectl get pods,svc` (show resources created for the release).
5. `helm get values YOUR_RELEASE_NAME` (see what Helm stored for values).

**Deliverable (`helm-3.txt`):** Paste outputs for steps 2, 3 (summary section is enough), 4, and 5.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 4: Upgrade with `--set` and see history

**Goal:** Change an existing release with `helm upgrade` and use revision history.

**Tasks:**

1. Use the **same release** from Assignment 3 (or install again if you removed it).
2. Upgrade replica count:  
   `helm upgrade YOUR_RELEASE_NAME bitnami/nginx --set replicaCount=3 --set service.type=NodePort`
3. Wait until pods are ready: `kubectl get pods -w` (Ctrl+C when stable) — capture final `kubectl get pods` line(s).
4. `helm history YOUR_RELEASE_NAME`
5. Show merged values: `helm get values YOUR_RELEASE_NAME --all` (or without `--all` if you prefer what was overridden).

**Deliverable (`helm-4.txt`):** Paste `helm upgrade` command line you used, step 3 pod listing, step 4 full history, and step 5 output (truncate middle of `--all` if huge, but keep top and replica-related lines).

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 5: Local chart, dry-run, uninstall

**Goal:** Install the course **minimal-web** chart from disk, practice dry-run, then clean up.

**Tasks:**

1. Copy the [examples/minimal-web](examples/minimal-web/) folder from this course repo onto your machine (keep `Chart.yaml`, `values.yaml`, `templates/` intact), or clone the IT244 repo and `cd` to `helm/examples/minimal-web`.
2. Lint: `helm lint .` (run **inside** the `minimal-web` chart directory).
3. Dry-run install: `helm install localdemo . --dry-run --debug` (from same directory). Capture the **first 60 lines** of output.
4. Real install: `helm install localdemo .` (use another release name if `localdemo` exists).
5. `helm list` and `kubectl get pods,svc` for the new release.
6. Uninstall: `helm uninstall localdemo` (or your release name). Confirm with `helm list` and `kubectl get pods` that workloads are gone.

**Deliverable (`helm-5.txt`):** Paste `helm lint` result, first 60 lines of dry-run, `helm list` after real install, short `kubectl get` after install, then the `helm uninstall` command and final `helm list`.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 6: Custom values file (`-f`) on Minikube

**Goal:** Drive a local chart with a **separate values file** instead of only `--set`, and confirm the cluster matches what you intended.

**Prerequisites:** [minimal-web](examples/minimal-web/) chart directory on your machine (same chart as Assignment 5). Assignment 5 should have ended with `helm uninstall`; if anything is still installed from earlier assignments, remove it or use a **new release name** below.

**Tasks:**

1. In an editor, create **`my-training-values.yaml`** (save it next to the chart or anywhere you like) with at least:
   - `replicaCount: 2`
   - A different **patch** of nginx under `image:` (e.g. keep `repository: nginx` and set `tag:` to another valid tag such as `1.26-alpine` if available on your network)
   - `service.type: NodePort` (may match the chart default—that is fine)
2. From the **`minimal-web`** chart directory, install a new release:  
   `helm install valueslab . -f /path/to/my-training-values.yaml`  
   (Use release name `valueslab` or another unique name; adjust `-f` path.)
3. Run `helm get values valueslab` (or your release name).
4. Run `kubectl get pods,deployments` and confirm **two** Pods are ready for that release.
5. Optional sanity check: `helm template valueslab . -f /path/to/my-training-values.yaml | grep -E 'replicas:|image:' | head -n 10` to show rendered lines match your file.

**Deliverable (`helm-6.txt`):**  
(1) The **full contents** of `my-training-values.yaml` at the top of the file.  
(2) The exact `helm install … -f …` command you ran.  
(3) Output of `helm get values` and `kubectl get pods,deployments`.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 7: Compare renders, upgrade, and hit the Service locally

**Goal:** Use **`helm template`** to preview YAML, apply a **valid** upgrade on Minikube, then **test the app locally** with port-forward (no browser required).

**Prerequisites:** Release from Assignment 6 still installed (`valueslab` or your name). If not, `helm install valueslab . -f my-training-values.yaml` again from `minimal-web`.

**Tasks:**

1. **Compare renders** (no cluster change): from the chart directory, run twice and capture the lines that show **replicas** and **image** (use `grep` or your editor):
   - `helm template preview-a . --set replicaCount=1 | grep -E 'replicas:|image:' | head -n 12`
   - `helm template preview-b . --set replicaCount=3 | grep -E 'replicas:|image:' | head -n 12`
2. **Upgrade** the real release to three replicas:  
   `helm upgrade valueslab . --set replicaCount=3`  
   (Replace `valueslab` with your Assignment 6 release name.)
3. Wait until Pods are ready: `kubectl rollout status deployment/valueslab-minimal-web` (adjust prefix if your release name differs—the Deployment is named `{{ .Release.Name }}-minimal-web`).
4. **Local test:** Forward a port to the chart’s Service (name is `RELEASE-minimal-web`):  
   `kubectl port-forward service/valueslab-minimal-web 9080:80`  
   In a **second** terminal, run:  
   `curl -s -o /dev/null -w "HTTP %{http_code}\n" http://127.0.0.1:9080/`  
   You should see **HTTP 200**. Stop port-forward with Ctrl+C when done.

**Deliverable (`helm-7.txt`):** Paste outputs for steps 1 (both `helm template` greps), step 2 command, step 3 output, and step 4 `curl` line.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Assignment 8: Failed upgrade and `helm rollback`

**Goal:** See how a **bad** value breaks the workload, then **recover** with a rollback—common operational skill.

**Prerequisites:** A working release from Assignment 7 (`valueslab` or equivalent) on **minimal-web**.

**Tasks:**

1. Note current revision: `helm history valueslab` (one line is enough).
2. **Break the release** with an image tag that will not pull (example):  
   `helm upgrade valueslab . --set image.tag=this-tag-should-not-exist-12345`
3. Show failure: `kubectl get pods` (expect `ImagePullBackOff` or `ErrImagePull`) and one line from `kubectl describe pod` for a failing Pod showing the **Failed** or **Pull** reason (first error lines are enough).
4. **Rollback:** `helm rollback valueslab` (goes to previous revision) **or** `helm rollback valueslab REVISION` using the revision *before* your bad upgrade from `helm history`.
5. Confirm recovery: `kubectl get pods` (Running), and `helm history valueslab` showing the rollback revision.

**Deliverable (`helm-8.txt`):** Paste the bad `helm upgrade` command, step 3 `kubectl get pods` + short `describe` excerpt, the `helm rollback` command you used, final `kubectl get pods`, and `helm history` after recovery.

**Submission:** Commit and push to **`helm-intro-assignment`**.

---

## Summary

| Assignment | Focus |
|------------|--------|
| 1 | Install Helm 3; `repo add`, `repo update`, `search`, `repo list` |
| 2 | `helm show chart/values`; `helm template` (no install) |
| 3 | `helm install` from repo; `list`, `status`, `get values`; `kubectl` check |
| 4 | `helm upgrade --set`; `history`; replicas |
| 5 | Local path chart; `lint`; `--dry-run`; install + `uninstall` |
| 6 | Custom **values YAML** + `helm install -f`; verify replicas on Minikube |
| 7 | **Compare** `helm template` outputs; **upgrade**; **port-forward** + `curl` |
| 8 | Intentionally **bad** upgrade; **`helm rollback`**; healthy cluster again |

---

[← Back to Helm introduction](README.md)
