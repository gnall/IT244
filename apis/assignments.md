# APIs (Java) — Assignment: Build a Local REST API (Incremental)

## Goal

Build a local Java REST API using Spring Boot that demonstrates:

- **API fundamentals** (endpoints, methods, JSON, status codes)
- **Basic programming functions** via transform endpoints
- **Read/write functionality** via a small CRUD API

You will develop and run everything **locally**.

---

## How to submit

Create a GitLab project named **`apis-assignment`** and push your code there. Include:

- Your Spring Boot project source
- A short `README.md` with:
  - How to run the app
  - Example `curl` commands (at least 5)

---

## Starter structure (copy from example directory)

Copy the starter project from:

- `apis/examples/java-spring-boot-starter/`

It already includes **one working endpoint for each request type**:

- **GET**: `GET /health`
- **POST**: `POST /transform/uppercase`
- **DELETE**: `DELETE /items/{id}`

Then implement the rest of the required API by filling in the `TODO` markers in the example code.

---

## Swagger / OpenAPI page (how it works)

The starter project exposes an **OpenAPI** document (via Springdoc) by scanning your Spring MVC controllers (your `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.). It also includes a simple **Swagger UI page** (`swagger-ui.html`) that reads that OpenAPI JSON.

Use these URLs:

- **Swagger UI (interactive docs)**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON (machine-readable spec)**: `http://localhost:8080/v3/api-docs`

### What to do with it

- **Discover endpoints**: every route you implement should show up as a path (example: `/items/{id}`).
- **See request/response shapes**: it will show JSON schemas derived from your request/response classes (records like `ItemCreateRequest`).
- **Try it in the browser**: click an endpoint → **Try it out** → send a request without writing `curl`.

### What changes Swagger UI

When you add or change endpoints, Swagger updates automatically because it reflects your code:

- Add a new method with `@PostMapping("/transform/reverse")` → a new operation appears in Swagger.
- Add validation like `@NotBlank` → Swagger documents that the field is required (and your API should return `400` when it’s missing/blank).

---

## API contract (what your server must support)

### Health

- `GET /health` → `200 OK`

Response:

```json
{ "status": "ok" }
```

### Transform endpoints (data transformation)

All transform endpoints:

- Use `POST`
- Accept JSON input: `{ "input": "..." }`
- Return JSON output: always includes the original `input`
- Return `400 Bad Request` if `input` is missing or invalid

Required endpoints:

1. `POST /transform/uppercase` → output is uppercase
2. `POST /transform/reverse` → output is reversed
3. `POST /transform/trim` → output is trimmed
4. `POST /transform/stats` → output includes:
   - `length` (characters)
   - `wordCount` (split by whitespace)

Suggested response shapes:

```json
{ "input": "hello", "output": "HELLO" }
```

```json
{ "input": "hello world", "length": 11, "wordCount": 2 }
```

### Items API (read/write CRUD)

An Item has:

- `id` (number)
- `name` (string, required, not blank)
- `value` (string, optional)

Endpoints:

1. `POST /items`
   - Creates an item
   - Returns `201 Created`
   - Returns the created item with `id`
2. `GET /items`
   - Returns `200 OK`
   - Returns an array of items
3. `GET /items/{id}`
   - Returns `200 OK` with the item
   - Returns `404 Not Found` if missing
4. `PUT /items/{id}`
   - Replaces the item’s fields (`name`, `value`)
   - Returns `200 OK` with updated item
   - Returns `404 Not Found` if missing
5. `DELETE /items/{id}`
   - Returns `204 No Content` if deleted
   - Returns `404 Not Found` if missing

Storage:

- Use an **in-memory** store (e.g. `Map<Long, Item>`)
- Use a simple `long` counter for ids

---

## Incremental build tasks

Complete in order. Each step should run and be testable with `curl`.

### Part 1: Project setup + first endpoint

- Create Spring Boot project (Spring Web + Validation)
- Implement `GET /health`

**Deliverable:** screenshot or terminal output of `curl http://localhost:8080/health`

### Part 2: One transform endpoint

- Implement `POST /transform/uppercase`
- Validate request body:
  - Missing `input` → `400`
  - Blank `input` → `400`

**Deliverable:** `curl` showing success and one `curl` showing the `400` behavior.

### Part 3: Three more transforms

- Add `reverse`, `trim`, `stats`
- Keep response formats consistent

**Deliverable:** at least one `curl` per endpoint.

### Part 4: Create + list items

- Implement:
  - `POST /items` returning `201`
  - `GET /items` returning list
- Add validation for `name`

**Deliverable:** create two items, then list them.

### Part 5: Read one + update + delete

- Implement:
  - `GET /items/{id}`
  - `PUT /items/{id}`
  - `DELETE /items/{id}`
- Ensure correct `404` behavior
- Ensure delete returns `204`

**Deliverable:** a short sequence of `curl` commands:

- create
- read by id
- update
- read again
- delete
- read again (should be `404`)

---

## Requirements checklist (grading oriented)

- **Correct HTTP methods** used for each endpoint
- **Correct status codes** (`201`, `204`, `400`, `404`)
- **JSON request/response** works for all endpoints
- **Validation** rejects bad input
- **Local run** instructions work (`mvn spring-boot:run` or `./mvnw spring-boot:run`)

---

[← Back to APIs module](README.md)

