# 🆔 PESEL Generator API

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Build](https://img.shields.io/badge/build-Maven-orange?logo=apachemaven)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

**PESEL Generator API** — a lightweight REST service built with **Spring Boot** that generates valid Polish PESEL numbers.  
It supports random generation as well as custom parameters (birthdate, sex, quantity).  
All generated numbers within a single request are guaranteed to be **unique** ✅

---

## ✨ Features
- 🔢 Generate one or multiple PESEL numbers
- 🎯 Supported parameters:
    - `sex` — gender (`male` / `female`)
    - `dob` — birthdate (`dd.MM.yyyy`)
    - `quantity` — amount of PESELs to generate (default: `1`)
- 🛡️ Input validation and clean JSON error responses
- 📦 Clean and simple architecture: controller, service, exceptions, utils
- 🚀 Ready for Docker & CI/CD

---

## ⚙️ Running

### Maven
```bash
mvn spring-boot:run
mvn clean package
java -jar target/pesel-*.jar
```
Service will be available at:
👉 http://localhost:8080/generator

### Single random PESEL
```bash
curl "http://localhost:8080/generator"
```

### Female PESEL
```bash
curl "http://localhost:8080/generator?sex=female"
```

### 10 random PESELs
```bash
curl "http://localhost:8080/generator?quantity=10"
```

### Specific date of birth
```bash
curl "http://localhost:8080/generator?dob=28.06.1991"
```

### Combined parameters
```bash
curl "http://localhost:8080/generator?dob=28.06.1991&sex=male&quantity=5"
```

## 🔥 Example Responses

### Successful response:
```bash
[
  "91062812345",
  "91062867890",
  "91062854321"
]
```

### Error response:
```bash
{
  "timestamp": "2025-09-15T07:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "dob must be in format dd.MM.yyyy",
  "path": "/generator"
}
```

---

👨‍💻 Made with ❤️ by [Anton Sizov](https://antonsizov.com)
