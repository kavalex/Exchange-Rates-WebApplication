FROM adoptopenjdk/openjdk11:alpine-jre
RUN addgroup -S alex-app && adduser -S alex-app -G alex-app
USER alex-app:alex-app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} alex-app.jar
ENTRYPOINT ["java","-jar","/alex-app.jar"]