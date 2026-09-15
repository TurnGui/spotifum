FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -pl spotifum-api -am -DskipTests -Pspring-boot

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/spotifum-api/target/spotifum-api-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
