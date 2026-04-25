# Example Java REST API (Capstone Part 1)

This directory contains a small **working** Java REST API application used for Capstone Part 1.

## Endpoints
- `GET /api/health`
- `GET /api/echo?message=...`

## Example Curl Commands
- `curl http://localhost:8080/api/health`
- `curl "http://localhost:8080/api/echo?message=hello%20world"`

## Notes for Capstone
- The code intentionally includes **at least one lint/style issue** so a Java lint job will report findings.
- You are expected to containerize this app and build CI jobs around it (see `../assignment.md`).

