# Java Spring Boot starter (copy for assignment)

This folder is a minimal Spring Boot API project used in the IT244 APIs assignment.

It includes 3 working endpoints:

- `GET /health`
- `POST /transform/uppercase`
- `DELETE /items/{id}`

Students should copy this folder into their own repo, run it locally, then implement the remaining endpoints by following the `TODO` markers in code.

## Run locally

```bash
mvn spring-boot:run
```

If you run into a Swagger UI startup error on your machine, upgrade/downgrade Spring Boot in `pom.xml` to match the course version.

Then try:

```bash
curl -s http://localhost:8080/health
```

## Swagger / OpenAPI UI

Once the app is running, open:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

