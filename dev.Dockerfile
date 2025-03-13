FROM bellsoft/liberica-runtime-container:jdk-23-crac-cds-musl AS builder
WORKDIR /app/build

COPY ./ ./

RUN mvn clean package

FROM bellsoft/liberica-runtime-container:jdk-23-cds-slim-musl AS optimizer

WORKDIR /app

COPY --from=builder /app/build/train-start/*.jar app.jar

RUN java -Djarmode=tools -jar app.jar extract --layers --launcher

FROM bellsoft/liberica-runtime-container:jdk-23-cds-slim-musl

ENTRYPOINT ["java", "-Dspring.aot.enabled=true", "-XX:SharedArchiveFile=application.jsa", "org.springframework.boot.loader.launch.JarLauncher"]
COPY --from=optimizer /app/app/dependencies/ ./
COPY --from=optimizer /app/app/spring-boot-loader/ ./
COPY --from=optimizer /app/app/snapshot-dependencies/ ./
COPY --from=optimizer /app/app/application/ ./
RUN java -Dspring.aot.enabled=true -XX:ArchiveClassesAtExit=./application.jsa -Dspring.context.exit=onRefresh org.springframework.boot.loader.launch.JarLauncher
