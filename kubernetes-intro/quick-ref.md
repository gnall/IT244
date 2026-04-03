# kubectl Quick Reference

Common commands for working with a cluster (Minikube or otherwise). For full options: `kubectl <command> --help`.

## Cluster and context

| Command | Description |
|---------|-------------|
| `kubectl cluster-info` | Show cluster endpoint and control-plane summary. |
| `kubectl version` | Client and server version (short: `-o short`). |
| `kubectl config get-contexts` | List kubeconfig contexts. |
| `kubectl config use-context NAME` | Switch active context. |
| `kubectl get nodes` | List cluster nodes. |
| `kubectl describe node NAME` | Detailed node info (capacity, conditions, pods). |

## Core resources (get / describe / delete)

| Command | Description |
|---------|-------------|
| `kubectl get pods` | List Pods (`-A` all namespaces, `-o wide` extra columns). |
| `kubectl get deployments` | List Deployments. |
| `kubectl get replicasets` | List ReplicaSets. |
| `kubectl get services` | List Services. |
| `kubectl get namespaces` | List Namespaces (alias: `ns`). |
| `kubectl get configmaps` | List ConfigMaps (`cm`). |
| `kubectl get secrets` | List Secrets. |
| `kubectl get all` | Common types in current namespace (not literally everything). |
| `kubectl describe TYPE NAME` | Events and spec for one object. |
| `kubectl delete TYPE NAME` | Delete resource. |
| `kubectl delete -f file.yaml` | Delete resources defined in file. |

## Apply and create

| Command | Description |
|---------|-------------|
| `kubectl apply -f file.yaml` | Create or update from manifest (declarative, preferred). |
| `kubectl apply -f dir/` | Apply all YAML in directory. |
| `kubectl create deployment NAME --image=IMAGE` | Imperative shorthand (good for quick tests). |
| `kubectl expose deployment NAME --port=80 --type=NodePort` | Imperatively create Service. |

## Pods and debugging

| Command | Description |
|---------|-------------|
| `kubectl logs POD` | Container logs (add `-c CONTAINER` if multiple). |
| `kubectl logs -f POD` | Stream logs. |
| `kubectl logs deployment/DEPLOYMENT` | Logs from one Pod in the Deployment. |
| `kubectl exec -it POD -- /bin/sh` | Shell inside container (image must have shell). |
| `kubectl port-forward pod/POD LOCAL:REMOTE` | Forward local port to Pod. |
| `kubectl port-forward service/SVC LOCAL:PORT` | Forward to a Service. |

## Scaling and rollouts

| Command | Description |
|---------|-------------|
| `kubectl scale deployment/NAME --replicas=N` | Scale replica count. |
| `kubectl rollout status deployment/NAME` | Watch rollout progress. |
| `kubectl rollout history deployment/NAME` | Revision history. |
| `kubectl rollout undo deployment/NAME` | Roll back to previous revision. |
| `kubectl rollout undo deployment/NAME --to-revision=N` | Roll back to specific revision. |

## Labels

| Command | Description |
|---------|-------------|
| `kubectl get pods --show-labels` | Show labels column. |
| `kubectl label pod POD KEY=VALUE` | Add/overwrite pod label. |
| `kubectl get pods -l app=myapp` | Filter by label selector. |

## Namespaces

| Command | Description |
|---------|-------------|
| `kubectl get pods -n NAMESPACE` | Resources in namespace. |
| `kubectl create namespace NAME` | New namespace. |
| `kubectl config set-context --current --namespace=NAMESPACE` | Default namespace for context. |

## Short names (aliases)

| Long | Alternate |
|------|-----------|
| `pods` | `po` |
| `deployments` | `deploy` |
| `replicasets` | `rs` |
| `services` | `svc` |
| `namespaces` | `ns` |
| `configmaps` | `cm` |

## Minikube-specific

| Command | Description |
|---------|-------------|
| `minikube start` | Start local cluster. |
| `minikube stop` | Stop cluster. |
| `minikube status` | Check status. |
| `minikube dashboard` | Open Kubernetes dashboard (if supported). |
| `minikube service NAME` | Open NodePort URL in browser (helper). |
| `minikube kubectl -- get pods` | Use Minikube-bundled kubectl. |

---

[← Back to Introduction](README.md) · [Assignments](assignments.md)
