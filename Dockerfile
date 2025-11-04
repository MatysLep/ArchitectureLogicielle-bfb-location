FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /opt/dev
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -T 1C

FROM amazoncorretto:21.0.2-alpine3.19
WORKDIR /opt/app
COPY --from=build /opt/dev/target/*.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/opt/app/app.jar"]