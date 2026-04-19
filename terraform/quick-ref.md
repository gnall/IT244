# Terraform CLI Quick Reference

Common **Terraform CLI** commands (Terraform **1.x**). For details on a subcommand: `terraform <command> -help`.

---

## Help and version

| Command | Description |
|---------|-------------|
| `terraform -help` | Top-level help. |
| `terraform <cmd> -help` | Help for one command (e.g. `terraform plan -help`). |
| `terraform version` | CLI version, platform, and optional provider mirror info. |

---

## Core workflow (everyday)

| Command | Description |
|---------|-------------|
| `terraform init` | Download providers/modules, configure backend, prepare working directory. Run after clone or when providers/modules change. |
| `terraform fmt` | Format `.tf` / `.tfvars` files in the current directory (canonical HCL style). |
| `terraform fmt -recursive` | Format all `.tf` files under subdirectories. |
| `terraform fmt -check` | Exit non-zero if formatting would change files (useful in CI). |
| `terraform validate` | Validate configuration **after** `init`; checks syntax and internal consistency. |
| `terraform plan` | Compare config + state to real infrastructure; show proposed create/update/delete **without** applying. |
| `terraform plan -out=FILE` | Save plan binary to `FILE` for later `apply` (reproducible apply). |
| `terraform apply` | Show plan, then apply after confirmation (unless `-auto-approve`). |
| `terraform apply FILE` | Apply a **saved plan** from `terraform plan -out=FILE`. |
| `terraform apply -auto-approve` | Apply without interactive approval (**use carefully**; common in automation with gates). |
| `terraform destroy` | Propose and destroy all resources in this configuration’s state (or use `-target` to limit). |
| `terraform destroy -auto-approve` | Destroy without prompt (dangerous if wrong workspace/backend). |

---

## Plan / apply options (often used)

| Command | Description |
|---------|-------------|
| `terraform plan -destroy` | Plan as if you will destroy (preview teardown). |
| `terraform plan -refresh-only` | Plan that only reconciles state with real infra (no config-driven changes). |
| `terraform apply -refresh-only` | Apply **refresh-only** updates to state (drift correction without other changes). |
| `terraform plan -target=ADDRESS` | Limit plan to one resource and its dependencies (break-glass; not for routine prod workflow). |
| `terraform apply -target=ADDRESS` | Same, for apply. |
| `terraform apply -replace=ADDRESS` | Force **replace** (destroy + recreate) for that resource address (successor to deprecated `taint`). |
| `terraform destroy -target=ADDRESS` | Destroy only that resource (and deps); still risky—confirm workspace/state. |

---

## Working directory (CLI)

| Command | Description |
|---------|-------------|
| `terraform -chdir=DIR <cmd>` | Run `cmd` as if the working directory were `DIR` (Terraform **0.14+**). Useful in scripts/monorepos. |

---

## Initialization variants

| Command | Description |
|---------|-------------|
| `terraform init` | Standard init. |
| `terraform init -upgrade` | Upgrade provider and module versions within declared constraints. |
| `terraform init -reconfigure` | Reconfigure backend **without** migration prompt (use when fixing backend settings). |
| `terraform init -migrate-state` | Backend migration flow when changing backend configuration. |
| `terraform init -backend=false` | Skip backend configuration (offline validation scenarios; advanced). |
| `terraform init -input=false` | Non-interactive; fail if input would be required. |

---

## Outputs

| Command | Description |
|---------|-------------|
| `terraform output` | Print all root module outputs. |
| `terraform output NAME` | Print one output value. |
| `terraform output -json` | All outputs as JSON. |
| `terraform output -raw NAME` | Raw string output (no quotes), when type is string. |

---

## State inspection and manipulation

| Command | Description |
|---------|-------------|
| `terraform state list` | List resource addresses in state. |
| `terraform state show ADDRESS` | Show one resource’s state attributes. |
| `terraform state mv OLD NEW` | Move state reference (refactor/rename resources or modules). |
| `terraform state rm ADDRESS` | Remove a resource from state **without** destroying it (Terraform “forgets” it; infra unchanged). |
| `terraform state pull` | Print raw state JSON (remote backends; **sensitive**). |
| `terraform state push` | Upload state (**dangerous**; rarely needed; understand implications first). |
| `terraform force-unlock LOCK_ID` | Release **stale** state lock if a run crashed (**coordinate** with team before using). |

Prefer **`terraform plan`** / **`apply`** for normal changes; use `state` subcommands for surgery, imports, and refactors.

---

## Import

| Command | Description |
|---------|-------------|
| `terraform import ADDRESS ID` | Associate an **existing** cloud object with a resource block; **write** the `resource` block first, then import. |

Import workflows vary by provider (`ID` format differs). See provider docs.

---

## Workspaces (CLI-managed state partitions)

| Command | Description |
|---------|-------------|
| `terraform workspace list` | List workspaces (`*` = current). |
| `terraform workspace show` | Print current workspace name. |
| `terraform workspace select NAME` | Switch workspace. |
| `terraform workspace new NAME` | Create and select a new workspace. |
| `terraform workspace delete NAME` | Delete a workspace (not allowed if it is `default` or non-empty in some setups—see docs). |

**Note:** Workspaces are **not** a substitute for strong isolation of production; many teams use **separate backends** or separate root modules instead.

---

## Providers and modules (inspection)

| Command | Description |
|---------|-------------|
| `terraform providers` | Print provider requirements and resolved paths for current config. |
| `terraform providers schema -json` | Machine-readable provider schemas (advanced/tooling). |
| `terraform get` | Legacy; **`terraform init`** fetches modules today. |

---

## Expressions and dependencies

| Command | Description |
|---------|-------------|
| `terraform console` | Interactive shell to evaluate expressions against current config/state (debugging). |

---

## Graph and visualization

| Command | Description |
|---------|-------------|
| `terraform graph` | Emit **Graphviz** DOT format of resources and dependencies. Often piped: `terraform graph \| dot -Tsvg > graph.svg`. |

---

## Testing (Terraform **1.6+**)

| Command | Description |
|---------|-------------|
| `terraform test` | Run **`tests`** directory / `*.tftest.hcl` integration tests (provider-dependent). |

---

## Format of note

- **Resource address** examples: `aws_instance.web`, `module.vpc.aws_subnet.private[0]`, `kubernetes_namespace.app`.

---

[← Back to Terraform introduction](README.md) · [Assignments](assignments.md)
