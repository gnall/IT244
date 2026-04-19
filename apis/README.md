# APIs (Java) — Introduction + Local REST Project

- [Assignments](assignments.md)

## What is an API?

An **API** (Application Programming Interface) is a **contract** that defines how one piece of software can interact with another.

- **You (client)** send a request in an agreed format
- **The API (server)** processes it
- **The API** returns a response in an agreed format

In this module, we focus on **web APIs** (APIs over HTTP), where the “contract” is built from:

- **Endpoints** (URLs like `/transform/uppercase`)
- **HTTP methods** (`GET`, `POST`, `PUT`, `DELETE`)
- **Request/response formats** (usually **JSON**)
- **Status codes** (e.g. `200`, `201`, `400`, `404`, `500`)

### Why APIs matter

APIs are how modern systems integrate:

- A web frontend calls a backend API for data
- Mobile apps call APIs
- Microservices call each other’s APIs
- Automation scripts call cloud provider APIs

You can think of APIs as “functions you call across the network.”

---

## Key web API ideas (the details that matter)

### Client vs server

- **Client**: the program making the request (browser, `curl`, Postman, another service)
- **Server**: the program exposing the API (your Java app)

### REST (practical definition)

In many classes, “REST” means:

- Use HTTP methods to express intent
- Use URLs to identify resources (e.g. `/items/123`)
- Use JSON bodies for structured data
- Use status codes to communicate outcomes

### HTTP methods (what they usually mean)

| Method | Typical meaning | Has request body? | Example |
|--------|------------------|-------------------|---------|
| `GET` | Read something | Usually no | `GET /items` |
| `POST` | Create something / run an action | Yes | `POST /items` |
| `PUT` | Replace something (by id) | Yes | `PUT /items/123` |
| `DELETE` | Delete something (by id) | No | `DELETE /items/123` |

### Status codes (minimum set)

| Code | Meaning | When you use it |
|------|---------|-----------------|
| `200 OK` | Success | Successful `GET`, successful transform/action |
| `201 Created` | Created new resource | `POST` that created a new item |
| `400 Bad Request` | Client sent invalid input | Missing fields, invalid JSON, failed validation |
| `404 Not Found` | Resource doesn’t exist | Item id not found |
| `500 Internal Server Error` | Bug / unexpected error | Your app crashed while handling request |

### JSON request/response basics

JSON is structured text. Example:

```json
{
  "input": "hello",
  "output": "HELLO"
}
```

---

## Building a local Java API (Spring Boot)

We’ll build a simple REST API locally using **Spring Boot**. You’ll implement:

- **Transform endpoints** (basic programming functions)
- **Read/write endpoints** using an **in-memory store** (no database required)
- Basic validation and correct HTTP status codes

### Prerequisites (local only)

- **Java 21** (or Java 17 if that’s what your environment supports)
- **Maven** (or use the included Maven Wrapper `./mvnw` if present in your generated project)
- A way to send requests:
  - `curl` (recommended)
  - Postman / Insomnia (optional)

### Create the project

Use Spring Initializr:

- Project: **Maven**
- Language: **Java**
- Spring Boot: latest stable 3.x
- Packaging: **Jar**
- Java: **21** (or 17)
- Dependencies:
  - **Spring Web**
  - **Validation**

Keep the project on your machine (this module assumes local development).

---

## What you will build

### 1) Health endpoint

- `GET /health` → `{ "status": "ok" }`

### 2) Transform endpoints (basic programming functions)

All of these should accept JSON input and return JSON output.

- `POST /transform/uppercase`
- `POST /transform/reverse`
- `POST /transform/trim`
- `POST /transform/stats` (length, word count, maybe vowel count)

### 3) Items API (read/write)

An “item” is a small JSON resource you can create, list, fetch, update, and delete.

- `POST /items` (create)
- `GET /items` (list)
- `GET /items/{id}` (read one)
- `PUT /items/{id}` (update)
- `DELETE /items/{id}` (delete)

Use an in-memory structure (e.g. `Map<Long, Item>`), plus an id counter. This keeps the focus on API concepts rather than databases.

---

## Testing your API locally (curl examples)

Assume your app runs at `http://localhost:8080`.

### Health check

```bash
curl -s http://localhost:8080/health
```

### Transform example

```bash
curl -s -X POST http://localhost:8080/transform/uppercase \
  -H "Content-Type: application/json" \
  -d '{"input":"hello api"}'
```

### Create an item

```bash
curl -s -X POST http://localhost:8080/items \
  -H "Content-Type: application/json" \
  -d '{"name":"Notebook","value":"paper"}'
```

### List items

```bash
curl -s http://localhost:8080/items
```

---

## Common pitfalls (and why they matter)

- **Wrong method**: `GET` should not change state; use `POST/PUT/DELETE` for state changes.
- **No validation**: API contracts should reject bad input with `400`.
- **No clear status codes**: make clients guess; use correct codes (`201` for create, `404` if missing).
- **Mixing “action” endpoints and resource endpoints**: transformations are “actions”; items are “resources.” Both can be valid, but keep each pattern consistent.

---

## Next

Go to [Assignments](assignments.md) for the incremental build.

