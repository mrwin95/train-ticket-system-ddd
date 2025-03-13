# Stage 1: Build the application with Maven
# ---- Build Stage ----
FROM bellsoft/liberica-openjdk-debian:23 AS build

# Install Maven manually

RUN apt update && apt install -y maven

# Set Maven repository path
ARG MAVEN_REPO=/root/.m2

# Create a directory for caching dependencies
RUN mkdir -p ${MAVEN_REPO}

WORKDIR /app

#COPY pom.xml .

# Cache dependencies in a separate layer
#RUN #mvn -B dependency:go-offline

COPY ./ ./

RUN mvn clean package -DskipTests

#FROM bellsoft/liberica-runtime-container:jdk-23-cds-slim-musl AS optimizer
FROM bellsoft/liberica-runtime-container:jdk-23-cds-slim-musl

WORKDIR /app

COPY --from=build /app/train-start/target/*.jar start.jar

RUN java -Djarmode=tools -jar /app/start.jar extract

RUN java -XX:ArchiveClassesAtExit=./application.jsa -Dspring.context.exit=onRefresh -jar start.jar

#FROM bellsoft/liberica-runtime-container:jre-23-musl
#
#ARG JAR_FILE=train-start/target/*.jar
#
#WORKDIR /app
#
#COPY --from=build /app/train-start/target/*.jar start.jar

EXPOSE 8080

#ENTRYPOINT ["java","-jar","/app/start.jar"]
