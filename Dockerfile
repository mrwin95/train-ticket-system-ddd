#FROM bellsoft/liberica-openjre-alpine:23 original bellsoft
FROM bellsoft/liberica-runtime-container:jre-23-musl
#original bellsoft
#RUN apk add --no-cache

ARG JAR_FILE=train-start/target/*.jar

COPY ${JAR_FILE} app.jar

#COPY .env .env

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java", "-XX:ArchiveClassesAtExit=./application.jsa", "-Dspring.context.exit=onRefresh", "-jar", "/app.jar"]

#java -XX:ArchiveClassesAtExit=./application.jsa -Dspring.context.exit=onRefresh -jar spring-petclinic-3.3.0-SNAPSHOT/spring-petclinic-3.3.0-SNAPSHOT.jar