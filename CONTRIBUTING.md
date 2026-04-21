# Contributing

Thanks for contributing to `pesel-api`.

## Prerequisites

- Java 21
- Maven 3.9+ (or use `./mvnw`)
- Docker (optional, for container runs)

## Local Development

Run the app:

```bash
./mvnw spring-boot:run
```

Run tests:

```bash
./mvnw test
```

Build jar:

```bash
./mvnw clean package
```

## Code Style

- Keep changes small and focused.
- Follow existing package layout (`controller`, `service`, `dto`, `exception`, `util`).
- Use meaningful exception messages and keep API responses consistent.
- Prefer explicit validation for query parameters.

## Adding or Changing API Behavior

- Update controller + service logic together.
- Keep error handling in `RestExceptionHandler` coherent with existing responses.
- Update README examples for any user-visible API changes.
- Add or update tests for the changed behavior.

## Pull Request Checklist

- Project builds successfully (`./mvnw clean package`).
- Tests pass (`./mvnw test`).
- Documentation is updated when behavior changes.
- No unrelated refactors in the same PR.

