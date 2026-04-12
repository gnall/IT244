# Helm CLI Quick Reference

Helm **3** commands. For full help: `helm <command> --help`.

## Version and environment

| Command | Description |
|---------|-------------|
| `helm version` | Client and server (Kubernetes) version. |
| `helm env` | Show Helm environment variables and paths. |

## Repositories

| Command | Description |
|---------|-------------|
| `helm repo add NAME URL` | Add a chart repository. |
| `helm repo list` | List configured repositories. |
| `helm repo update` | Fetch latest charts from all repos (or `helm repo update REPO`). |
| `helm repo remove NAME` | Remove a repository. |
| `helm search repo KEYWORD` | Search charts in added repos. |
| `helm search hub KEYWORD` | Search [Artifact Hub](https://artifacthub.io/) (no repo add required). |

## Install, upgrade, uninstall

| Command | Description |
|---------|-------------|
| `helm install RELEASE CHART` | Install chart; `CHART` can be `repo/chart`, path `./mychart`, or URL. |
| `helm install RELEASE CHART -n NAMESPACE` | Install into namespace (create ns first if needed). |
| `helm install RELEASE CHART -f values.yaml` | Merge values file. |
| `helm install RELEASE CHART --set key=val` | Set single value (repeat `--set` for more). |
| `helm install RELEASE CHART --dry-run --debug` | Render and print YAML; do not install. |
| `helm upgrade RELEASE CHART` | Upgrade existing release to new chart/values. |
| `helm upgrade --install RELEASE CHART` | Upgrade if exists, else install (“upsert”). |
| `helm uninstall RELEASE` | Remove release (alias: `helm delete`). |
| `helm uninstall RELEASE -n NAMESPACE` | Uninstall from namespace. |

## Inspect charts (before install)

| Command | Description |
|---------|-------------|
| `helm show chart CHART` | Display `Chart.yaml`. |
| `helm show values CHART` | Display default `values.yaml`. |
| `helm show readme CHART` | Display chart README if present. |
| `helm show all CHART` | Chart, values, and readme. |
| `helm lint PATH` | Lint chart directory for errors/warnings. |
| `helm template RELEASE CHART` | Render templates locally; print YAML (no cluster change). |
| `helm template RELEASE CHART -f my.yaml` | Render with custom values. |

## Releases (what is installed)

| Command | Description |
|---------|-------------|
| `helm list` | List releases in current namespace (`-A` all namespaces). |
| `helm list -n NAMESPACE` | List in specific namespace. |
| `helm status RELEASE` | Status of a release. |
| `helm get all RELEASE` | Manifest + notes + values + hooks. |
| `helm get manifest RELEASE` | Rendered Kubernetes manifests. |
| `helm get values RELEASE` | User-supplied values (add `--all` to see defaults merged). |
| `helm get notes RELEASE` | Post-install notes from chart. |
| `helm history RELEASE` | Revision history for rollbacks. |
| `helm rollback RELEASE REVISION` | Roll back to revision number. |

## Package and dependency (authoring)

| Command | Description |
|---------|-------------|
| `helm package PATH` | Create `.tgz` from chart directory. |
| `helm dependency update PATH` | Download subchart dependencies per `Chart.yaml`. |

## History and rollback

| Command | Description |
|---------|-------------|
| `helm history RELEASE` | List revisions. |
| `helm rollback RELEASE [REVISION]` | Roll back (omit revision to go to previous). |

---

[← Back to Helm introduction](README.md) · [Assignments](assignments.md)
