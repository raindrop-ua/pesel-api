# Architecture

## Overview

`pesel-api` is a stateless Spring Boot REST service that generates valid Polish PESEL numbers.

- Entry point: `PeselApiApplication`
- Main endpoint: `GET /generator`
- Output: JSON with `meta` and `data` sections

## High-Level Flow

1. `GeneratorController` reads and validates query params: `sex`, `dob`, `quantity`.
2. `PeselGeneratorService` generates one or many PESEL values.
3. `PeselUtils` handles PESEL-specific date and checksum rules.
4. `RestExceptionHandler` maps exceptions to consistent JSON error responses.

## Package Structure

- `controller`:
  - HTTP endpoint handling and request parsing.
- `service`:
  - Core generation logic, uniqueness handling, quantity limits.
- `util`:
  - Pure utility logic for PESEL rules (date ranges, checksum, century month encoding).
- `dto`:
  - Response models (`GenerationResponse`, `GenerationMeta`, `ErrorResponse`, etc.).
- `exception`:
  - Domain exceptions and centralized exception-to-response mapping.
- `model`:
  - Domain enums (currently `Sex`).

## Generation Rules

- Supported year range: `1800..2299`.
- If `dob` is provided:
  - Year/month/day part is derived from input date with century-aware month encoding.
  - Serial + sex digit + checksum complete the PESEL.
- If `dob` is omitted:
  - Date is randomized within the last 50 years from current date.
- `quantity` defaults to `1`, max `10000`.
- Service enforces uniqueness within a single request.

## Error Handling

- Validation/domain exceptions return `400 Bad Request` with a stable JSON shape.
- Unexpected failures return `500 Internal Server Error`.
- Error payload is sanitized before being returned.

## Runtime Notes

- Java baseline: `25` (build and runtime images use Eclipse Temurin 25).
- Default HTTP port: `8090` (configurable via `SERVER_PORT`).
- Actuator exposes `health` and `info`.
- Docker compose maps `8090:8090` and includes a basic healthcheck.
