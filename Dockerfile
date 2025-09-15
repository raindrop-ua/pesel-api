# ---- Build stage -------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -B -DskipTests clean package

# ---- Runtime stage -----------------------------------------------------------
FROM eclipse-temurin:21-jre

RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8090

ENV JAVA_OPTS="-XX:+UseZGC -XX:MaxRAMPercentage=75.0 -XX:+AlwaysActAsServerClassMachine"
ENV APP_OPTS=""

HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
  CMD curl -fsS http://127.0.0.1:8090/generator || exit 1

ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar $APP_OPTS
