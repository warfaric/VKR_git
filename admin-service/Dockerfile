# ---------- build stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY catalog-service/pom.xml catalog-service/pom.xml
COPY admin-service/pom.xml admin-service/pom.xml

RUN mvn -q -DskipTests dependency:go-offline

COPY common common
COPY catalog-service catalog-service
COPY admin-service admin-service

RUN mvn -q -DskipTests -pl admin-service -am package

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre
RUN apt-get update \
    && apt-get upgrade -y \
    && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /workspace/admin-service/target/*.jar /app/app.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
