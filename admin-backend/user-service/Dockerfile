FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :user-service:build -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/user-service/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "app.jar" ]