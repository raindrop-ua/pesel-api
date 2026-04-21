# PESEL Generator API

![Java](https://img.shields.io/badge/Java-25-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Build](https://img.shields.io/badge/build-Maven-orange?logo=apachemaven)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

**PESEL Generator API** — a lightweight REST service built with **Spring Boot** that generates valid Polish PESEL numbers.  
It supports random generation as well as custom parameters (birthdate, sex, quantity).  
All generated numbers within a single request are guaranteed to be **unique**.

---

## Features
- Generate one or multiple PESEL numbers
- Supported parameters:
    - `sex` — gender (`male` / `female`)
    - `dob` — birthdate (`dd.MM.yyyy`)
    - `quantity` — amount of PESELs to generate (default: `1`)
- Random generation uses dates within the last **50 years** from the current date.
  For older birthdates, pass an explicit `dob` value.
- Input validation and clean JSON error responses
- Clean and simple architecture: controller, service, exceptions, utils
- Ready for Docker & CI/CD

---

## Running

### Maven
```bash
mvn spring-boot:run
mvn clean package
java -jar target/pesel-*.jar
```
Service will be available at:
http://localhost:8090/generator

### Single random PESEL
```bash
curl "http://localhost:8090/generator"
```

### Female PESEL
```bash
curl "http://localhost:8090/generator?sex=female"
```

### 10 random PESELs
```bash
curl "http://localhost:8090/generator?quantity=10"
```

### Specific date of birth
```bash
curl "http://localhost:8090/generator?dob=03.09.1983"
```

### Combined parameters
```bash
curl "http://localhost:8090/generator?dob=26.06.1988&sex=female&quantity=5"
```

## Live Demo

[Try it on api.pesel.dev/generator](https://api.pesel.dev/generator)

## Documentation

- [Contributing Guide](./CONTRIBUTING.md)
- [Architecture Overview](./ARCHITECTURE.md)


---

Coded by [Anton Sizov](https://antonsizov.com)
